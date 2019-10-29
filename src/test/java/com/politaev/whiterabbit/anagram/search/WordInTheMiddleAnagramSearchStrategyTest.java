package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.GivenPhrase;
import com.politaev.whiterabbit.anagram.SizeLimit;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class WordInTheMiddleAnagramSearchStrategyTest extends AnagramSearchStrategyTest {

    @Override
    AnagramSearchStrategy createSearchStrategy() {
        return new WordInTheMiddleAnagramSearchStrategy(context);
    }

    @GivenPhrase("aaabbb")
    @Test
    public void testSearch() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("a", "a", "ab", "bb"),
                charCountCombinationOf("a", "b", "aa", "bb"),
                charCountCombinationOf("a", "b", "ab", "ab"),
                charCountCombinationOf("b", "b", "aa", "ab"),
                charCountCombinationOf("aa", "ab", "bb"),
                charCountCombinationOf("ab", "ab", "ab")
        );
    }

    @GivenPhrase("aaabb")
    @Test
    public void testSearchOddPhraseLength() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("a", "a", "a", "b", "b"),
                charCountCombinationOf("a", "a", "a", "bb"),
                charCountCombinationOf("a", "a", "b", "ab"),
                charCountCombinationOf("a", "b", "b", "aa"),
                charCountCombinationOf("a", "aa", "bb"),
                charCountCombinationOf("a", "ab", "ab"),
                charCountCombinationOf("b", "aa", "ab")
        );
    }

    @GivenPhrase("aaabbb")
    @SizeLimit(3)
    @Test
    public void testSearchLimitSize() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("aa", "ab", "bb"),
                charCountCombinationOf("ab", "ab", "ab")
        );
    }

    @GivenPhrase("aaabbb")
    @SizeLimit(2)
    @Test
    public void testSearchNoResult() {
        assertThat(foundAnagrams).isEmpty();
    }
}
