package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.anagram.SizeLimit;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.util.AnnotationValueRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static com.politaev.whiterabbit.anagram.search.CharCountCombination.wrap;
import static com.politaev.whiterabbit.anagram.search.CombinationWithDesiredCharCountSumComposer.createCombinationComposer;
import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class CombinationWithDesiredCharCountSumComposerTest extends AnagramTest {

    private CombinationWithDesiredCharCountSumComposer combinationComposer;

    @Rule
    public AnnotationValueRule<SizeLimit, Integer> sizeLimit = new AnnotationValueRule<>(SizeLimit.class, 6);

    @Override
    @Before
    public void setUp() {
        super.setUp();
        CharCount desiredCharCountSum = charCounter.countChars("aaabbb");
        Collection<CharCountCombination> availablePieces = asList(
                wrap(charCountCombinationOf("a", "b", "b")),
                wrap(charCountCombinationOf("b", "aa")),
                wrap(charCountCombinationOf("b", "ab")),
                wrap(charCountCombinationOf("bbb")),
                wrap(charCountCombinationOf("abb")),
                wrap(charCountCombinationOf("a", "a")),
                wrap(charCountCombinationOf("a", "b")),
                wrap(charCountCombinationOf("ab")),
                wrap(charCountCombinationOf("bb"))
        );
        combinationComposer = createCombinationComposer()
                .composingCombinationsWithSumEqualTo(desiredCharCountSum)
                .andSizeLimitedBy(sizeLimit.getValue())
                .bySelectingAdditionsFrom(availablePieces);
    }

    @Test
    public void testComposeStartingWithCombination() {
        CharCountCombination combination = wrap(charCountCombinationOf("a", "a", "b"));
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeStartingWithCombination(combination)
                .collect(Collectors.toList());
        assertThat(streamedCombinations).containsOnly(
                charCountCombinationOf("a", "a", "b", "b", "ab"),
                charCountCombinationOf("a", "a", "b", "abb")
        );
    }

    @Test
    public void testComposeStartingWithCombinationNoResult() {
        CharCountCombination combination = wrap(charCountCombinationOf("a", "bb"));
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeStartingWithCombination(combination)
                .collect(Collectors.toList());
        assertThat(streamedCombinations).isEmpty();
    }

    @Test
    @SizeLimit(4)
    public void testComposeStartingWithCombinationLimitSize() {
        CharCountCombination combination = wrap(charCountCombinationOf("a", "a", "b"));
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeStartingWithCombination(combination)
                .collect(Collectors.toList());
        assertThat(streamedCombinations).containsOnly(
                charCountCombinationOf("a", "a", "b", "abb")
        );
    }

    @Test
    @SizeLimit(3)
    public void testComposeStartingWithCombinationLimitSizeNoResult() {
        CharCountCombination combination = wrap(charCountCombinationOf("a", "a", "b"));
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeStartingWithCombination(combination)
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

    @Test
    @SizeLimit(2)
    public void testComposeEndingWithCharCountLimitSize() {
        CharCount charCount = charCounter.countChars("aabb");
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeEndingWithCharCount(charCount)
                .collect(Collectors.toList());
        assertThat(streamedCombinations).containsOnly(
                charCountCombinationOf("ab", "aabb")
        );
    }

    @Test
    @SizeLimit(1)
    public void testComposeEndingWithCharCountLimitSizeNoResult() {
        CharCount charCount = charCounter.countChars("aabb");
        List<Combination<CharCount>> streamedCombinations = combinationComposer.composeEndingWithCharCount(charCount)
                .collect(Collectors.toList());
        assertThat(streamedCombinations).isEmpty();
    }
}
