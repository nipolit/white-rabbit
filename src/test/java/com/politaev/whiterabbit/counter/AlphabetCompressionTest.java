package com.politaev.whiterabbit.counter;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class AlphabetCompressionTest {
    @Test
    public void testCharIndexChanged() {
        Alphabet sourceAlphabet = Alphabet.ofRange('a', 'd');
        Alphabet targetAlphabet = Alphabet.ofChars('a', 'c', 'd');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(sourceAlphabet).to(targetAlphabet);
        assertEquals(3, alphabetCompression.getSourceIndex(2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSourceAlphabetRequiredToContainTargetAlphabet() {
        Alphabet sourceAlphabet = Alphabet.ofRange('a', 'd');
        Alphabet targetAlphabet = Alphabet.ofChars('a', 'c', 'e');
        AlphabetCompression.from(sourceAlphabet).to(targetAlphabet);
    }
}
