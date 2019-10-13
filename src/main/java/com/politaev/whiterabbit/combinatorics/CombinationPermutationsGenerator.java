package com.politaev.whiterabbit.combinatorics;

import org.paukov.combinatorics3.Generator;
import org.paukov.combinatorics3.IGenerator;

import java.util.List;
import java.util.stream.Stream;

public class CombinationPermutationsGenerator<T extends Comparable> {
    private final IGenerator<List<T>> delegate;

    public CombinationPermutationsGenerator(Combination<T> combination) {
        T[] elements = combination.getElements();
        delegate = Generator.permutation(elements).simple();
    }

    public Stream<List<T>> stream() {
        return delegate.stream();
    }
}
