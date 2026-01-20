package org.example;

import org.example.config.AppConfig;
import org.example.parser.ArgumentParser;
import org.example.processer.FileProcessor;
import org.example.statistics.StatisticsPrinter;

public class Main {
    public static void main(String[] args) {
        if (args.length == 0) {
            printUsage();
            return;
        }

        try {
            ArgumentParser argumentParser = new ArgumentParser();
            AppConfig config = argumentParser.parse(args);

            if (config.getInputFiles().isEmpty()) {
                System.err.println("Ошибка: не указаны входные файлы");
                printUsage();
                return;
            }

            FileProcessor processor = new FileProcessor(config);
            processor.process();

            if (config.isShortStatistics() || config.isFullStatistics()) {
                StatisticsPrinter printer = new StatisticsPrinter();
                printer.print(processor.getStatistics(), config.isFullStatistics());
            }

        } catch (Exception e) {
            System.err.println("Критическая ошибка: " + e.getMessage());
        }

    }

    private static void printUsage() {
        System.out.println("Использование: java -jar target/file-content-filter-utility-1.0-SNAPSHOT.jar [опции] <файл1> <файл2> ...");
        System.out.println();
        System.out.println("Опции:");
        System.out.println("  -o <путь>     Путь для выходных файлов (по умолчанию: текущая директория)");
        System.out.println("  -p <префикс>  Префикс имен выходных файлов");
        System.out.println("  -a            Режим добавления в существующие файлы");
        System.out.println("  -s            Краткая статистика");
        System.out.println("  -f            Полная статистика");
        System.out.println();
        System.out.println("Примеры:");
        System.out.println("  java -jar target/file-content-filter-utility-1.0-SNAPSHOT.jar in1.txt in2.txt");
        System.out.println("  java -jar target/file-content-filter-utility-1.0-SNAPSHOT.jar -s -o /output -p result_ in1.txt in2.txt");
        System.out.println("  java -jar target/file-content-filter-utility-1.0-SNAPSHOT.jar -f -a in1.txt in2.txt");
    }
}