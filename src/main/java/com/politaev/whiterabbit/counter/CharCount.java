package com.politaev.whiterabbit.counter;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.IntStream;

public final class CharCount {
    private final Alphabet alphabet;
    private final int[] countOfEveryChar;

    public static CharCount countLatinChars(String string) {
        LatinCharCounter latinCharCounter = new LatinCharCounter(string);
        return latinCharCounter.countChars();
    }

    CharCount(Alphabet alphabet, int[] countOfEveryChar) {
        this.alphabet = alphabet;
        this.countOfEveryChar = countOfEveryChar;
    }

    public CharCount add(CharCount otherCharCount) {
        requireSuitableForBinaryOperation(otherCharCount);
        int[] resultCountingArray = sumOfCountingArrays(otherCharCount.countOfEveryChar);
        return new CharCount(alphabet, resultCountingArray);
    }

    private void requireSuitableForBinaryOperation(CharCount otherCharCount) {
        Objects.requireNonNull(otherCharCount);
        requireEqualAlphabets(otherCharCount);
    }

    private void requireEqualAlphabets(CharCount otherCharCount) {
        if (!alphabet.equals(otherCharCount.alphabet)) {
            throw new IllegalArgumentException("Operations must be performed on CharCounts using the same Alphabet.");
        }
    }

    private int[] sumOfCountingArrays(int[] otherCountingArray) {
        return IntStream.range(0, countOfEveryChar.length)
                .map(i -> countOfEveryChar[i] + otherCountingArray[i])
                .toArray();
    }

    public CharCount subtract(CharCount otherCharCount) {
        requireSuitableForBinaryOperation(otherCharCount);
        int[] resultCountingArray = diffOfCountingArrays(otherCharCount.countOfEveryChar);
        return new CharCount(alphabet, resultCountingArray);
    }

    private int[] diffOfCountingArrays(int[] otherCountingArray) {
        return IntStream.range(0, countOfEveryChar.length)
                .map(i -> countOfEveryChar[i] - otherCountingArray[i])
                .toArray();
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
