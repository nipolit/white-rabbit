package com.politaev.whiterabbit.combinatorics;

import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class OneToManySubstitutionFunctionResolverTest {
    @Test
    public void testAllSubstitutionCombinationsStreamed() {
        List<String> originalElements = asList("ab", "-", "123");
        Function<String, Collection<Character>> substitutionFunction = s -> s.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        OneToManySubstitutionFunctionResolver<String, Character> substituteStringWithItsCharResolver = new OneToManySubstitutionFunctionResolver<>(originalElements, substitutionFunction);
        List<List<Character>> allSubstitutions = substituteStringWithItsCharResolver.substitutedElementsLists().collect(Collectors.toList());

        assertThat(allSubstitutions).containsOnly(
                asList('a', '-', '1'),
                asList('a', '-', '2'),
                asList('a', '-', '3'),
                asList('b', '-', '1'),
                asList('b', '-', '2'),
                asList('b', '-', '3'));
    }

}
