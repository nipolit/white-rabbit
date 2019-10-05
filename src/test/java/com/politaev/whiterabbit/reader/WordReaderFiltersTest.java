package com.politaev.whiterabbit.reader;

import com.politaev.whiterabbit.counter.CharCounter;
import com.politaev.whiterabbit.counter.LatinCharCounter;
import com.politaev.whiterabbit.reader.filter.AllowedCharactersFilter;
import com.politaev.whiterabbit.reader.filter.CharCountLimitFilter;
import com.politaev.whiterabbit.reader.filter.NotEmptyFilter;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class WordReaderFiltersTest {
    private List<Predicate<String>> wordFilters;

    @Before
    public void setUp() {
        wordFilters = new ArrayList<>();
    }

    private void addWordFilter(Predicate<String> wordFilter) {
        wordFilters.add(wordFilter);
    }

    private Set<String> readWordsToSet(String pathToResource) throws IOException {
        try (WordReader wordReader = new WordReader(pathToResource)) {
            setWordFilters(wordReader);
            return wordReader.words().collect(Collectors.toSet());
        }
    }

    private void setWordFilters(WordReader wordReader) {
        wordFilters.forEach(wordReader::addWordFilter);
    }

    @Test
    public void testNotEmptyFilterFiltersEmptyWords() throws IOException {
        addNotEmptyFilter();
        Set<String> words = readWordsToSet("wordReaderFiltersTest/notEmptyFilter/wordlist");
        assertFalse(words.contains(""));
    }

    @Test
    public void testNotEmptyFilterAllowsNotEmptyWords() throws IOException {
        addNotEmptyFilter();
        Set<String> words = readWordsToSet("wordReaderFiltersTest/notEmptyFilter/wordlist");
        assertEquals(10, words.size());
    }

    private void addNotEmptyFilter() {
        addWordFilter(new NotEmptyFilter());
    }

    @Test
    public void testAllowedCharactersFilterFiltersNotAllowedChars() throws IOException {
        addAllowedCharactersFilter();
        Set<String> words = readWordsToSet("wordReaderFiltersTest/allowedCharactersFilter/wordlist");
        assertFalse(words.contains("abracadabra"));
    }

    @Test
    public void testAllowedCharactersFilterAllowsAllowedChars() throws IOException {
        addAllowedCharactersFilter();
        Set<String> words = readWordsToSet("wordReaderFiltersTest/allowedCharactersFilter/wordlist");
        assertEquals(20, words.size());
    }

    private void addAllowedCharactersFilter() {
        AllowedCharactersFilter allowedCharactersFilter = new AllowedCharactersFilter();
        Stream.of('a', 'b', 'c', 'd', 'e').forEach(allowedCharactersFilter::allow);
        addWordFilter(allowedCharactersFilter);
    }

    @Test
    public void testCharCountLimitFilterFiltersOverTheLimitWords() throws IOException {
        addCharCountLimitFilter();
        Set<String> words = readWordsToSet("wordReaderFiltersTest/charCountLimitFilter/wordlist");
        assertFalse(words.contains("deded"));
    }

    @Test
    public void testCharCountLimitFilterAllowsUnderTheLimitWords() throws IOException {
        addCharCountLimitFilter();
        Set<String> words = readWordsToSet("wordReaderFiltersTest/charCountLimitFilter/wordlist");
        assertEquals(25, words.size());
    }

    private void addCharCountLimitFilter() {
        CharCounter charCounter = new LatinCharCounter();
        String limitString = "aabbccddee";
        CharCountLimitFilter charCountLimitFilter = new CharCountLimitFilter(charCounter, limitString);
        addWordFilter(charCountLimitFilter);
    }

    @Test
    public void testComposedFilter() throws IOException {
        addAllFilters();
        Set<String> words = readWordsToSet("wordReaderFiltersTest/composedFilter/wordlist");
        assertEquals(12, words.size());
    }

    private void addAllFilters() {
        addNotEmptyFilter();
        addAllowedCharactersFilter();
        addCharCountLimitFilter();
    }
}
