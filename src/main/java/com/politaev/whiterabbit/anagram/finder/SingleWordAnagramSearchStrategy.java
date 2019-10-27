package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.stream.Stream;

class SingleWordAnagramSearchStrategy extends AnagramSearchStrategy {

    private final Dictionary dictionary;

    SingleWordAnagramSearchStrategy(CharCount anagramCharCount, Dictionary dictionary) {
        super(anagramCharCount);
        this.dictionary = dictionary;
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
