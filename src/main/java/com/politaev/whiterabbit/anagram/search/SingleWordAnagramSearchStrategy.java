package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.stream.Stream;

class SingleWordAnagramSearchStrategy extends AnagramSearchStrategy {

    SingleWordAnagramSearchStrategy(AnagramSearchContext context) {
        super(context);
    }

    @Override
    public Stream<Combination<CharCount>> search() {
        if (getDictionary().containsWordsWithCharCount(getAnagramCharCount())) {
            return Stream.of(new Combination<>(getAnagramCharCount()));
        } else {
            return Stream.empty();
        }
    }
}
