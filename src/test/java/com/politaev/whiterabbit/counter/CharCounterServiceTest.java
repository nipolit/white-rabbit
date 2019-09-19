package com.politaev.whiterabbit.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CharCounterServiceTest {
    CharCounterService charCounterService;

    @Before
    public void initTest() {
        charCounterService = new CharCounterService();
    }

    @Test
    public void testEqualStrings() {
        CharCount charCount1 = charCounterService.countChars("test");
        CharCount charCount2 = charCounterService.countChars("sett");
        assertEquals(charCount1, charCount2);
    }

    @Test
    public void testDifferentStrings() {
        CharCount charCount1 = charCounterService.countChars("test");
        CharCount charCount2 = charCounterService.countChars("next");
        assertNotEquals(charCount1, charCount2);
    }

    @Test
    public void testLowerBoundaryCharsCount() {
        CharCount charCount1 = charCounterService.countChars("aa");
        CharCount charCount2 = charCounterService.countChars("aaaaa");
        assertNotEquals(charCount1, charCount2);
    }

    @Test
    public void testHigherBoundaryCharsCount() {
        CharCount charCount1 = charCounterService.countChars("zz");
        CharCount charCount2 = charCounterService.countChars("zzzzz");
        assertNotEquals(charCount1, charCount2);
    }

    @Test
    public void testIgnoredCharacters() {
        CharCount charCount1 = charCounterService.countChars("tests");
        CharCount charCount2 = charCounterService.countChars("test's");
        assertEquals(charCount1, charCount2);
    }
}
