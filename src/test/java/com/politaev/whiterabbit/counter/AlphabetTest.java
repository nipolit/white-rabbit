package com.politaev.whiterabbit.counter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlphabetTest {
    @Test
    public void testNumberOfLatinLetters() {
        assertEquals(26, Alphabet.LATIN.getNumberOfChars());
    }

    @Test
    public void testAlphabetCreatedFromCharRange() {
        char[] chars = new char[]{'s', 't', 'u', 'v'};
        Alphabet createdFromArray = Alphabet.ofChars(chars);
        Alphabet createdFromRange = Alphabet.ofRange('s', 'v');
        assertEquals(createdFromArray, createdFromRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequiresDistinctChars() {
        char[] chars = new char[]{'a', 'b', 'b', 'c'};
        Alphabet.ofChars(chars);
    }
}
