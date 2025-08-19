package org.example.classify;

public interface LineClassify {
    enum Types {INTEGER, STRING, FLOAT}

    Types classify(String raw);
}
