package com.politaev.whiterabbit.combinatorics;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;
import static org.junit.Assert.assertEquals;


public class CombinationPermutationsGeneratorTest {
    @Test
    public void testAllPermutationsStreamed() {
        Combination<String> combination = new Combination<>("monkey", "donkey", "goat", "bear");
        CombinationPermutationsGenerator<String> permutationsGenerator = new CombinationPermutationsGenerator<>(combination);
        List<List<String>> permutations = permutationsGenerator.stream().collect(Collectors.toList());

        assertThat(permutations).hasSize(24);
        assertThat(permutations).contains(asList("bear", "donkey", "goat", "monkey"));
        assertThat(permutations).contains(asList("bear", "donkey", "monkey", "goat"));
        assertThat(permutations).contains(asList("bear", "goat", "donkey", "monkey"));
        assertThat(permutations).contains(asList("bear", "goat", "monkey", "donkey"));
        assertThat(permutations).contains(asList("bear", "monkey", "donkey", "goat"));
        assertThat(permutations).contains(asList("bear", "monkey", "goat", "donkey"));
        assertThat(permutations).contains(asList("donkey", "bear", "goat", "monkey"));
        assertThat(permutations).contains(asList("donkey", "bear", "monkey", "goat"));
        assertThat(permutations).contains(asList("donkey", "goat", "bear", "monkey"));
        assertThat(permutations).contains(asList("donkey", "goat", "monkey", "bear"));
        assertThat(permutations).contains(asList("donkey", "monkey", "bear", "goat"));
        assertThat(permutations).contains(asList("donkey", "monkey", "goat", "bear"));
        assertThat(permutations).contains(asList("goat", "bear", "donkey", "monkey"));
        assertThat(permutations).contains(asList("goat", "bear", "monkey", "donkey"));
        assertThat(permutations).contains(asList("goat", "donkey", "bear", "monkey"));
        assertThat(permutations).contains(asList("goat", "donkey", "monkey", "bear"));
        assertThat(permutations).contains(asList("goat", "monkey", "bear", "donkey"));
        assertThat(permutations).contains(asList("goat", "monkey", "donkey", "bear"));
        assertThat(permutations).contains(asList("monkey", "bear", "donkey", "goat"));
        assertThat(permutations).contains(asList("monkey", "bear", "goat", "donkey"));
        assertThat(permutations).contains(asList("monkey", "goat", "bear", "donkey"));
        assertThat(permutations).contains(asList("monkey", "goat", "donkey", "bear"));
        assertThat(permutations).contains(asList("monkey", "donkey", "bear", "goat"));
        assertThat(permutations).contains(asList("monkey", "donkey", "goat", "bear"));
    }

    @Test
    public void testDuplicatePermutationsOmitted() {
        Combination<Integer> combination = new Combination<>(0, 1, 1);
        CombinationPermutationsGenerator<Integer> permutationsGenerator = new CombinationPermutationsGenerator<>(combination);
        List<List<Integer>> permutations = permutationsGenerator.stream().collect(Collectors.toList());

        assertThat(permutations).hasSize(3);
        assertThat(permutations).contains(asList(0, 1, 1));
        assertThat(permutations).contains(asList(1, 0, 1));
        assertThat(permutations).contains(asList(1, 1, 0));
    }

    @Test
    public void testCombinationNotModifiedByPermutationGeneration() {
        Combination<String> combination1 = new Combination<>("monkey", "donkey", "goat", "bear");
        Combination<String> combination2 = new Combination<>("monkey", "donkey", "goat", "bear");
        CombinationPermutationsGenerator<String> permutationsGenerator = new CombinationPermutationsGenerator<>(combination1);
        permutationsGenerator.stream().count();

        assertEquals(combination1, combination2);
    }
}