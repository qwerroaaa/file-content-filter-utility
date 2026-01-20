package org.example.parser;

import java.math.BigDecimal;
import java.math.BigInteger;

public class DataTypeDetector {
    public enum DataType {
        INTEGER,
        FLOAT,
        STRING
    }

    public DataType detect(String value) {
        if (value == null || value.isEmpty()) {
            return DataType.STRING;
        }

        String trimmed = value.trim();

        if (isInteger(trimmed)) {
            return DataType.INTEGER;
        }

        if (isFloat(trimmed)) {
            return DataType.FLOAT;
        }

        return DataType.STRING;
    }

    private boolean isInteger(String value) {
        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String value) {
        try {
            if (!value.contains(".") && !value.toLowerCase().contains("e")) {
                return false;
            }
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
