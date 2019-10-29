package com.politaev.whiterabbit.anagram.output;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;

public class PhraseResolverTest extends AnagramTest {

    private static final String[] WORDS = new String[]{
            "a", "b",
            "aa", "ab", "ba", "bb",
            "aaa", "aab", "aba", "baa", "abb", "bab", "bba", "bbb"
    };
    private PhraseResolver phraseResolver;

    @Override
    protected String[] getWords() {
        return WORDS;
    }

    @Override
    @Before
    public void setUp() {
        super.setUp();
        phraseResolver = new PhraseResolver(dictionary);
    }

    @Test
    public void testResolveSingleWordPhrase() {
        Combination<CharCount> combination = charCountCombinationOf("aa");
        List<String> streamedPhrases = phraseResolver.resolve(combination).collect(Collectors.toList());
        assertThat(streamedPhrases).containsOnly("aa");
    }

    @Test
    public void testResolveSingleWordPhraseWithSubstitutions() {
        Combination<CharCount> combination = charCountCombinationOf("aab");
        List<String> streamedPhrases = phraseResolver.resolve(combination).collect(Collectors.toList());
        assertThat(streamedPhrases).containsOnly("aab", "aba", "baa");
    }

    @Test
    public void testResolvePhraseWithPermutations() {
        Combination<CharCount> combination = charCountCombinationOf("a", "bb", "aaa");
        List<String> streamedPhrases = phraseResolver.resolve(combination).collect(Collectors.toList());
        assertThat(streamedPhrases).containsOnly(
                "a bb aaa",
                "a aaa bb",
                "aaa a bb",
                "aaa bb a",
                "bb aaa a",
                "bb a aaa"
        );
    }

    @Test
    public void testResolvePhraseWithPermutationsAndSubstitutions() {
        Combination<CharCount> combination = charCountCombinationOf("a", "ab", "bbb");
        List<String> streamedPhrases = phraseResolver.resolve(combination).collect(Collectors.toList());
        assertThat(streamedPhrases).containsOnly(
                "a ab bbb",
                "a ba bbb",
                "a bbb ab",
                "a bbb ba",
                "bbb a ab",
                "bbb a ba",
                "bbb ab a",
                "bbb ba a",
                "ab bbb a",
                "ba bbb a",
                "ab a bbb",
                "ba a bbb"
        );
    }
}
