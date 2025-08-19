package org.example.classify;

public class DefineClassify implements LineClassify {
    @Override
    public Types classify(String raw) {
        String s = raw == null ? "" : raw.trim();

        if (s.isEmpty()) return Types.STRING;
        if (isInteger(s)) return Types.INTEGER;
        if (isFloat(s)) return Types.FLOAT;

        return Types.STRING;
    }

    private boolean isInteger (String s) {
        int n = s.length();
        int i = 0;
        char s0 = s.charAt(0);
        if (s0 == '+' || s0 == '-') {
            if (n == 1) return false;
            i = 1;
        }
        for (; i < n; i++) {
            char c = s.charAt(i);
            if (c < '0' || c > '9') return false;
        }

        return true;
    }

    private boolean isFloat (String s) {
        if (!(s.indexOf('.') >= 0 || s.indexOf(('e')) >= 0 || s.indexOf('E') >= 0)) return false;

        try {
            double v = Double.parseDouble(s);
            return Double.isFinite(v);
        } catch (NumberFormatException ex) {
            return false;
        }
    }
}
