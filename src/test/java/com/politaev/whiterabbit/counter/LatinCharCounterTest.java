package com.politaev.whiterabbit.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class LatinCharCounterTest {
    private LatinCharCounter latinCharCounter;

    @Before
    public void setUp() {
        latinCharCounter = new LatinCharCounter();
    }

    private CharCount countChars(String string) {
        return latinCharCounter.countChars(string);
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

    @Test
    public void testLowerBoundaryCharsCount() {
        CharCount charCount1 = countChars("aa");
        CharCount charCount2 = countChars("aaaaa");
        assertNotEquals(charCount1, charCount2);
    }

    @Test
    public void testHigherBoundaryCharsCount() {
        CharCount charCount1 = countChars("zz");
        CharCount charCount2 = countChars("zzzzz");
        assertNotEquals(charCount1, charCount2);
    }

    @Test
    public void testIgnoredCharacters() {
        CharCount charCount1 = countChars("tests");
        CharCount charCount2 = countChars("test's");
        assertEquals(charCount1, charCount2);
    }
}
