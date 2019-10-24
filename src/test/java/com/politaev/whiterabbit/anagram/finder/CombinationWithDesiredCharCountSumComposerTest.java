package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombination.wrap;
import static com.politaev.whiterabbit.anagram.finder.CombinationWithDesiredCharCountSumComposer.createCombinationComposer;
import static org.fest.assertions.Assertions.assertThat;

public class CombinationWithDesiredCharCountSumComposerTest extends AnagramTest {

    private CombinationWithDesiredCharCountSumComposer combinationComposer;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        CharCount desiredCharCountSum = charCounter.countChars("aaabbb");
        Collection<CharCountCombination> availablePieces = wrapCombinations(
                charCountCombinationOf("a", "b", "b"),
                charCountCombinationOf("b", "aa"),
                charCountCombinationOf("b", "ab"),
                charCountCombinationOf("bbb"),
                charCountCombinationOf("abb"),
                charCountCombinationOf("a", "a"),
                charCountCombinationOf("a", "b"),
                charCountCombinationOf("ab"),
                charCountCombinationOf("bb")
        );
        combinationComposer = createCombinationComposer()
                .composingCombinationsWithSumEqualTo(desiredCharCountSum)
                .bySelectingAdditionsFrom(availablePieces);
    }

    @SafeVarargs
    private final List<CharCountCombination> wrapCombinations(Combination<CharCount>... charCountCombinations) {
        return Stream.of(charCountCombinations)
                .map(CharCountCombination::wrap)
                .collect(Collectors.toList());
    }

    @Test
    public void testComposeStartingWithCombination() {
        Combination<CharCount> combination = charCountCombinationOf("a", "a", "b");
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeStartingWithCombination(wrap(combination))
                .collect(Collectors.toList());
        assertThat(streamedCombinations).containsOnly(
                charCountCombinationOf("a", "a", "b", "b", "ab"),
                charCountCombinationOf("a", "a", "b", "abb")
        );
    }

    @Test
    public void testComposeStartingWithCombinationNoResult() {
        Combination<CharCount> combination = charCountCombinationOf("a", "bb");
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeStartingWithCombination(wrap(combination))
                .collect(Collectors.toList());
        assertThat(streamedCombinations).isEmpty();
    }

    @Test
    public void testComposeEndingWithCharCount() {
        CharCount charCount = charCounter.countChars("aabb");
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeEndingWithCharCount(charCount)
                .collect(Collectors.toList());
        assertThat(streamedCombinations).containsOnly(
                charCountCombinationOf("a", "b", "aabb"),
                charCountCombinationOf("ab", "aabb")
        );
    }

    @Test
    public void testComposeEndingWithCharCountNoResult() {
        CharCount charCount = charCounter.countChars("aaa");
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeEndingWithCharCount(charCount)
                .collect(Collectors.toList());
        assertThat(streamedCombinations).isEmpty();
    }
}
