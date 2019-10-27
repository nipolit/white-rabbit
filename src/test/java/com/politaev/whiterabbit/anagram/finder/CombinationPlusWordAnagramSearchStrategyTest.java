package com.politaev.whiterabbit.anagram.finder;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class CombinationPlusWordAnagramSearchStrategyTest extends AnagramSearchStrategyTest {

    @Override
    AnagramSearchStrategy createSearchStrategy() {
        return new CombinationPlusWordAnagramSearchStrategy(givenPhraseCharCount, dictionary, anagramComposer);
    }

    @GivenPhrase("aaabbb")
    @Test
    public void testSearch() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("a", "a", "abbb"),
                charCountCombinationOf("a", "b", "aabb"),
                charCountCombinationOf("b", "b", "aaab"),
                charCountCombinationOf("aa", "abbb"),
                charCountCombinationOf("ab", "aabb"),
                charCountCombinationOf("bb", "aaab")
        );
    }

    @GivenPhrase("aaabb")
    @Test
    public void testSearchOddPhraseLength() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("a", "a", "abb"),
                charCountCombinationOf("a", "b", "aab"),
                charCountCombinationOf("b", "b", "aaa"),
                charCountCombinationOf("aa", "abb"),
                charCountCombinationOf("ab", "aab"),
                charCountCombinationOf("bb", "aaa"),
                charCountCombinationOf("a", "aabb"),
                charCountCombinationOf("b", "aaab")
        );
    }

    @GivenPhrase("aaabbb")
    @SizeLimit(2)
    @Test
    public void testSearchLimitSize() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("aa", "abbb"),
                charCountCombinationOf("ab", "aabb"),
                charCountCombinationOf("bb", "aaab")
        );
    }

    @GivenPhrase("aaabbb")
    @SizeLimit(1)
    @Test
    public void testSearchNoResult() {
        assertThat(foundAnagrams).isEmpty();
    }
}
