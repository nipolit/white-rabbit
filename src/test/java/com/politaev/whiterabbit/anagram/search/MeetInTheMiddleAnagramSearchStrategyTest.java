package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.GivenPhrase;
import com.politaev.whiterabbit.anagram.SizeLimit;
import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class MeetInTheMiddleAnagramSearchStrategyTest extends AnagramSearchStrategyTest {

    @Override
    AnagramSearchStrategy createSearchStrategy() {
        return new MeetInTheMiddleAnagramSearchStrategy(context);
    }

    @GivenPhrase("aaabbb")
    @Test
    public void testSearchEvenPhraseLength() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("a", "a", "a", "b", "b", "b"),
                charCountCombinationOf("a", "b", "b", "b", "aa"),
                charCountCombinationOf("a", "a", "b", "b", "ab"),
                charCountCombinationOf("a", "a", "a", "b", "bb"),
                charCountCombinationOf("a", "a", "a", "bbb"),
                charCountCombinationOf("a", "a", "b", "abb"),
                charCountCombinationOf("a", "b", "b", "aab"),
                charCountCombinationOf("b", "b", "b", "aaa"),
                charCountCombinationOf("a", "a", "ab", "bb"),
                charCountCombinationOf("a", "b", "aa", "bb"),
                charCountCombinationOf("a", "b", "ab", "ab"),
                charCountCombinationOf("b", "b", "aa", "ab"),
                charCountCombinationOf("a", "a", "abbb"),
                charCountCombinationOf("a", "b", "aabb"),
                charCountCombinationOf("b", "b", "aaab"),
                charCountCombinationOf("a", "aa", "bbb"),
                charCountCombinationOf("a", "ab", "abb"),
                charCountCombinationOf("a", "bb", "aab"),
                charCountCombinationOf("b", "aa", "abb"),
                charCountCombinationOf("b", "ab", "aab"),
                charCountCombinationOf("b", "bb", "aaa"),
                charCountCombinationOf("aa", "ab", "bb"),
                charCountCombinationOf("ab", "ab", "ab"),
                charCountCombinationOf("aa", "abbb"),
                charCountCombinationOf("ab", "aabb"),
                charCountCombinationOf("bb", "aaab"),
                charCountCombinationOf("aaa", "bbb"),
                charCountCombinationOf("aab", "abb")
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
                charCountCombinationOf("a", "a", "abb"),
                charCountCombinationOf("a", "b", "aab"),
                charCountCombinationOf("b", "b", "aaa"),
                charCountCombinationOf("a", "aa", "bb"),
                charCountCombinationOf("a", "ab", "ab"),
                charCountCombinationOf("b", "aa", "ab"),
                charCountCombinationOf("aa", "abb"),
                charCountCombinationOf("ab", "aab"),
                charCountCombinationOf("bb", "aaa"),
                charCountCombinationOf("a", "aabb"),
                charCountCombinationOf("b", "aaab")
        );
    }

    @GivenPhrase("aaabbb")
    @SizeLimit(1)
    @Test
    public void testSearchNoResult() {
        assertThat(foundAnagrams).isEmpty();
    }
}
