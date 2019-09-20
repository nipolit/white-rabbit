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
        Alphabet createdFromArray = new Alphabet(chars);
        Alphabet createdFromRange = Alphabet.ofRange('s', 'v');
        assertEquals(createdFromArray, createdFromRange);
    }
}
