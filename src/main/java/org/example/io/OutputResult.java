package org.example.io;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;
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

    private BigInteger minInt = null, maxInt = null, sumInt = BigInteger.ZERO;
    private BigDecimal minFloat = null, maxFloat = null, sumFloat = BigDecimal.ZERO;
    private String shortestStr = null, longestStr = null;

    private final boolean append;

    private static final MathContext MC = MathContext.DECIMAL128;

    public OutputResult(Path integers, Path floats, Path strings, boolean append) {
        this.intFile = integers;
        this.floatFile = floats;
        this.stringFile = strings;
        this.append = append;
    }

    private BufferedWriter openWriter(Path file) throws IOException {
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
        BigInteger value = new BigInteger(s);
        intWriteInFile.write(s);
        intWriteInFile.newLine();
        intsCount++;
        sumInt = sumInt.add(value);
        if (minInt == null || value.compareTo(minInt) < 0) minInt = value;
        if (maxInt == null || value.compareTo(maxInt) > 0) maxInt = value;
    }

    public void writeFloat(String s) throws IOException {
        if (floatWriteInFile == null) floatWriteInFile = openWriter(floatFile);
        BigDecimal value = new BigDecimal(s);
        floatWriteInFile.write(s);
        floatWriteInFile.newLine();
        floatsCount++;
        sumFloat = sumFloat.add(value);
        if (minFloat == null || value.compareTo(minFloat) < 0) minFloat = value;
        if (maxFloat == null || value.compareTo(maxFloat) > 0) maxFloat = value;
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

    public BigInteger getMinInt() { return minInt; }
    public BigInteger getMaxInt() { return maxInt; }
    public BigDecimal getMinFloat() { return minFloat; }
    public BigDecimal getMaxFloat() { return maxFloat; }
    public BigDecimal getAvgInt() { return intsCount == 0 ? BigDecimal.ZERO : new BigDecimal(sumInt).divide(BigDecimal.valueOf(intsCount), MC); }
    public BigDecimal getAvgFloat() { return floatsCount == 0 ? BigDecimal.ZERO : sumFloat.divide(BigDecimal.valueOf(floatsCount), MC); }
    public String getShortestStr() { return shortestStr; }
    public String getLongestStr() { return longestStr; }
    public BigInteger getSumInt() { return sumInt; }
    public BigDecimal getSumFloat() { return sumFloat; }

    @Override
    public void close() throws IOException {
        IOException err = null;
        try { if (intWriteInFile != null) intWriteInFile.close(); } catch (IOException e) { err = e; }
        try { if (floatWriteInFile != null) floatWriteInFile.close(); } catch (IOException e) { if (err == null) err = e; }
        try { if (stringWriteInFile != null) stringWriteInFile.close(); } catch (IOException e) { if (err == null) err = e; }
        if (err != null) {throw err;}
    }
}
