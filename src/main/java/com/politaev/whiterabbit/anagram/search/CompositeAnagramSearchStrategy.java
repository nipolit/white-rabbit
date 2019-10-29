package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.List;
import java.util.stream.Stream;

abstract class CompositeAnagramSearchStrategy extends AnagramSearchStrategy {

    private final List<AnagramSearchStrategy> strategies;

    CompositeAnagramSearchStrategy(AnagramSearchContext context) {
        super(context);
        this.strategies = createAnagramSearchStrategies();
    }

    abstract List<AnagramSearchStrategy> createAnagramSearchStrategies();

    @Override
    public Stream<Combination<CharCount>> search() {
        return strategies.stream()
                .flatMap(AnagramSearchStrategy::search);
    }
}
