package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.Objects;
import java.util.stream.IntStream;

class CharCountCombination {
    private final Combination<CharCount> delegate;
    private final CharCount charCountSum;

    static CharCountCombination wrap(Combination<CharCount> combination) {
        requireNotEmpty(combination);
        return new CharCountCombination(combination);
    }

    private static void requireNotEmpty(Combination<CharCount> combination) {
        Objects.requireNonNull(combination);
        if (combination.size() == 0) {
            throw new IllegalArgumentException("Empty combination");
        }
    }

    private CharCountCombination(Combination<CharCount> delegate) {
        this.delegate = delegate;
        this.charCountSum = sumUpElements();
    }

    private CharCount sumUpElements() {
        CharCount first = delegate.get(0);
        return IntStream.range(1, delegate.size())
                .mapToObj(delegate::get)
                .reduce(first, CharCount::add);
    }

    CharCount getCharCountSum() {
        return charCountSum;
    }

    int getTotalChars() {
        return charCountSum.totalChars();
    }

    CharCount getFirstElement() {
        return delegate.get(0);
    }

    CharCount getLastElement() {
        return delegate.get(delegate.size() - 1);
    }

    Combination<CharCount> unwrap() {
        return delegate;
    }

    Combination<CharCount> add(CharCount element) {
        return delegate.add(element);
    }
}
