package com.politaev.whiterabbit.combinatorics;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class CombinationTest {
    @Test
    public void testEqualCombinations() {
        Combination<Integer> c1 = new Combination<>(1, 2, 3);
        Combination<Integer> c2 = new Combination<>(1, 2, 3);
        assertEquals(c1, c2);
    }

    @Test
    public void testDifferentCombinations() {
        Combination<Integer> c1 = new Combination<>(1, 2, 3);
        Combination<Integer> c2 = new Combination<>(2, 3, 4);
        assertNotEquals(c1, c2);
    }

    @Test
    public void testOrderOfElementsMattersNot() {
        Combination<Integer> c1 = new Combination<>(1, 2, 3);
        Combination<Integer> c2 = new Combination<>(3, 1, 2);
        assertEquals(c1, c2);
    }

    @Test
    public void testDuplicateElements() {
        Combination<Integer> c1 = new Combination<>(1, 2, 2);
        Combination<Integer> c2 = new Combination<>(1, 1, 2);
        assertNotEquals(c1, c2);
    }

    @Test
    public void testSize() {
        Combination<Integer> c = new Combination<>(1, 2, 3, 4, 5, 6, 7);
        assertEquals(7, c.size());
    }

    @Test
    public void testGetElement() {
        Combination<Integer> c = new Combination<>(1, 2, 3, 4, 5, 6, 7);
        int thirdElement = c.get(2);
        assertEquals(3, thirdElement);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void testGetElementOutOfBounds() {
        Combination<Integer> c = new Combination<>(1, 2, 3, 4, 5, 6, 7);
        c.get(10);
    }

    @Test
    public void testStreamElements() {
        Integer[] elements = new Integer[]{1, 2, 3, 4, 5, 6, 7};
        Combination<Integer> c = new Combination<>(elements);
        List<Integer> streamedElements = c.elements().collect(Collectors.toList());
        assertThat(streamedElements).containsExactly((Object[]) elements);
    }

    @Test
    public void testAddElement() {
        Combination<Integer> c1 = new Combination<>(1, 2, 3);
        Combination<Integer> c2 = new Combination<>(1, 3).add(2);
        assertEquals(c1, c2);
    }

    @Test
    public void testCombinationImmutableOnAdd() {
        Combination<Integer> c1 = new Combination<>(1, 2, 3);
        Combination<Integer> c2 = new Combination<>(1, 2, 3);
        c2.add(4);
        assertEquals(c1, c2);
    }

    @Test
    public void testAddCombination() {
        Combination<Integer> c1 = new Combination<>(1, 2, 3);
        Combination<Integer> c2 = new Combination<>(1, 3);
        Combination<Integer> c3 = c2.add(new Combination<>(2));
        assertEquals(c1, c3);
    }

    @Test
    public void testCombinationImmutableOnAddCombination() {
        Combination<Integer> c1 = new Combination<>(1, 3);
        Combination<Integer> c2 = new Combination<>(1, 3);
        c2.add(new Combination<>(2));
        assertEquals(c1, c2);
    }
}
