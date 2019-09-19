package com.politaev.whiterabbit.counter;

class LatinCharCounter {
    private static final int ARRAY_ZERO_ELEMENT_CHAR_INDEX = (int) 'a';
    private static final int COUNT_ARRAY_SIZE = 'z' - 'a' + 1;

    private final String string;

    LatinCharCounter(String string) {
        this.string = string;
    }

    CharCount countChars() {
        int[] charCountArray = countCharsToArray();
        return new CharCount(charCountArray);
    }

    private int[] countCharsToArray() {
        int[] charCountArray = initCharCountArray();
        string.chars()
                .map(charIndex -> charIndex - ARRAY_ZERO_ELEMENT_CHAR_INDEX)
                .filter(arrayIndex -> arrayIndex >= 0 && arrayIndex < COUNT_ARRAY_SIZE)
                .forEach(arrayIndex -> charCountArray[arrayIndex]++);
        return charCountArray;
    }

    private int[] initCharCountArray() {
        return new int[COUNT_ARRAY_SIZE];
    }
}
