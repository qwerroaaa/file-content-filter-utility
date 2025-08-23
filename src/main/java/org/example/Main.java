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

        Path outInts   = cfg.outputDir.resolve("integers.txt");
        Path outFloats = cfg.outputDir.resolve("floats.txt");
        Path outStrs   = cfg.outputDir.resolve("strings.txt");

        try (OutputResult sinks = new OutputResult(outInts, outFloats, outStrs, cfg.append)) {
            FileProcessor proc = new FileProcessor(new DefineClassify(), sinks);
            for (Path in : cfg.inputs) {
                proc.process(in);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}