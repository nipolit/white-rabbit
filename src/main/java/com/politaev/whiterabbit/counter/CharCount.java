package com.politaev.whiterabbit.counter;

import java.util.Arrays;

public final class CharCount {
    private final int[] countOfEveryChar;

    CharCount(int[] countOfEveryChar) {
        this.countOfEveryChar = countOfEveryChar;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        CharCount otherCharCount = (CharCount) other;
        return Arrays.equals(countOfEveryChar, otherCharCount.countOfEveryChar);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(countOfEveryChar);
    }
}
