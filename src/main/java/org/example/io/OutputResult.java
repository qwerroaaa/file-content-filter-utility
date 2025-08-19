package org.example.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;

public class OutputResult implements AutoCloseable {
    private final BufferedWriter ints;
    private final BufferedWriter floats;
    private final BufferedWriter strings;

    public OutputResult(Path integers, Path floats, Path strings) throws IOException {
        this.ints = Files.newBufferedWriter(integers, StandardCharsets.UTF_8);
        this.floats = Files.newBufferedWriter(floats, StandardCharsets.UTF_8);
        this.strings = Files.newBufferedWriter(strings, StandardCharsets.UTF_8);
    }

    public void writeInt(String s) throws IOException {ints.write(s); ints.newLine();};
    public void writeFloat(String s) throws IOException {floats.write(s); floats.newLine();};
    public void writeString(String s) throws IOException {strings.write(s); strings.newLine();};

    @Override
    public void close() throws IOException {
        IOException err = null;
        try {ints.close();} catch (IOException e) {err = e;};
        try {floats.close();} catch (IOException e) { if (err == null) err = e;};
        try {strings.close();} catch (IOException e) { if (err == null) err = e;};
        if (err != null) {throw err;};
    }
}
