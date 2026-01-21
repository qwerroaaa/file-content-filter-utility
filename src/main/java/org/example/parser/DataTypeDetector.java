package org.example.parser;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.regex.Pattern;

public class DataTypeDetector {
    public enum DataType {
        INTEGER,
        FLOAT,
        STRING
    }

    private static final Pattern INTEGER_PATTERN = Pattern.compile("^-?(0|[1-9]\\d*)$");
    private static final Pattern FLOAT_PATTERN = Pattern.compile(
            "^-?(0|[1-9]\\d*)\\.\\d+([eE][+-]?\\d+)?$"
    );
    private static final Pattern SCIENTIFIC_PATTERN = Pattern.compile(
            "^-?(0|[1-9]\\d*)[eE][+-]?\\d+$"
    );

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
        if (!INTEGER_PATTERN.matcher(value).matches()) {
            return false;
        }

        try {
            new BigInteger(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isFloat(String value) {
        boolean matchesFloatPattern = FLOAT_PATTERN.matcher(value).matches();
        boolean matchesScientificPattern = SCIENTIFIC_PATTERN.matcher(value).matches();

        if (!matchesFloatPattern && !matchesScientificPattern) {
            return false;
        }

        try {
            new BigDecimal(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
