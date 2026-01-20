package org.example.statistics;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class FloatStatistics implements Statistics {
    private long count = 0;
    private BigDecimal min = null;
    private BigDecimal max = null;
    private BigDecimal sum = BigDecimal.ZERO;

    @Override
    public void add(String value) {
        try {
            BigDecimal number = new BigDecimal(value.trim());
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
        return String.format("Вещественных чисел: %d", count);
    }

    @Override
    public String getFullStats() {
        if (count == 0) {
            return getShortStats();
        }

        BigDecimal average = sum.divide(BigDecimal.valueOf(count), 15, RoundingMode.HALF_UP);

        return String.format(
                "Вещественные числа:\n" +
                        "  Количество: %d\n" +
                        "  Минимум: %s\n" +
                        "  Максимум: %s\n" +
                        "  Сумма: %s\n" +
                        "  Среднее: %s",
                count,
                min.toPlainString(),
                max.toPlainString(),
                sum.toPlainString(),
                average.stripTrailingZeros().toPlainString()
        );
    }

    @Override
    public String getTypeName() {
        return "floats";
    }
}
