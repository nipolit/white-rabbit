package com.politaev.whiterabbit.reader;

import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;

public class WordReaderTest {
    private List<String> readWordsToList(String pathToResource) throws IOException {
        try (WordReader wordReader = new WordReader(pathToResource)) {
            return wordReader.words().collect(Collectors.toList());
        }
    }

    @Test
    public void testOnlyLatinCharacters() throws IOException {
        List<String> words = readWordsToList("wordReaderTest/onlyLatinCharacters/wordlist");
        assertEquals(10, words.size());
    }

    @Test
    public void testNonLetterCharacters() throws IOException {
        List<String> words = readWordsToList("wordReaderTest/nonLetterCharacters/wordlist");
        assertEquals(9, words.size());
    }

    @Test
    public void testSpecialCharacters() throws IOException {
        List<String> words = readWordsToList("wordReaderTest/specialCharacters/wordlist");
        assertEquals(2, words.size());
    }

    @Test
    public void testCarriageReturnCharacter() throws IOException {
        List<String> words = readWordsToList("wordReaderTest/carriageReturnCharacter/wordlist");
        assertEquals(3, words.size());
    }
}
