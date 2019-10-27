package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.stream.Stream;

abstract class AnagramSearchStrategy {

    final CharCount anagramCharCount;

    AnagramSearchStrategy(CharCount anagramCharCount) {
        this.anagramCharCount = anagramCharCount;
    }

    abstract Stream<Combination<CharCount>> search();

    boolean anagramHasEvenLength() {
        return anagramCharCount.totalChars() % 2 == 0;
    }
}
