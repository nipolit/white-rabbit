package com.politaev.whiterabbit.counter;

class LatinCharCounter {
    private static final int ARRAY_ZERO_ELEMENT_CHAR_INDEX = (int) 'a';
    private static final int COUNT_ARRAY_SIZE = Alphabet.LATIN.getNumberOfChars();

    private final String string;
    private int[] charCountArray;

    LatinCharCounter(String string) {
        this.string = string;
    }

    CharCount countChars() {
        countCharsToArray();
        return new CharCount(Alphabet.LATIN, charCountArray);
    }

    private void countCharsToArray() {
        charCountArray = initCharCountArray();
        string.chars()
                .map(this::charIndexToArrayIndex)
                .filter(this::indexWithinArray)
                .forEach(this::incrementCharCount);
    }

    private int[] initCharCountArray() {
        return new int[COUNT_ARRAY_SIZE];
    }

    private int charIndexToArrayIndex(int charIndex) {
        return charIndex - ARRAY_ZERO_ELEMENT_CHAR_INDEX;
    }

    private boolean indexWithinArray(int index) {
        return index >= 0 && index < COUNT_ARRAY_SIZE;
    }

    private void incrementCharCount(int charIndexInArray) {
        charCountArray[charIndexInArray]++;
    }
}
