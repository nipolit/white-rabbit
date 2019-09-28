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
        requireEqualAlphabetsForOperation(otherCharCount.alphabet);
    }

    private void requireEqualAlphabetsForOperation(Alphabet otherAlphabet) {
        requireEqualAlphabets(otherAlphabet, "Operations must be performed on CharCounts using the same Alphabet.");
    }

    private void requireEqualAlphabets(Alphabet otherAlphabet, String message) {
        if (!alphabet.equals(otherAlphabet)) {
            throw new IllegalArgumentException(message);
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

    public boolean includes(CharCount otherCharCount) {
        requireSuitableForBinaryOperation(otherCharCount);
        return countingArrayIncludesOther(otherCharCount.countOfEveryChar);
    }

    private boolean countingArrayIncludesOther(int[] otherCountingArray) {
        return IntStream.range(0, countOfEveryChar.length)
                .allMatch(i -> countOfEveryChar[i] >= otherCountingArray[i]);
    }

    public CharCount compress(AlphabetCompression alphabetCompression) {
        requireSuitableAlphabetCompression(alphabetCompression);
        int[] compressedCountingArray = compressCountingArray(alphabetCompression);
        return new CharCount(alphabetCompression.getTargetAlphabet(), compressedCountingArray);
    }

    private void requireSuitableAlphabetCompression(AlphabetCompression alphabetCompression) {
        Objects.requireNonNull(alphabetCompression);
        requireEqualAlphabets(alphabetCompression.getSourceAlphabet(), "Source alphabet of AlphabetCompression must match the one of CharCount");
    }

    private int[] compressCountingArray(AlphabetCompression alphabetCompression) {
        return IntStream.range(0, alphabetCompression.getTargetAlphabet().getNumberOfChars())
                .map(alphabetCompression::getSourceIndex)
                .map(sourceArrayIndex -> countOfEveryChar[sourceArrayIndex])
                .toArray();
    }

    public int totalChars() {
        return IntStream.of(countOfEveryChar).sum();
    }

    public Alphabet getAlphabet() {
        return alphabet;
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
