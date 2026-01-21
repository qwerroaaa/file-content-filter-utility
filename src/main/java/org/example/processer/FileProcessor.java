package org.example.processer;

import org.example.config.AppConfig;
import org.example.parser.DataTypeDetector;
import org.example.statistics.FloatStatistics;
import org.example.statistics.IntegerStatistics;
import org.example.statistics.Statistics;
import org.example.statistics.StringStatistics;
import org.example.writer.OutputWriterManager;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class FileProcessor {

    private final AppConfig config;
    private final DataTypeDetector detector;
    private final Map<String, Statistics> statistics;

    public FileProcessor(AppConfig config) {
        this.config = config;
        this.detector = new DataTypeDetector();
        this.statistics = new HashMap<>();

        statistics.put("integers", new IntegerStatistics());
        statistics.put("floats", new FloatStatistics());
        statistics.put("strings", new StringStatistics());
    }

    public void process() {
        try (OutputWriterManager writerManager = new OutputWriterManager(config)) {
            for (String inputFile : config.getInputFiles()) {
                processFile(inputFile, writerManager);
            }
        } catch (Exception e) {
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }

    private void processFile(String inputFile, OutputWriterManager writerManager) {
        Path path = Paths.get(inputFile);

        if (!Files.exists(path)) {
            System.err.println("Предупреждение: файл не найден - " + inputFile);
            return;
        }

        if (!Files.isReadable(path)) {
            System.err.println("Предупреждение: нет доступа для чтения файла - " + inputFile);
            return;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(inputFile), StandardCharsets.UTF_8))) {

            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;

                if (line.trim().isEmpty()) {
                    continue;
                }

                processLine(line, writerManager, inputFile, lineNumber);
            }

        } catch (FileNotFoundException e) {
            System.err.println("Предупреждение: файл не найден - " + inputFile);
        } catch (IOException e) {
            System.err.println("Предупреждение: ошибка чтения файла " + inputFile + ": " + e.getMessage());
        }
    }

    private void processLine(String line, OutputWriterManager writerManager,
                             String fileName, int lineNumber) {
        try {
            DataTypeDetector.DataType type = detector.detect(line);

            switch (type) {
                case INTEGER:
                    writerManager.writeInteger(line);
                    statistics.get("integers").add(line);
                    break;

                case FLOAT:
                    writerManager.writeFloat(line);
                    statistics.get("floats").add(line);
                    break;

                case STRING:
                    writerManager.writeString(line);
                    statistics.get("strings").add(line);
                    break;
            }

        } catch (IOException e) {
            System.err.printf(
                    "Предупреждение: ошибка записи строки %d из файла %s: %s%n",
                    lineNumber, fileName, e.getMessage());
        }
    }

    public Map<String, Statistics> getStatistics() {
        return statistics;
    }
}
