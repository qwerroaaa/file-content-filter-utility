package org.example.statistics;

public class StringStatistics implements Statistics {
    private long count = 0;
    private int minLength = Integer.MAX_VALUE;
    private int maxLength = 0;

    @Override
    public void add(String value) {
        if (value != null) {
            count++;
            int length = value.length();

            if (length < minLength) {
                minLength = length;
            }
            if (length > maxLength) {
                maxLength = length;
            }
        }
    }

    @Override
    public long getCount() {
        return count;
    }

    @Override
    public String getShortStats() {
        return String.format("Строк: %d", count);
    }

    @Override
    public String getFullStats() {
        if (count == 0) {
            return getShortStats();
        }

        return String.format(
                "Строки:\n" +
                        "  Количество: %d\n" +
                        "  Минимальная длина: %d\n" +
                        "  Максимальная длина: %d",
                count, minLength, maxLength
        );
    }

    @Override
    public String getTypeName() {
        return "strings";
    }
}
