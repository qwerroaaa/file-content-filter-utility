package org.example.classify;

public class DefineClassify implements LineClassify {
    @Override
    public Types classify(String raw) {
        String line = raw == null ? "" : raw.trim();

        if (line.isEmpty()) return Types.STRING;
        if (isInteger(line)) return Types.INTEGER;
        if (isFloat(line)) return Types.FLOAT;

        return Types.STRING;
    }

    private boolean isInteger (String line) {
        int length = line.length();
        int index = 0;
        char firstChar = line.charAt(0);
        if (firstChar == '+' || firstChar == '-') {
            if (length == 1) return false;
            index = 1;
        }
        for (; index < length; index++) {
            char chosenChar = line.charAt(index);
            if (chosenChar < '0' || chosenChar > '9') return false;
        }

        return true;
    }

    private boolean isFloat (String line) {
        if (!(line.indexOf('.') >= 0 || line.indexOf(('e')) >= 0 || line.indexOf('E') >= 0)) return false;

        try {
            double value = Double.parseDouble(line);
            return Double.isFinite(value);
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
