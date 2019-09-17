package com.politaev.whiterabbit.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CharCounterTest {
    CharCounter charCounter;

    @Before
    public void initTest() {
        charCounter = new CharCounter();
    }

    @Test
    public void testEqualStrings() {
        CharCount charCount1 = charCounter.countChars("test");
        CharCount charCount2 = charCounter.countChars("sett");
        assertEquals(charCount1, charCount2);
    }

    @Test
    public void testDifferentStrings() {
        CharCount charCount1 = charCounter.countChars("test");
        CharCount charCount2 = charCounter.countChars("next");
        assertNotEquals(charCount1, charCount2);
    }

    @Test
    public void testLowerBoundaryCharsCount() {
        CharCount charCount1 = charCounter.countChars("aa");
        CharCount charCount2 = charCounter.countChars("aaaaa");
        assertNotEquals(charCount1, charCount2);
    }

    @Test
    public void testHigherBoundaryCharsCount() {
        CharCount charCount1 = charCounter.countChars("zz");
        CharCount charCount2 = charCounter.countChars("zzzzz");
        assertNotEquals(charCount1, charCount2);
    }
}
