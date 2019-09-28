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
        Alphabet createdFromArray = Alphabet.ofChars('s', 't', 'u', 'v');
        Alphabet createdFromRange = Alphabet.ofRange('s', 'v');
        assertEquals(createdFromArray, createdFromRange);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testRequiresDistinctChars() {
        Alphabet.ofChars('a', 'b', 'b', 'c');
    }
}
