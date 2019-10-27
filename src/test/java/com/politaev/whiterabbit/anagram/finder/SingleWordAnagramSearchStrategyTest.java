package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class SingleWordAnagramSearchStrategyTest extends AnagramTest {

    @Test
    public void testSearch() {
        CharCount givenPhraseCharCount = charCounter.countChars("aabb");
        AnagramSearchStrategy singleWordSearchStrategy = new SingleWordAnagramSearchStrategy(givenPhraseCharCount, dictionary);
        List<Combination<CharCount>> foundAnagrams = singleWordSearchStrategy.search().collect(Collectors.toList());
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("aabb")
        );
    }
}
