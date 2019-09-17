package com.politaev.whiterabbit.counter;

public class CharCounter {
    private static int ARRAY_ZERO_ELEMENT_CHAR_INDEX = (int) 'a';
    private static int COUNT_ARRAY_SIZE = 'z' - 'a' + 1;

    public CharCount countChars(String string) {
        int[] charCountArray = countCharsToArray(string);
        return new CharCount(charCountArray);
    }

    private int[] countCharsToArray(String string) {
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
