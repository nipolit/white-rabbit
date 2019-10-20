package com.politaev.whiterabbit.combinatorics;

import org.paukov.combinatorics3.IGenerator;

import java.util.List;
import java.util.stream.Stream;

import static org.paukov.combinatorics3.Generator.cartesianProduct;

public class OneToManySubstitutionResolver<T> {
    private final List<T>[] substitutesForEveryElement;

    @SafeVarargs
    public OneToManySubstitutionResolver(List<T>... substitutesForEveryElement) {
        this.substitutesForEveryElement = substitutesForEveryElement;
    }

    public Stream<List<T>> substitutedElementsLists() {
        IGenerator<List<T>> substitutionCombinationsGenerator = cartesianProduct(substitutesForEveryElement);
        return substitutionCombinationsGenerator.stream();
    }
}
