package com.politaev.whiterabbit.anagram;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.util.AnnotationValueRule;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static com.politaev.whiterabbit.anagram.AnagramFinder.createAnagramFinder;
import static org.fest.assertions.Assertions.assertThat;

public class AnagramFinderTest extends AnagramTest {

    private CharCount givenPhraseCharCount;
    private List<Combination<CharCount>> foundAnagrams;

    @Rule
    public AnnotationValueRule<GivenPhrase, String> givenPhrase = new AnnotationValueRule<>(GivenPhrase.class);

    @Rule
    public AnnotationValueRule<SizeLimit, Integer> sizeLimit = new AnnotationValueRule<>(SizeLimit.class);

    @Override
    @Before
    public void setUp() {
        super.setUp();
        givenPhraseCharCount = charCounter.countChars(givenPhrase.getValue());
        AnagramFinder anagramFinder = createAnagramFinder()
                .searchingAnagramsWithCharCountSum(givenPhraseCharCount)
                .withWordNumberLimitedBy(getSizeLimit())
                .withWordsFromDictionary(dictionary);
        foundAnagrams = anagramFinder.findAllCombinations().collect(Collectors.toList());
    }

    private int getSizeLimit() {
        Integer sizeLimitValue = sizeLimit.getValue();
        if (sizeLimitValue != null) return sizeLimitValue;
        else return givenPhraseCharCount.totalChars();
    }

    @GivenPhrase("aaabbb")
    @Test
    public void findAllEvenLength() {
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
    public void findAllOddLength() {
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
    @SizeLimit(4)
    @Test
    public void limitWordsEvenLength() {
        assertThat(foundAnagrams).containsOnly(
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
    @SizeLimit(3)
    @Test
    public void limitWordsOddLength() {
        assertThat(foundAnagrams).containsOnly(
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
}
