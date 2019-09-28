package com.politaev.whiterabbit.dictionary;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.counter.CharCounter;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static java.util.Collections.emptySet;
import static java.util.Collections.unmodifiableSet;

public class Dictionary {
    private final CharCounter charCounter;
    private final Map<CharCount, Set<String>> wordsByCharCount;

    public Dictionary(CharCounter charCounter) {
        this.charCounter = charCounter;
        this.wordsByCharCount = new HashMap<>();
    }

    public void add(String word) {
        CharCount wordCharCount = charCounter.countChars(word);
        Set<String> wordsStored = wordsByCharCount.computeIfAbsent(wordCharCount, charCount -> new HashSet<>());
        wordsStored.add(word);
    }

    public Set<String> getWords(CharCount charCount) {
        Set<String> words = wordsByCharCount.getOrDefault(charCount, emptySet());
        return unmodifiableSet(words);
    }
}
