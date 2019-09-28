package com.politaev.whiterabbit.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class AlphabetSubsetCharCounterTest {
    private AlphabetSubsetCharCounter alphabetSubsetCharCounter;

    @Before
    public void setUp() {
        LatinCharCounter latinCharCounter = new LatinCharCounter();
        alphabetSubsetCharCounter = AlphabetSubsetCharCounter.of(latinCharCounter, 'e', 'n', 's', 't', 'x');
    }

    private CharCount countChars(String string) {
        return alphabetSubsetCharCounter.countChars(string);
    }

    @Test
    public void testEqualStrings() {
        CharCount charCount1 = countChars("test");
        CharCount charCount2 = countChars("sett");
        assertEquals(charCount1, charCount2);
    }

    @Test
    public void testDifferentStrings() {
        CharCount charCount1 = countChars("test");
        CharCount charCount2 = countChars("next");
        assertNotEquals(charCount1, charCount2);
    }
}
