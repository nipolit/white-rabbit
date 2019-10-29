package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.politaev.whiterabbit.anagram.search.CharCountCombinationExtender.createExtender;
import static org.fest.assertions.Assertions.assertThat;


public class CharCountCombinationExtenderTest extends AnagramTest {

    @Test
    public void testNoRestrictions() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .build();
        Combination<CharCount> initialCombination = charCountCombinationOf("aa", "bb", "aaab");
        List<Combination<CharCount>> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                charCountCombinationOf("aa", "bb", "aaab", "aaab"),
                charCountCombinationOf("aa", "bb", "aaab", "aabb"),
                charCountCombinationOf("aa", "bb", "aaab", "abbb"),
                charCountCombinationOf("aa", "bb", "aaab", "bbbb")
        );
    }

    @Test
    public void testTotalCharsLimit() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotHigherThan(6)
                .build();
        Combination<CharCount> initialCombination = charCountCombinationOf("a", "bb");
        List<Combination<CharCount>> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                charCountCombinationOf("a", "bb", "bb"),
                charCountCombinationOf("a", "bb", "aaa"),
                charCountCombinationOf("a", "bb", "aab"),
                charCountCombinationOf("a", "bb", "abb"),
                charCountCombinationOf("a", "bb", "bbb")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationNotUnderTotalCharsLimitIllegal() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotHigherThan(3)
                .build();
        combinationExtender.extend(charCountCombinationOf("a", "bb"));
    }

    @Test
    public void testTotalCharsThreshold() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotLowerThan(4)
                .build();
        Combination<CharCount> initialCombination = charCountCombinationOf("a");
        List<Combination<CharCount>> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                charCountCombinationOf("a", "aaa"),
                charCountCombinationOf("a", "aab"),
                charCountCombinationOf("a", "abb"),
                charCountCombinationOf("a", "bbb"),
                charCountCombinationOf("a", "aaaa"),
                charCountCombinationOf("a", "aaab"),
                charCountCombinationOf("a", "aabb"),
                charCountCombinationOf("a", "abbb"),
                charCountCombinationOf("a", "bbbb")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationNotUnderExpectedTotalCharsIllegal() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotLowerThan(2)
                .build();
        combinationExtender.extend(charCountCombinationOf("a", "bb"));
    }

    @Test
    public void testTotalCharsExactValue() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withExpectedTotalChars(4)
                .build();
        Combination<CharCount> initialCombination = charCountCombinationOf("a");
        List<Combination<CharCount>> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                charCountCombinationOf("a", "aaa"),
                charCountCombinationOf("a", "aab"),
                charCountCombinationOf("a", "abb"),
                charCountCombinationOf("a", "bbb")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationOverTotalCharsThresholdIllegal() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withExpectedTotalChars(3)
                .build();
        combinationExtender.extend(charCountCombinationOf("a", "bb"));
    }

    @Test
    public void testResultCharCountLimit() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "ab" + "abbbb");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .build();
        Combination<CharCount> initialCombination = charCountCombinationOf("b", "ab");
        List<Combination<CharCount>> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                charCountCombinationOf("b", "ab", "ab"),
                charCountCombinationOf("b", "ab", "bb"),
                charCountCombinationOf("b", "ab", "abb"),
                charCountCombinationOf("b", "ab", "bbb"),
                charCountCombinationOf("b", "ab", "abbb"),
                charCountCombinationOf("b", "ab", "bbbb")
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationEqualToResultCharCountLimitIllegal() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "ab");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .build();
        combinationExtender.extend(charCountCombinationOf("b", "ab"));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationNotIncludedInResultCharCountLimitIllegal() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "ab");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .build();
        combinationExtender.extend(charCountCombinationOf("a", "a"));
    }

    @Test
    public void testAllConditionsAtOnce() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "abbbb");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .withResultTotalCharsNotLowerThan(3)
                .withResultTotalCharsNotHigherThan(4)
                .build();
        Combination<CharCount> initialCombination = charCountCombinationOf("b");
        List<Combination<CharCount>> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                charCountCombinationOf("b", "ab"),
                charCountCombinationOf("b", "bb"),
                charCountCombinationOf("b", "abb"),
                charCountCombinationOf("b", "bbb")
        );
    }
}
