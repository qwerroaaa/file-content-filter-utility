package org.example.writer;

import org.example.config.AppConfig;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

public class OutputWriterManager implements AutoCloseable {
    private final AppConfig config;
    private final Map<String, BufferedWriter> writers = new HashMap<>();
    private final Map<String, Boolean> hasData = new HashMap<>();

    public OutputWriterManager(AppConfig config) {
        this.config = config;
    }

    public void writeInteger(String value) throws IOException {
        write("integers", config.getIntegersFilePath(), value);
    }

    public void writeFloat(String value) throws IOException {
        write("floats", config.getFloatsFilePath(), value);
    }

    public void writeString(String value) throws IOException {
        write("strings", config.getStringsFilePath(), value);
    }

    private void write(String key, String filePath, String value) throws IOException {
        BufferedWriter writer = writers.get(key);

        if (writer == null) {
            writer = createWriter(filePath);
            writers.put(key, writer);
            hasData.put(key, true);
        }

        writer.write(value);
        writer.newLine();
    }

    private BufferedWriter createWriter(String filePath) throws IOException {
        Path path = Paths.get(filePath);

        // Создаем директории если они не существуют
        Path parent = path.getParent();
        if (parent != null && !Files.exists(parent)) {
            Files.createDirectories(parent);
        }

        FileOutputStream fos = new FileOutputStream(filePath, config.isAppendMode());
        OutputStreamWriter osw = new OutputStreamWriter(fos, StandardCharsets.UTF_8);
        return new BufferedWriter(osw);
    }

    public boolean hasData(String type) {
        return hasData.getOrDefault(type, false);
    }

    @Override
    public void close() {
        for (Map.Entry<String, BufferedWriter> entry : writers.entrySet()) {
            try {
                entry.getValue().flush();
                entry.getValue().close();
            } catch (IOException e) {
                System.err.println("Ошибка при закрытии файла " + entry.getKey() + ": " + e.getMessage());
            }
        }
        writers.clear();
    }
}
