package org.example.parser;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class InputArgs {
    public final List<Path> inputs = new ArrayList<>();

    public static InputArgs parse(String[] args) {
        InputArgs cfg = new InputArgs();
        for (String a : args) {
            if (!a.startsWith("-")) {
                cfg.inputs.add(Paths.get(a));
            }
        }
        return cfg;
    }
}
