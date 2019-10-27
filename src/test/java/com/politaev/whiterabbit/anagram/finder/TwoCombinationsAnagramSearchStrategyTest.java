package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class TwoCombinationsAnagramSearchStrategyTest extends AnagramSearchStrategyTest {

    @GivenPhrase("aaabbb")
    @Test
    public void testSearch() {
        AnagramSearchStrategy twoCombinationsSearchStrategy = new TwoCombinationsAnagramSearchStrategy(givenPhraseCharCount, combinationsNotOverHalfAnagramLength, anagramComposer);
        List<Combination<CharCount>> foundAnagrams = twoCombinationsSearchStrategy.search().collect(Collectors.toList());
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
        AnagramSearchStrategy twoCombinationsSearchStrategy = new TwoCombinationsAnagramSearchStrategy(givenPhraseCharCount, combinationsNotOverHalfAnagramLength, anagramComposer);
        List<Combination<CharCount>> foundAnagrams = twoCombinationsSearchStrategy.search().collect(Collectors.toList());
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
        AnagramSearchStrategy twoCombinationsSearchStrategy = new TwoCombinationsAnagramSearchStrategy(givenPhraseCharCount, combinationsNotOverHalfAnagramLength, anagramComposer);
        List<Combination<CharCount>> foundAnagrams = twoCombinationsSearchStrategy.search().collect(Collectors.toList());
        assertThat(foundAnagrams).isEmpty();
    }
}
