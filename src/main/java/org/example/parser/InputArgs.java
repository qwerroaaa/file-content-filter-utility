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
                        .desc("Режим добавления в существующие файлы (append)")
                        .build()
        );

        CommandLineParser parser = new DefaultParser();
        HelpFormatter help = new HelpFormatter();

        try {
            CommandLine cmd = parser.parse(options, args);

            InputArgs cfg = new InputArgs();

            // -o
            String outPath = cmd.getOptionValue("o", ".");
            cfg.outputDir = Paths.get(outPath);

            // -a
            cfg.append = cmd.hasOption("a");

            for (String file : cmd.getArgs()) {
                cfg.inputs.add(Paths.get(file));
            }

            return cfg;
        } catch (ParseException e) {
            System.err.println("Ошибка парсинга аргументов: " + e.getMessage());
            help.printHelp("java -jar app.jar [options] <files...>", options);
            System.exit(2);
            return null;
        }
    }
}
