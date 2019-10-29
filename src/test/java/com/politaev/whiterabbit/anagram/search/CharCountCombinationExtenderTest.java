package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.politaev.whiterabbit.anagram.search.CharCountCombination.wrap;
import static com.politaev.whiterabbit.anagram.search.CharCountCombinationExtender.createExtender;
import static org.fest.assertions.Assertions.assertThat;


public class CharCountCombinationExtenderTest extends AnagramTest {

    @Test
    public void testNoRestrictions() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .build();
        CharCountCombination initialCombination = wrap(charCountCombinationOf("aa", "bb", "aaab"));
        List<CharCountCombination> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                wrap(charCountCombinationOf("aa", "bb", "aaab", "aaab")),
                wrap(charCountCombinationOf("aa", "bb", "aaab", "aabb")),
                wrap(charCountCombinationOf("aa", "bb", "aaab", "abbb")),
                wrap(charCountCombinationOf("aa", "bb", "aaab", "bbbb"))
        );
    }

    @Test
    public void testTotalCharsLimit() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotHigherThan(6)
                .build();
        CharCountCombination initialCombination = wrap(charCountCombinationOf("a", "bb"));
        List<CharCountCombination> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                wrap(charCountCombinationOf("a", "bb", "bb")),
                wrap(charCountCombinationOf("a", "bb", "aaa")),
                wrap(charCountCombinationOf("a", "bb", "aab")),
                wrap(charCountCombinationOf("a", "bb", "abb")),
                wrap(charCountCombinationOf("a", "bb", "bbb"))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationNotUnderTotalCharsLimitIllegal() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotHigherThan(3)
                .build();
        combinationExtender.extend(wrap(charCountCombinationOf("a", "bb")));
    }

    @Test
    public void testTotalCharsThreshold() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotLowerThan(4)
                .build();
        CharCountCombination initialCombination = wrap(charCountCombinationOf("a"));
        List<CharCountCombination> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                wrap(charCountCombinationOf("a", "aaa")),
                wrap(charCountCombinationOf("a", "aab")),
                wrap(charCountCombinationOf("a", "abb")),
                wrap(charCountCombinationOf("a", "bbb")),
                wrap(charCountCombinationOf("a", "aaaa")),
                wrap(charCountCombinationOf("a", "aaab")),
                wrap(charCountCombinationOf("a", "aabb")),
                wrap(charCountCombinationOf("a", "abbb")),
                wrap(charCountCombinationOf("a", "bbbb"))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationNotUnderExpectedTotalCharsIllegal() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultTotalCharsNotLowerThan(2)
                .build();
        combinationExtender.extend(wrap(charCountCombinationOf("a", "bb")));
    }

    @Test
    public void testTotalCharsExactValue() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withExpectedTotalChars(4)
                .build();
        CharCountCombination initialCombination = wrap(charCountCombinationOf("a"));
        List<CharCountCombination> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                wrap(charCountCombinationOf("a", "aaa")),
                wrap(charCountCombinationOf("a", "aab")),
                wrap(charCountCombinationOf("a", "abb")),
                wrap(charCountCombinationOf("a", "bbb"))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationOverTotalCharsThresholdIllegal() {
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withExpectedTotalChars(3)
                .build();
        combinationExtender.extend(wrap(charCountCombinationOf("a", "bb")));
    }

    @Test
    public void testResultCharCountLimit() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "ab" + "abbbb");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .build();
        CharCountCombination initialCombination = wrap(charCountCombinationOf("b", "ab"));
        List<CharCountCombination> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                wrap(charCountCombinationOf("b", "ab", "ab")),
                wrap(charCountCombinationOf("b", "ab", "bb")),
                wrap(charCountCombinationOf("b", "ab", "abb")),
                wrap(charCountCombinationOf("b", "ab", "bbb")),
                wrap(charCountCombinationOf("b", "ab", "abbb")),
                wrap(charCountCombinationOf("b", "ab", "bbbb"))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationEqualToResultCharCountLimitIllegal() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "ab");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .build();
        combinationExtender.extend(wrap(charCountCombinationOf("b", "ab")));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCombinationNotIncludedInResultCharCountLimitIllegal() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "ab");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .build();
        combinationExtender.extend(wrap(charCountCombinationOf("a", "a")));
    }

    @Test
    public void testAllConditionsAtOnce() {
        CharCount resultCharCountLimit = charCounter.countChars("b" + "abbbb");
        CharCountCombinationExtender combinationExtender = createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .withResultTotalCharsNotLowerThan(3)
                .withResultTotalCharsNotHigherThan(4)
                .build();
        CharCountCombination initialCombination = wrap(charCountCombinationOf("b"));
        List<CharCountCombination> extendedCombinations = combinationExtender.extend(initialCombination).collect(Collectors.toList());
        assertThat(extendedCombinations).containsExactly(
                wrap(charCountCombinationOf("b", "ab")),
                wrap(charCountCombinationOf("b", "bb")),
                wrap(charCountCombinationOf("b", "abb")),
                wrap(charCountCombinationOf("b", "bbb"))
        );
    }
}
