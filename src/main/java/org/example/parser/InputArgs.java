package org.example.parser;

import org.apache.commons.cli.*;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputArgs {
    public final List<Path> inputs = new ArrayList<>();
    public boolean append = false;
    public Path outputDir = Paths.get(".");
    public String prefix = "";
    public boolean printShortStat = false;
    public boolean printFullStat = false;

    public static InputArgs parse(String[] args) {
        Options options = new Options();
        options.addOption(
            Option.builder("o")
                    .longOpt("out")
                    .hasArg()
                    .argName("dir")
                    .desc("Путь для сохранения результатов")
                    .build()
        );

        options.addOption(
                Option.builder("a")
                        .longOpt("append")
                        .desc("Режим добавления в существующие файлы")
                        .build()
        );

        options.addOption(
                Option.builder("p")
                        .longOpt("prefix")
                        .hasArg()
                        .argName("prefix")
                        .desc("Добавление префикса к выходным файлам")
                        .build()
        );

        options.addOption(
                Option.builder("s")
                        .longOpt("shortStat")
                        .desc("Вывод краткой статистики")
                        .build()
        );

        options.addOption(
                Option.builder("f")
                        .longOpt("fullStat")
                        .desc("Вывод полной статистики")
                        .build()
        );

        CommandLineParser parser = new DefaultParser();
        HelpFormatter help = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);

            InputArgs inputArgs = new InputArgs();

            String outPath = cmd.getOptionValue("o", ".");
            inputArgs.outputDir = Paths.get(outPath);
            inputArgs.append = cmd.hasOption("a");
            inputArgs.prefix = cmd.getOptionValue("p", "");
            inputArgs.printShortStat = cmd.hasOption("s");
            inputArgs.printFullStat = cmd.hasOption("f");

            for (String file : cmd.getArgs()) {
                inputArgs.inputs.add(Paths.get(file));
            }

            return inputArgs;
        } catch (ParseException e) {
            System.err.println("Ошибка парсинга аргументов: " + e.getMessage());
            help.printHelp("java -jar util.jar [options] [files]", options);
            return null;
        }
    }
}
