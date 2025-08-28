package org.example.io;

import org.example.classify.LineClassify;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.*;

public class FileProcessor {
    private static final Logger log = Logger.getLogger(FileProcessor.class.getName());
    private final LineClassify classifier;
    private final OutputResult out;

    public FileProcessor(LineClassify classifier, OutputResult out) {
        this.classifier = classifier;
        this.out = out;
    }

    public void process(Path file) throws IOException {
        long lineNo = 0;
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String raw;
            while ((raw = r.readLine()) != null) {
                lineNo++;
                try {
                    handle(raw);
                } catch (Exception error) {
                    log.log(Level.WARNING,
                            "Строка пропущена (файл: " + file.getFileName() + ", line " + lineNo + "): "
                                    + " — причина: " + error.getMessage(), error);
                }
            }
        }
    }

    private void handle(String raw) throws IOException {
        String line = raw == null ? "" : raw.trim();
        switch (classifier.classify(raw)) {
            case INTEGER: out.writeInt(line);    break;
            case FLOAT:   out.writeFloat(line);  break;
            case STRING:  out.writeString(line); break;
        }
    }
}
