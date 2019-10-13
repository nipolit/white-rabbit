package com.politaev.whiterabbit.combinatorics;

import java.util.Arrays;

public class Combination<T extends Comparable> {
    private final T[] elements;

    public Combination(T... elements) {
        this.elements = Arrays.copyOf(elements, elements.length);
        Arrays.sort(this.elements);
    }

    public int size() {
        return elements.length;
    }

    public T get(int index) {
        return elements[index];
    }

    public Combination<T> add(T element) {
        T[] extendedElements = Arrays.copyOf(elements, elements.length + 1);
        extendedElements[extendedElements.length - 1] = element;
        return new Combination<>(extendedElements);
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof Combination)) return false;
        Combination<?> otherCombination = (Combination<?>) other;
        return Arrays.equals(elements, otherCombination.elements);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(elements);
    }
}
