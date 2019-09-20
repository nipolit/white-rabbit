package com.politaev.whiterabbit.counter;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

final class Alphabet {
    static final Alphabet LATIN = ofRange('a', 'z');
    private final char[] chars;

    Alphabet(char[] chars) {
        this.chars = chars;
    }

    static Alphabet ofRange(char firstChar, char lastChar) {
        char[] allCharsInRange = charsInRangeToArray(firstChar, lastChar);
        return new Alphabet(allCharsInRange);
    }

    private static char[] charsInRangeToArray(char firstChar, char lastChar) {
        return IntStream.rangeClosed(firstChar, lastChar)
                .mapToObj(charIndex -> (char) charIndex)
                .map(Object::toString)
                .collect(Collectors.joining())
                .toCharArray();
    }

    int getNumberOfChars() {
        return chars.length;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Alphabet otherAlphabet = (Alphabet) other;
        return Arrays.equals(chars, otherAlphabet.chars);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(chars);
    }
}
