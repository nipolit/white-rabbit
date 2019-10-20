package com.politaev.whiterabbit.combinatorics;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

public class OneToManySubstitutionFunctionResolver<T, R> {
    private final List<T> originalElements;
    private final Function<T, Collection<R>> substitutionFunction;

    public OneToManySubstitutionFunctionResolver(List<T> originalElements, Function<T, Collection<R>> substitutionFunction) {
        this.originalElements = originalElements;
        this.substitutionFunction = substitutionFunction;
    }

    public Stream<List<R>> substitutedElementsLists() {
        OneToManySubstitutionResolver<R> substitutionResolver = new OneToManySubstitutionResolver<>(computeAllSubstitutes());
        return substitutionResolver.substitutedElementsLists();
    }

    @SuppressWarnings("unchecked")
    private List<R>[] computeAllSubstitutes() {
        return originalElements.stream()
                .map(substitutionFunction)
                .map(ArrayList::new)
                .toArray(List[]::new);
    }
}
