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

    private long intsCount = 0;
    private long floatsCount = 0;
    private long stringsCount = 0;

    private Long minInt = null, maxInt = null, sumInt = 0L;
    private Double minFloat = null, maxFloat = null, sumFloat = 0.0;
    private String shortestStr = null, longestStr = null;

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
        long value = Long.parseLong(s);
        intWriteInFile.write(s);
        intWriteInFile.newLine();
        intsCount++;
        sumInt += value;
        if (minInt == null || value < minInt) minInt = value;
        if (maxInt == null || value > maxInt) maxInt = value;
    }

    public void writeFloat(String s) throws IOException {
        if (floatWriteInFile == null) floatWriteInFile = openWriter(floatFile);
        double value = Double.parseDouble(s);
        floatWriteInFile.write(s);
        floatWriteInFile.newLine();
        floatsCount++;
        sumFloat += value;
        if (minFloat == null || value < minFloat) minFloat = value;
        if (maxFloat == null || value > maxFloat) maxFloat = value;
    }

    public void writeString(String s) throws IOException {
        if (stringWriteInFile == null) stringWriteInFile = openWriter(stringFile);
        stringWriteInFile.write(s);
        stringWriteInFile.newLine();
        stringsCount++;
        if (shortestStr == null || s.length() < shortestStr.length()) {
            shortestStr = s;
        }
        if (longestStr == null || s.length() > longestStr.length()) {
            longestStr = s;
        }
    }

    public long getIntsCount() {return intsCount;}
    public long getFloatsCount() {return floatsCount;}
    public long getStringsCount() {return stringsCount;}
    public long getTotalCount() {return intsCount + floatsCount + stringsCount;}

    public Long getMinInt() { return minInt; }
    public Long getMaxInt() { return maxInt; }
    public Double getMinFloat() { return minFloat; }
    public Double getMaxFloat() { return maxFloat; }
    public double getAvgInt() { return intsCount == 0 ? 0 : (double) sumInt / intsCount; }
    public double getAvgFloat() { return floatsCount == 0 ? 0 : sumFloat / floatsCount; }
    public String getShortestStr() { return shortestStr; }
    public String getLongestStr() { return longestStr; }
    public Long getSumInt() { return sumInt; }
    public double getSumFloat() { return sumFloat; }

    @Override
    public void close() throws IOException {
        IOException err = null;
        try { if (intWriteInFile != null) intWriteInFile.close(); } catch (IOException e) { err = e; }
        try { if (floatWriteInFile != null) floatWriteInFile.close(); } catch (IOException e) { if (err == null) err = e; }
        try { if (stringWriteInFile != null) stringWriteInFile.close(); } catch (IOException e) { if (err == null) err = e; }
        if (err != null) {throw err;}
    }
}
