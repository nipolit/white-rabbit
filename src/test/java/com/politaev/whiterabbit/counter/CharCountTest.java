package com.politaev.whiterabbit.counter;

import org.junit.Test;

import static com.politaev.whiterabbit.counter.CharCount.countLatinChars;
import static org.junit.Assert.assertEquals;

public class CharCountTest {
    @Test
    public void testCharCountSum() {
        String string1 = "benedict", string2 = "cumberbatch";
        CharCount charCount1 = countLatinChars(string1);
        CharCount charCount2 = countLatinChars(string2);
        CharCount actualSum = charCount1.add(charCount2);
        CharCount expectedSum = countLatinChars(string1 + string2);
        assertEquals(expectedSum, actualSum);
    }
}