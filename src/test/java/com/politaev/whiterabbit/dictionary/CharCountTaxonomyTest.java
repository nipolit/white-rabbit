package com.politaev.whiterabbit.dictionary;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.counter.CharCounter;
import com.politaev.whiterabbit.counter.LatinCharCounter;
import org.junit.Before;
import org.junit.Test;

import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class CharCountTaxonomyTest {
    private static final String[] WORDS = new String[]{
            "a", "b",
            "aa", "ab", "bb",
            "aaa", "aab", "abb", "bbb"
    };
    private CharCountTaxonomy taxonomy;
    private CharCounter charCounter;

    @Before
    public void setUp() {
        taxonomy = new CharCountTaxonomy();
        charCounter = new LatinCharCounter();
        populateTaxonomy();
    }

    private void populateTaxonomy() {
        Stream.of(WORDS)
                .map(charCounter::countChars)
                .forEach(taxonomy::add);
    }

    @Test
    public void testStreamAllCharCounts() {
        long charCountsStreamed = taxonomy.charCountsOfLimitedTotalChars(100).count();
        assertEquals(WORDS.length, charCountsStreamed);
    }

    @Test
    public void testStreamCharCountsLimitedByTotalChars() {
        long charCountsStreamed = taxonomy.charCountsOfLimitedTotalChars(2).count();
        assertEquals(5, charCountsStreamed);
    }

    @Test
    public void testStreamCharCountsFromSomeCharCount() {
        CharCount from = charCounter.countChars("b");
        long charCountsStreamed = taxonomy.charCountsOfLimitedTotalCharsStartingFromElement(from, 2).count();
        assertEquals(4, charCountsStreamed);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamSubsetOfCharCountsWithIncorrectBoundaries() {
        CharCount from = charCounter.countChars("aab");
        taxonomy.charCountsOfLimitedTotalCharsStartingFromElement(from, 2);
    }
}
