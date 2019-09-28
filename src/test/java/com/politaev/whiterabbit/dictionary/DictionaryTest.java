package com.politaev.whiterabbit.dictionary;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.counter.CharCounter;
import com.politaev.whiterabbit.counter.LatinCharCounter;
import org.junit.Before;
import org.junit.Test;

import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class DictionaryTest {
    private CharCounter charCounter;
    private Dictionary dictionary;

    @Before
    public void setUp() {
        charCounter = new LatinCharCounter();
        dictionary = new Dictionary(charCounter);
    }

    @Test
    public void testAddWord() {
        String word = "neo";
        CharCount charCount = charCounter.countChars(word);
        dictionary.add(word);
        Set<String> wordsFromDictionary = dictionary.getWords(charCount);
        assertTrue(wordsFromDictionary.contains(word));
    }

    @Test
    public void testAddWordSeveralTimes() {
        String word = "agentsmith";
        CharCount charCount = charCounter.countChars(word);
        dictionary.add(word);
        dictionary.add(word);
        Set<String> wordsFromDictionary = dictionary.getWords(charCount);
        assertEquals(1, wordsFromDictionary.size());
    }

    @Test
    public void testGetWordWithoutAdding() {
        String word = "morpheus";
        CharCount charCount = charCounter.countChars(word);
        Set<String> wordsFromDictionary = dictionary.getWords(charCount);
        assertFalse(wordsFromDictionary.contains(word));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testAddWordToWordsFromDictionary() {
        String word = "trinity", anotherWord = "cypher";
        CharCount charCount = charCounter.countChars(word);
        dictionary.add(word);
        Set<String> wordsFromDictionary = dictionary.getWords(charCount);
        wordsFromDictionary.add(anotherWord);
    }
}
