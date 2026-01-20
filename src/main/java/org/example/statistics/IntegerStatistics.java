package org.example.statistics;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

public class IntegerStatistics implements Statistics {
    private long count = 0;
    private BigInteger min = null;
    private BigInteger max = null;
    private BigInteger sum = BigInteger.ZERO;

    @Override
    public void add(String value) {
        try {
            BigInteger number = new BigInteger(value.trim());
            count++;
            sum = sum.add(number);

            if (min == null || number.compareTo(min) < 0) {
                min = number;
            }
            if (max == null || number.compareTo(max) > 0) {
                max = number;
            }
        } catch (NumberFormatException e) {
            // Игнорируем невалидные значения
        }
    }

    @Override
    public long getCount() {
        return count;
    }

    @Override
    public String getShortStats() {
        return String.format("Целых чисел: %d", count);
    }

    @Override
    public String getFullStats() {
        if (count == 0) {
            return getShortStats();
        }

        BigDecimal average = new BigDecimal(sum)
                .divide(BigDecimal.valueOf(count), 10, RoundingMode.HALF_UP);

        return String.format(
                "Целые числа:\n" +
                        "  Количество: %d\n" +
                        "  Минимум: %s\n" +
                        "  Максимум: %s\n" +
                        "  Сумма: %s\n" +
                        "  Среднее: %s",
                count, min, max, sum, average.stripTrailingZeros().toPlainString()
        );
    }

    @Override
    public String getTypeName() {
        return "integers";
    }
}
