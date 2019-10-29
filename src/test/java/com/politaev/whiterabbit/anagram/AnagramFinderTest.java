package com.politaev.whiterabbit.anagram;

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

    private static final String[] WORDS = new String[]{
            "a", "b",
            "aa", "ab", "ba", "bb",
            "aaa", "aab", "aba", "baa", "abb", "bab", "bba", "bbb"
    };
    private CharCount givenPhraseCharCount;
    private List<String> foundAnagrams;

    @Rule
    public AnnotationValueRule<GivenPhrase, String> givenPhrase = new AnnotationValueRule<>(GivenPhrase.class);

    @Rule
    public AnnotationValueRule<SizeLimit, Integer> sizeLimit = new AnnotationValueRule<>(SizeLimit.class);

    @Override
    protected String[] getWords() {
        return WORDS;
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        givenPhraseCharCount = charCounter.countChars(givenPhrase.getValue());
        AnagramFinder anagramFinder = createAnagramFinder()
                .searchingAnagramsWithCharCountSum(givenPhraseCharCount)
                .withWordNumberLimitedBy(getSizeLimit())
                .withWordsFromDictionary(dictionary);
        foundAnagrams = anagramFinder.findAnagrams().collect(Collectors.toList());
    }

    private int getSizeLimit() {
        Integer sizeLimitValue = sizeLimit.getValue();
        if (sizeLimitValue != null) return sizeLimitValue;
        else return givenPhraseCharCount.totalChars();
    }

    @GivenPhrase("aabb")
    @Test
    public void findAllEvenLength() {
        assertThat(foundAnagrams).containsOnly(
                "a a b b", "a b a b", "a b b a", "b a a b", "b a b a", "b b a a",
                "a a bb", "a bb a", "bb a a",
                "b b aa", "b aa b", "aa b b",
                "a b ab", "a ab b", "ab a b", "b a ab", "b ab a", "ab b a",
                "a b ba", "a ba b", "ba a b", "b a ba", "b ba a", "ba b a",
                "aa bb", "bb aa", "ab ba", "ba ab", "ab ab", "ba ba",
                "a abb", "a bab", "a bba", "abb a", "bab a", "bba a",
                "b aab", "b aba", "b baa", "aab b", "aba b", "baa b"
        );
    }

    @GivenPhrase("abb")
    @Test
    public void findAllOddLength() {
        assertThat(foundAnagrams).containsOnly(
                "a b b", "b a b", "b b a",
                "a bb", "bb a",
                "b ab", "ab b",
                "b ba", "ba b",
                "abb", "bba", "bab"
        );
    }

    @GivenPhrase("aabb")
    @SizeLimit(3)
    @Test
    public void limitWordsEvenLength() {
        assertThat(foundAnagrams).containsOnly(
                "a a bb", "a bb a", "bb a a",
                "b b aa", "b aa b", "aa b b",
                "a b ab", "a ab b", "ab a b", "b a ab", "b ab a", "ab b a",
                "a b ba", "a ba b", "ba a b", "b a ba", "b ba a", "ba b a",
                "aa bb", "bb aa", "ab ba", "ba ab", "ab ab", "ba ba",
                "a abb", "a bab", "a bba", "abb a", "bab a", "bba a",
                "b aab", "b aba", "b baa", "aab b", "aba b", "baa b"
        );
    }

    @GivenPhrase("abb")
    @SizeLimit(2)
    @Test
    public void limitWordsOddLength() {
        assertThat(foundAnagrams).containsOnly(
                "a bb", "bb a",
                "b ab", "ab b",
                "b ba", "ba b",
                "abb", "bba", "bab"
        );
    }

    @GivenPhrase("aabb")
    @SizeLimit(1)
    @Test
    public void testNoResult() {
        assertThat(foundAnagrams).isEmpty();
    }
}
