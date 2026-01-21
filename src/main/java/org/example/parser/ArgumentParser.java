package org.example.parser;

import org.apache.commons.cli.*;
import org.example.config.AppConfig;

public class ArgumentParser {
    private final Options options;

    public ArgumentParser() {
        this.options = createOptions();
    }

    private Options createOptions() {
        Options opts = new Options();

        opts.addOption(Option.builder("o")
                .longOpt("output")
                .hasArg()
                .argName("путь")
                .desc("Путь для выходных файлов")
                .build());

        opts.addOption(Option.builder("p")
                .longOpt("prefix")
                .hasArg()
                .argName("префикс")
                .desc("Префикс имен выходных файлов")
                .build());

        opts.addOption(Option.builder("a")
                .longOpt("append")
                .desc("Режим добавления в существующие файлы")
                .build());

        opts.addOption(Option.builder("s")
                .longOpt("short")
                .desc("Краткая статистика")
                .build());

        opts.addOption(Option.builder("f")
                .longOpt("full")
                .desc("Полная статистика")
                .build());

        opts.addOption(Option.builder("h")
                .longOpt("help")
                .desc("Показать справку")
                .build());

        return opts;
    }

    public AppConfig parse(String[] args) throws ParseException {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd = parser.parse(options, args);

        AppConfig config = new AppConfig();

        if (cmd.hasOption("o")) {
            config.setOutputPath(cmd.getOptionValue("o"));
        }

        if (cmd.hasOption("p")) {
            config.setPrefix(cmd.getOptionValue("p"));
        }

        if (cmd.hasOption("a")) {
            config.setAppendMode(true);
        }

        if (cmd.hasOption("s")) {
            config.setShortStatistics(true);
        }

        if (cmd.hasOption("f")) {
            config.setFullStatistics(true);
        }

        for (String arg : cmd.getArgs()) {
            config.addInputFile(arg);
        }

        return config;
    }
}
