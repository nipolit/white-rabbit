package com.politaev.whiterabbit.combinatorics;

import org.junit.Test;

import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;
import static java.util.Collections.singletonList;
import static org.fest.assertions.Assertions.assertThat;

public class OneToManySubstitutionResolverTest {

    @Test
    public void testAllSubstitutionCombinationsStreamed() {
        OneToManySubstitutionResolver<String> substitutionResolver = new OneToManySubstitutionResolver<>(
                asList("rum", "gin"),
                singletonList("-"),
                asList("cola", "tonic", "juice")
        );
        List<List<String>> allSubstitutions = substitutionResolver.substitutedElementsLists().collect(Collectors.toList());

        assertThat(allSubstitutions).containsOnly(
                asList("rum", "-", "cola"),
                asList("rum", "-", "tonic"),
                asList("rum", "-", "juice"),
                asList("gin", "-", "cola"),
                asList("gin", "-", "tonic"),
                asList("gin", "-", "juice"));
    }
}
