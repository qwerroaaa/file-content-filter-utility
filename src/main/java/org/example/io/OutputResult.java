package org.example.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class OutputResult implements AutoCloseable {
    private final Path intFile;
    private final Path floatFile;
    private final Path stringFile;

    private BufferedWriter intWriteInFile;
    private BufferedWriter floatWriteInFile;
    private BufferedWriter stringWriteInFile;

    private final boolean append;

    public OutputResult(Path integers, Path floats, Path strings, boolean append) {
        this.intFile = integers;
        this.floatFile = floats;
        this.stringFile = strings;
        this.append = append;
    }

    private BufferedWriter openWriter(Path file) throws IOException {
        // гарантируем наличие родительских папок
        Path parent = file.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        OpenOption[] opts = append
                ? new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.APPEND}
                : new OpenOption[]{StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING, StandardOpenOption.WRITE};

        return Files.newBufferedWriter(file, StandardCharsets.UTF_8, opts);
    }

    public void writeInt(String s) throws IOException {
        if (intWriteInFile == null) intWriteInFile = openWriter(intFile);
        intWriteInFile.write(s);
        intWriteInFile.newLine();
    }

    public void writeFloat(String s) throws IOException {
        if (floatWriteInFile == null) floatWriteInFile = openWriter(floatFile);
        floatWriteInFile.write(s);
        floatWriteInFile.newLine();
    }

    public void writeString(String s) throws IOException {
        if (stringWriteInFile == null) stringWriteInFile = openWriter(stringFile);
        stringWriteInFile.write(s);
        stringWriteInFile.newLine();
    }

    @Override
    public void close() throws IOException {
        IOException err = null;
        try { if (intWriteInFile != null) intWriteInFile.close(); } catch (IOException e) { err = e; }
        try { if (floatWriteInFile != null) floatWriteInFile.close(); } catch (IOException e) { if (err == null) err = e; }
        try { if (stringWriteInFile != null) stringWriteInFile.close(); } catch (IOException e) { if (err == null) err = e; }
        if (err != null) {throw err;}
    }
}
