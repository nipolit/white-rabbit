package com.politaev.whiterabbit.counter;

public class LatinCharCounter implements CharCounter {
    private static final int ARRAY_ZERO_ELEMENT_CHAR_INDEX = (int) 'a';
    private static final int COUNT_ARRAY_SIZE = Alphabet.LATIN.getNumberOfChars();

    @Override
    public CharCount countChars(String string) {
        int[] charCountArray = countCharsToArray(string);
        return new CharCount(Alphabet.LATIN, charCountArray);
    }

    private int[] countCharsToArray(String string) {
        CharCountArrayBuilder arrayBuilder = new CharCountArrayBuilder();
        string.chars()
                .map(LatinCharCounter.this::charIndexToArrayIndex)
                .filter(LatinCharCounter.this::indexWithinArray)
                .forEach(arrayBuilder::incrementCharCount);
        return arrayBuilder.result();
    }

    private class CharCountArrayBuilder {
        private int[] charCountArray = initCharCountArray();

        private int[] initCharCountArray() {
            return new int[COUNT_ARRAY_SIZE];
        }

        void incrementCharCount(int charIndexInArray) {
            charCountArray[charIndexInArray]++;
        }

        int[] result() {
            return charCountArray;
        }
    }

    private int charIndexToArrayIndex(int charIndex) {
        return charIndex - ARRAY_ZERO_ELEMENT_CHAR_INDEX;
    }

    private boolean indexWithinArray(int index) {
        return index >= 0 && index < COUNT_ARRAY_SIZE;
    }

    @Override
    public Alphabet getAlphabet() {
        return Alphabet.LATIN;
    }
}
