package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.stream.Stream;

class SingleWordAnagramSearchStrategy implements AnagramSearchStrategy {

    private final CharCount anagramCharCount;
    private final Dictionary dictionary;

    SingleWordAnagramSearchStrategy(CharCount anagramCharCount, Dictionary dictionary) {
        this.dictionary = dictionary;
        this.anagramCharCount = anagramCharCount;
    }

    @Override
    public Stream<Combination<CharCount>> search() {
        if (dictionary.containsWordsWithCharCount(anagramCharCount)) {
            return Stream.of(new Combination<>(anagramCharCount));
        } else {
            return Stream.empty();
        }
    }
}
