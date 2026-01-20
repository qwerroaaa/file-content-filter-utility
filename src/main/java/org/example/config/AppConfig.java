package org.example.config;

import java.util.ArrayList;
import java.util.List;

public class AppConfig {
    private String outputPath = ".";
    private String prefix = "";
    private boolean appendMode = false;
    private boolean shortStatistics = false;
    private boolean fullStatistics = false;
    private final List<String> inputFiles = new ArrayList<>();

    public static final String INTEGERS_FILE = "integers.txt";
    public static final String FLOATS_FILE = "floats.txt";
    public static final String STRINGS_FILE = "strings.txt";

    public String getOutputPath() {
        return outputPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public boolean isAppendMode() {
        return appendMode;
    }

    public void setAppendMode(boolean appendMode) {
        this.appendMode = appendMode;
    }

    public boolean isShortStatistics() {
        return shortStatistics;
    }

    public void setShortStatistics(boolean shortStatistics) {
        this.shortStatistics = shortStatistics;
    }

    public boolean isFullStatistics() {
        return fullStatistics;
    }

    public void setFullStatistics(boolean fullStatistics) {
        this.fullStatistics = fullStatistics;
    }

    public List<String> getInputFiles() {
        return inputFiles;
    }

    public void addInputFile(String file) {
        this.inputFiles.add(file);
    }

    public String getIntegersFilePath() {
        return buildOutputFilePath(INTEGERS_FILE);
    }

    public String getFloatsFilePath() {
        return buildOutputFilePath(FLOATS_FILE);
    }

    public String getStringsFilePath() {
        return buildOutputFilePath(STRINGS_FILE);
    }

    private String buildOutputFilePath(String fileName) {
        String separator = outputPath.endsWith("/") || outputPath.endsWith("\\") ? "" : "/";
        return outputPath + separator + prefix + fileName;
    }

    @Override
    public String toString() {
        return "AppConfig{" +
                "outputPath='" + outputPath + '\'' +
                ", prefix='" + prefix + '\'' +
                ", appendMode=" + appendMode +
                ", shortStatistics=" + shortStatistics +
                ", fullStatistics=" + fullStatistics +
                ", inputFiles=" + inputFiles +
                '}';
    }
}
