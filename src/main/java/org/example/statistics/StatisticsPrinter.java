package org.example.statistics;

import java.util.Map;

public class StatisticsPrinter {
    public void print(Map<String, Statistics> statisticsMap, boolean fullStats) {
        System.out.println();
        System.out.println("=== СТАТИСТИКА ===");
        System.out.println();

        Statistics intStats = statisticsMap.get("integers");
        Statistics floatStats = statisticsMap.get("floats");
        Statistics stringStats = statisticsMap.get("strings");

        if (fullStats) {
            printFull(intStats);
            printFull(floatStats);
            printFull(stringStats);
        } else {
            printShort(intStats);
            printShort(floatStats);
            printShort(stringStats);
        }
    }

    private void printShort(Statistics stats) {
        if (stats != null && stats.getCount() > 0) {
            System.out.println(stats.getShortStats());
        }
    }

    private void printFull(Statistics stats) {
        if (stats != null && stats.getCount() > 0) {
            System.out.println(stats.getFullStats());
            System.out.println();
        }
    }
}
