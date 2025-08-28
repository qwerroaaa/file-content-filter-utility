package org.example.stat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.MathContext;

public class Stats {
    private long intsCount = 0;
    private long floatsCount = 0;
    private long stringsCount = 0;

    private BigInteger minInt = null, maxInt = null, sumInt = BigInteger.ZERO;
    private BigDecimal minFloat = null, maxFloat = null, sumFloat = BigDecimal.ZERO;
    private String shortestStr = null, longestStr = null;

    private static final MathContext MC = MathContext.DECIMAL128;

    public void acceptInt(String s) {
        BigInteger value = new BigInteger(s);
        intsCount++;
        sumInt = sumInt.add(value);
        if (minInt == null || value.compareTo(minInt) < 0) minInt = value;
        if (maxInt == null || value.compareTo(maxInt) > 0) maxInt = value;
    }

    public void acceptFloat(String s) {
        BigDecimal value = new BigDecimal(s);
        floatsCount++;
        sumFloat = sumFloat.add(value);
        if (minFloat == null || value.compareTo(minFloat) < 0) minFloat = value;
        if (maxFloat == null || value.compareTo(maxFloat) > 0) maxFloat = value;
    }

    public void acceptString(String s) {
        stringsCount++;
        if (shortestStr == null || s.length() < shortestStr.length()) shortestStr = s;
        if (longestStr == null || s.length() > longestStr.length())  longestStr  = s;
    }

    public long getIntsCount()     { return intsCount; }
    public long getFloatsCount()   { return floatsCount; }
    public long getStringsCount()  { return stringsCount; }
    public long getTotalCount()    { return intsCount + floatsCount + stringsCount; }

    public BigInteger getMinInt()  { return minInt; }
    public BigInteger getMaxInt()  { return maxInt; }
    public BigDecimal getMinFloat(){ return minFloat; }
    public BigDecimal getMaxFloat(){ return maxFloat; }

    public BigDecimal getAvgInt() {
        return intsCount == 0 ? BigDecimal.ZERO
                : new BigDecimal(sumInt).divide(BigDecimal.valueOf(intsCount), MC);
    }

    public BigDecimal getAvgFloat() {
        return floatsCount == 0 ? BigDecimal.ZERO
                : sumFloat.divide(BigDecimal.valueOf(floatsCount), MC);
    }

    public String getShortestStr() { return shortestStr; }
    public String getLongestStr()  { return longestStr; }
    public BigInteger getSumInt()  { return sumInt; }
    public BigDecimal getSumFloat(){ return sumFloat; }
}
