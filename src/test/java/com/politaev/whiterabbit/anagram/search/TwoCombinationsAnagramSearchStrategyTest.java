package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.GivenPhrase;
import com.politaev.whiterabbit.anagram.SizeLimit;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TwoCombinationsAnagramSearchStrategyTest extends AnagramSearchStrategyTest {

    @Override
    AnagramSearchStrategy createSearchStrategy() {
        return new TwoCombinationsAnagramSearchStrategy(context);
    }

    @GivenPhrase("aaabbb")
    @Test
    public void testSearch() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("a", "a", "a", "b", "b", "b"),
                charCountCombinationOf("a", "b", "b", "b", "aa"),
                charCountCombinationOf("a", "a", "b", "b", "ab"),
                charCountCombinationOf("a", "a", "a", "b", "bb"),
                charCountCombinationOf("a", "a", "a", "bbb"),
                charCountCombinationOf("a", "a", "b", "abb"),
                charCountCombinationOf("a", "b", "b", "aab"),
                charCountCombinationOf("b", "b", "b", "aaa"),
                charCountCombinationOf("a", "aa", "bbb"),
                charCountCombinationOf("a", "ab", "abb"),
                charCountCombinationOf("a", "bb", "aab"),
                charCountCombinationOf("b", "aa", "abb"),
                charCountCombinationOf("b", "ab", "aab"),
                charCountCombinationOf("b", "bb", "aaa"),
                charCountCombinationOf("aaa", "bbb"),
                charCountCombinationOf("aab", "abb")
        );
    }

    @GivenPhrase("aaabbb")
    @SizeLimit(4)
    @Test
    public void testSearchLimitSize() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("a", "a", "a", "bbb"),
                charCountCombinationOf("a", "a", "b", "abb"),
                charCountCombinationOf("a", "b", "b", "aab"),
                charCountCombinationOf("b", "b", "b", "aaa"),
                charCountCombinationOf("a", "aa", "bbb"),
                charCountCombinationOf("a", "ab", "abb"),
                charCountCombinationOf("a", "bb", "aab"),
                charCountCombinationOf("b", "aa", "abb"),
                charCountCombinationOf("b", "ab", "aab"),
                charCountCombinationOf("b", "bb", "aaa"),
                charCountCombinationOf("aaa", "bbb"),
                charCountCombinationOf("aab", "abb")
        );
    }

    @GivenPhrase("aaabb")
    @Test
    public void testSearchNoResult() {
        assertThat(foundAnagrams).isEmpty();
    }
}
