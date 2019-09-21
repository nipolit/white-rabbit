package com.politaev.whiterabbit.counter;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class CharCountTest {
    CharCounterService charCounterService;

    @Before
    public void initTest() {
        charCounterService = new CharCounterService();
    }

    @Test
    public void testCharCountSum() {
        String string1 = "benedict", string2 = "cumberbatch";
        CharCount charCount1 = charCounterService.countChars(string1);
        CharCount charCount2 = charCounterService.countChars(string2);
        CharCount actualSum = charCount1.add(charCount2);
        CharCount expectedSum = charCounterService.countChars(string1 + string2);
        assertEquals(expectedSum, actualSum);
    }
}
