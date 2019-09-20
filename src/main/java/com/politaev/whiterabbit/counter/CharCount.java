package com.politaev.whiterabbit.counter;

import java.util.Arrays;
import java.util.Objects;

public final class CharCount {
    private final Alphabet alphabet;
    private final int[] countOfEveryChar;

    CharCount(Alphabet alphabet, int[] countOfEveryChar) {
        this.alphabet = alphabet;
        this.countOfEveryChar = countOfEveryChar;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        CharCount otherCharCount = (CharCount) other;
        return Objects.equals(alphabet, otherCharCount.alphabet) &&
                Arrays.equals(countOfEveryChar, otherCharCount.countOfEveryChar);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(countOfEveryChar);
    }
}
