package com.politaev.whiterabbit.combinatorics;

import org.paukov.combinatorics3.IGenerator;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.paukov.combinatorics3.Generator.cartesianProduct;

public class OneToManySubstitutionFunctionResolver<T, R> {
    private final List<T> originalElements;
    private final Function<T, Collection<R>> substitutionFunction;

    public OneToManySubstitutionFunctionResolver(List<T> originalElements, Function<T, Collection<R>> substitutionFunction) {
        this.originalElements = originalElements;
        this.substitutionFunction = substitutionFunction;
    }

    public Stream<List<R>> substitutedElementsLists() {
        List<R>[] substitutesForEveryOriginalElement = computeAllSubstitutes();
        IGenerator<List<R>> substitutionCombinationsGenerator = cartesianProduct(substitutesForEveryOriginalElement);
        return substitutionCombinationsGenerator.stream();
    }

    @SuppressWarnings("unchecked")
    private List<R>[] computeAllSubstitutes() {
        return originalElements.stream()
                .map(substitutionFunction)
                .map(ArrayList::new)
                .toArray(List[]::new);
    }
}
