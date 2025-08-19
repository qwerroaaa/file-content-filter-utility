package org.example;

import org.example.parser.InputArgs;
import org.example.classify.DefineClassify;
import org.example.io.FileProcessor;
import org.example.io.OutputResult;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Main {
    public static void main(String[] args) {
        InputArgs cfg = InputArgs.parse(args);

        Path outInts   = Paths.get("output/integers.txt");
        Path outFloats = Paths.get("output/floats.txt");
        Path outStrs   = Paths.get("output/strings.txt");

        try (OutputResult sinks = new OutputResult(outInts, outFloats, outStrs)) {
            FileProcessor proc = new FileProcessor(new DefineClassify(), sinks);
            for (Path in : cfg.inputs) {
                proc.process(in); // читаем в порядке как в командной строке
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}