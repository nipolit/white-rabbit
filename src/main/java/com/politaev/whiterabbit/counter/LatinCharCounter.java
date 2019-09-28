package com.politaev.whiterabbit.counter;

public class LatinCharCounter implements CharCounter {
    private static final int ARRAY_ZERO_ELEMENT_CHAR_INDEX = (int) 'a';
    private static final int COUNT_ARRAY_SIZE = Alphabet.LATIN.getNumberOfChars();

    @Override
    public CharCount countChars(String string) {
        int[] charCountArray = buildCharCountArray(string);
        return new CharCount(Alphabet.LATIN, charCountArray);
    }

    private int[] buildCharCountArray(String string) {
        CharCountArrayBuilder charCountArrayBuilder = new CharCountArrayBuilder(string);
        return charCountArrayBuilder.build();
    }

    private class CharCountArrayBuilder {
        private final String string;
        private int[] charCountArray;

        private CharCountArrayBuilder(String string) {
            this.string = string;
        }

        int[] build() {
            countCharsToArray();
            return charCountArray;
        }

        private void countCharsToArray() {
            charCountArray = initCharCountArray();
            string.chars()
                    .map(LatinCharCounter.this::charIndexToArrayIndex)
                    .filter(LatinCharCounter.this::indexWithinArray)
                    .forEach(this::incrementCharCount);
        }

        private void incrementCharCount(int charIndexInArray) {
            charCountArray[charIndexInArray]++;
        }
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

    @Override
    public Alphabet getAlphabet() {
        return Alphabet.LATIN;
    }
}
