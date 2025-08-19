package org.example.io;

import org.example.classify.LineClassify;
import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileProcessor {
    private final LineClassify classifier;
    private final OutputResult out;

    public FileProcessor(LineClassify classifier, OutputResult out) {
        this.classifier = classifier;
        this.out = out;
    }

    public void process(Path file) throws IOException {
        try (BufferedReader r = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
            String raw;
            while ((raw = r.readLine()) != null) {
                handle(raw);
            }
        }
    }

    private void handle(String raw) throws IOException {
        String s = raw == null ? "" : raw.trim();
        switch (classifier.classify(raw)) {
            case INTEGER: out.writeInt(s);    break;
            case FLOAT:   out.writeFloat(s);  break;
            case STRING:  out.writeString(s); break;
        }
    }
}
