package com.politaev.whiterabbit.dictionary;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.counter.CharCounter;
import com.politaev.whiterabbit.counter.LatinCharCounter;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.fest.assertions.Assertions.assertThat;

public class CharCountTaxonomyTest {
    private static final String[] WORDS_OF_LENGTH_1 = new String[]{"a", "b"};
    private static final String[] WORDS_OF_LENGTH_2 = new String[]{"aa", "ab", "bb"};
    private static final String[] WORDS_OF_LENGTH_3 = new String[]{"aaa", "aab", "abb", "bbb"};
    private static final String[] WORDS_OF_LENGTH_4 = new String[]{"aaaa", "aaab", "aabb", "abbb", "bbbb"};
    private static final String[] WORDS = concat(
            WORDS_OF_LENGTH_1,
            WORDS_OF_LENGTH_2,
            WORDS_OF_LENGTH_3,
            WORDS_OF_LENGTH_4);
    private CharCountTaxonomy taxonomy;
    private CharCounter charCounter;

    private static String[] concat(String[]... arrays) {
        return Stream.of(arrays)
                .flatMap(Stream::of)
                .toArray(String[]::new);
    }

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
        List<CharCount> streamedCharCounts = taxonomy.charCountsOfLimitedTotalChars(100).collect(Collectors.toList());
        assertThat(streamedCharCounts).containsExactly(countChars(WORDS));
    }

    private Object[] countChars(String[] words) {
        return Stream.of(words)
                .map(charCounter::countChars)
                .toArray(CharCount[]::new);
    }

    @Test
    public void testStreamCharCountsLimitedByTotalChars() {
        List<CharCount> streamedCharCounts = taxonomy.charCountsOfLimitedTotalChars(2).collect(Collectors.toList());
        assertThat(streamedCharCounts).containsExactly(countChars(WORDS_OF_LENGTH_1, WORDS_OF_LENGTH_2));
    }

    private Object[] countChars(String[]... wordsArrays) {
        return countChars(concat(wordsArrays));
    }

    @Test
    public void testStreamCharCountsFromSomeCharCount() {
        CharCount from = charCounter.countChars("b");
        List<CharCount> streamedCharCounts = taxonomy.charCountsFromElementToTotalChars(from, 2).collect(Collectors.toList());
        assertThat(streamedCharCounts).containsExactly(countChars(new String[]{"b"}, WORDS_OF_LENGTH_2));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamSubsetOfCharCountsWithIncorrectBoundaries() {
        CharCount from = charCounter.countChars("aab");
        taxonomy.charCountsFromElementToTotalChars(from, 2);
    }

    @Test
    public void testStreamCharCountsBetweenTotalChars() {
        List<CharCount> streamedCharCounts = taxonomy.charCountsFromTotalCharsToTotalChars(2, 3).collect(Collectors.toList());
        assertThat(streamedCharCounts).containsExactly(countChars(WORDS_OF_LENGTH_2, WORDS_OF_LENGTH_3));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testStreamCharCountsBetweenTotalCharsWithIncorrectBoundaries() {
        taxonomy.charCountsFromTotalCharsToTotalChars(3, 2);
    }
}
