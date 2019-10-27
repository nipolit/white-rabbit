package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class CombinationPlusWordAnagramSearchStrategyTest extends AnagramSearchStrategyTest {

    @GivenPhrase("aaabbb")
    @Test
    public void testSearch() {
        AnagramSearchStrategy combinationPlusWordSearchStrategy = new CombinationPlusWordAnagramSearchStrategy(givenPhraseCharCount, dictionary, anagramComposer);
        List<Combination<CharCount>> foundAnagrams = combinationPlusWordSearchStrategy.search().collect(Collectors.toList());
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
        AnagramSearchStrategy combinationPlusWordSearchStrategy = new CombinationPlusWordAnagramSearchStrategy(givenPhraseCharCount, dictionary, anagramComposer);
        List<Combination<CharCount>> foundAnagrams = combinationPlusWordSearchStrategy.search().collect(Collectors.toList());
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
        AnagramSearchStrategy combinationPlusWordSearchStrategy = new CombinationPlusWordAnagramSearchStrategy(givenPhraseCharCount, dictionary, anagramComposer);
        List<Combination<CharCount>> foundAnagrams = combinationPlusWordSearchStrategy.search().collect(Collectors.toList());
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
        AnagramSearchStrategy combinationPlusWordSearchStrategy = new CombinationPlusWordAnagramSearchStrategy(givenPhraseCharCount, dictionary, anagramComposer);
        List<Combination<CharCount>> foundAnagrams = combinationPlusWordSearchStrategy.search().collect(Collectors.toList());
        assertThat(foundAnagrams).isEmpty();
    }
}
