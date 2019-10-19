package com.politaev.whiterabbit.anagram;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.counter.CharCounter;
import com.politaev.whiterabbit.counter.LatinCharCounter;
import com.politaev.whiterabbit.dictionary.Dictionary;
import org.junit.Before;

import java.util.stream.Stream;

public abstract class AnagramTest {
    protected static final String[] WORDS = new String[]{
            "a", "b",
            "aa", "ab", "bb",
            "aaa", "aab", "abb", "bbb",
            "aaaa", "aaab", "aabb", "abbb", "bbbb"
    };

    protected Dictionary dictionary;
    protected CharCounter charCounter;

    @Before
    public void setUp() {
        charCounter = new LatinCharCounter();
        dictionary = new Dictionary(charCounter);
        Stream.of(WORDS).forEach(dictionary::add);
    }

    protected Combination<CharCount> charCountCombinationOf(String... words) {
        CharCount[] wordCharCounts = Stream.of(words)
                .map(charCounter::countChars)
                .toArray(CharCount[]::new);
        return new Combination<>(wordCharCounts);
    }
}
