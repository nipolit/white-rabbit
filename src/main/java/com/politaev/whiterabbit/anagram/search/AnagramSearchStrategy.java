package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.Set;
import java.util.stream.Stream;

public abstract class AnagramSearchStrategy {

    private final AnagramSearchContext context;

    AnagramSearchStrategy(AnagramSearchContext context) {
        this.context = context;
    }

    public abstract Stream<Combination<CharCount>> search();

    CharCount getAnagramCharCount() {
        return context.getAnagramCharCount();
    }

    int getAnagramWordLimit() {
        return context.getAnagramWordLimit();
    }

    Dictionary getDictionary() {
        return context.getDictionary();
    }

    CombinationWithDesiredCharCountSumComposer getAnagramComposer() {
        return context.getAnagramComposer();
    }

    Set<CharCountCombination> getCombinations() {
        return context.getCombinations();
    }

    AnagramSearchContext getContext() {
        return context;
    }

    boolean anagramHasEvenLength() {
        return getAnagramCharCount().totalChars() % 2 == 0;
    }
}
