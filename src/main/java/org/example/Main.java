package org.example;

import org.example.parser.InputArgs;
import org.example.classify.DefineClassify;
import org.example.io.FileProcessor;
import org.example.io.OutputResult;
import org.example.stat.Stats;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        InputArgs inputArgs = InputArgs.parse(args);

        Path integersOutputFile = inputArgs.outputDir.resolve(inputArgs.prefix + "integers.txt");
        Path floatsOutputFile = inputArgs.outputDir.resolve(inputArgs.prefix + "floats.txt");
        Path stringsOutputFile   = inputArgs.outputDir.resolve(inputArgs.prefix + "strings.txt");

        Stats statistics = new Stats();

        try (OutputResult outputResult = new OutputResult(integersOutputFile, floatsOutputFile, stringsOutputFile, inputArgs.append, statistics)) {
            FileProcessor fileProcessor = new FileProcessor(new DefineClassify(), outputResult);
            for (Path inputFile : inputArgs.inputs) {
                fileProcessor.process(inputFile);
            }

            if (inputArgs.printShortStat) {
                System.out.println("Краткая статистика:");
                System.out.println("Кол-во int значений: " + statistics.getIntsCount());
                System.out.println("Кол-во float значений: " + statistics.getFloatsCount());
                System.out.println("Кол-во string значений: " + statistics.getStringsCount());
                System.out.println("Общее количество значений: " + statistics.getTotalCount());
            }

            if (inputArgs.printFullStat) {
                System.out.println("Полная статистика:");
                System.out.println("Кол-во int значений: " + statistics.getIntsCount());
                System.out.println("Кол-во float значений: " + statistics.getFloatsCount());
                System.out.println("Кол-во string значений: " + statistics.getStringsCount());
                System.out.println("Общее количество значений: " + statistics.getTotalCount());
                System.out.println("Integers:");
                System.out.println("min = " + statistics.getMinInt());
                System.out.println("max = " + statistics.getMaxInt());
                System.out.println("sum = " + statistics.getSumInt());
                System.out.println("avg = " + statistics.getAvgInt());
                System.out.println("Floats:");
                System.out.println("min = " + statistics.getMinFloat());
                System.out.println("max = " + statistics.getMaxFloat());
                System.out.println("sum = " + statistics.getSumFloat());
                System.out.println("avg = " + statistics.getAvgFloat());
                System.out.println("Strings:");
                System.out.println("short string = " + statistics.getShortestStr());
                System.out.println("long string = " + statistics.getLongestStr());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}