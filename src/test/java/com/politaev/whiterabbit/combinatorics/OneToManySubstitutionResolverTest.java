package com.politaev.whiterabbit.combinatorics;

import org.junit.Test;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static org.fest.assertions.Assertions.assertThat;

public class OneToManySubstitutionResolverTest {
    @Test
    public void testAllSubstitutionCombinationsStreamed() {
        List<String> originalElements = asList("ab", "-", "123");
        Function<String, Collection<Character>> substitutionFunction = s -> s.chars()
                .mapToObj(c -> (char) c)
                .collect(Collectors.toList());
        OneToManySubstitutionResolver<String, Character> substituteStringWithItsCharResolver = new OneToManySubstitutionResolver<>(originalElements, substitutionFunction);
        List<List<Character>> allSubstitutions = substituteStringWithItsCharResolver.substitutedElementsLists().collect(Collectors.toList());

        assertThat(allSubstitutions).hasSize(6);
        assertThat(allSubstitutions).contains(asList('a', '-', '1'));
        assertThat(allSubstitutions).contains(asList('a', '-', '2'));
        assertThat(allSubstitutions).contains(asList('a', '-', '3'));
        assertThat(allSubstitutions).contains(asList('b', '-', '1'));
        assertThat(allSubstitutions).contains(asList('b', '-', '2'));
        assertThat(allSubstitutions).contains(asList('b', '-', '3'));
    }

}
