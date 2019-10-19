package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.Objects;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class CharCountCombination extends Combination<CharCount> {

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

    public CharCount getCharCountSum() {
        return charCountSum;
    }

    public int getTotalChars() {
        return charCountSum.totalChars();
    }

    public Combination<CharCount> unwrap() {
        return delegate;
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public CharCount get(int index) {
        return delegate.get(index);
    }

    @Override
    public Combination<CharCount> add(CharCount element) {
        return delegate.add(element);
    }

    @Override
    public Stream<CharCount> elements() {
        return delegate.elements();
    }
}
