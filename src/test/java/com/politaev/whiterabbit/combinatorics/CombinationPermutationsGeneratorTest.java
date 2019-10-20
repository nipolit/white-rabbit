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

        assertThat(permutations).containsOnly(
                asList("bear", "donkey", "goat", "monkey"),
                asList("bear", "donkey", "monkey", "goat"),
                asList("bear", "goat", "donkey", "monkey"),
                asList("bear", "goat", "monkey", "donkey"),
                asList("bear", "monkey", "donkey", "goat"),
                asList("bear", "monkey", "goat", "donkey"),
                asList("donkey", "bear", "goat", "monkey"),
                asList("donkey", "bear", "monkey", "goat"),
                asList("donkey", "goat", "bear", "monkey"),
                asList("donkey", "goat", "monkey", "bear"),
                asList("donkey", "monkey", "bear", "goat"),
                asList("donkey", "monkey", "goat", "bear"),
                asList("goat", "bear", "donkey", "monkey"),
                asList("goat", "bear", "monkey", "donkey"),
                asList("goat", "donkey", "bear", "monkey"),
                asList("goat", "donkey", "monkey", "bear"),
                asList("goat", "monkey", "bear", "donkey"),
                asList("goat", "monkey", "donkey", "bear"),
                asList("monkey", "bear", "donkey", "goat"),
                asList("monkey", "bear", "goat", "donkey"),
                asList("monkey", "goat", "bear", "donkey"),
                asList("monkey", "goat", "donkey", "bear"),
                asList("monkey", "donkey", "bear", "goat"),
                asList("monkey", "donkey", "goat", "bear"));
    }

    @Test
    public void testDuplicatePermutationsOmitted() {
        Combination<Integer> combination = new Combination<>(0, 1, 1);
        CombinationPermutationsGenerator<Integer> permutationsGenerator = new CombinationPermutationsGenerator<>(combination);
        List<List<Integer>> permutations = permutationsGenerator.stream().collect(Collectors.toList());

        assertThat(permutations).containsOnly(
                asList(0, 1, 1),
                asList(1, 0, 1),
                asList(1, 1, 0));
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
