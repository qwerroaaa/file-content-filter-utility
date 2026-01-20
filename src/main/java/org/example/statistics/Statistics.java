package org.example.statistics;

public interface Statistics {
    void add(String value);

    long getCount();

    String getShortStats();

    String getFullStats();

    String getTypeName();
}
