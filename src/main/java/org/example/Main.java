package org.example;

import org.example.parser.InputArgs;
import org.example.classify.DefineClassify;
import org.example.io.FileProcessor;
import org.example.io.OutputResult;
import java.nio.file.Path;

public class Main {
    public static void main(String[] args) {
        InputArgs cfg = InputArgs.parse(args);

        Path outInts   = cfg.outputDir.resolve(cfg.prefix + "integers.txt");
        Path outFloats = cfg.outputDir.resolve(cfg.prefix + "floats.txt");
        Path outStrs   = cfg.outputDir.resolve(cfg.prefix + "strings.txt");

        try (OutputResult sinks = new OutputResult(outInts, outFloats, outStrs, cfg.append)) {
            FileProcessor proc = new FileProcessor(new DefineClassify(), sinks);
            for (Path in : cfg.inputs) {
                proc.process(in);
            }

            if (cfg.printShortStat) {
                System.out.println("Краткая статистика:");
                System.out.println("Кол-во int значений: " + sinks.getIntsCount());
                System.out.println("Кол-во float значений: " + sinks.getFloatsCount());
                System.out.println("Кол-во string значений: " + sinks.getStringsCount());
                System.out.println("Общее количество значений: " + sinks.getTotalCount());
            }

            if (cfg.printFullStat) {
                System.out.println("Полная статистика:");
                System.out.println("Кол-во int значений: " + sinks.getIntsCount());
                System.out.println("Кол-во float значений: " + sinks.getFloatsCount());
                System.out.println("Кол-во string значений: " + sinks.getStringsCount());
                System.out.println("Общее количество значений: " + sinks.getTotalCount());
                System.out.println("Integers:");
                System.out.println("min = " + sinks.getMinInt());
                System.out.println("max = " + sinks.getMaxInt());
                System.out.println("sum = " + sinks.getSumInt());
                System.out.println("avg = " + sinks.getAvgInt());
                System.out.println("Floats:");
                System.out.println("min = " + sinks.getMinFloat());
                System.out.println("max = " + sinks.getMaxFloat());
                System.out.println("sum = " + sinks.getSumFloat());
                System.out.println("avg = " + sinks.getAvgFloat());
                System.out.println("Strings:");
                System.out.println("short string = " + sinks.getShortestStr());
                System.out.println("long string = " + sinks.getLongestStr());
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}