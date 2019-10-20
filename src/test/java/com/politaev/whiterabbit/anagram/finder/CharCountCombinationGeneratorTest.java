package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombinationGenerator.createGenerator;
import static org.fest.assertions.Assertions.assertThat;

public class CharCountCombinationGeneratorTest extends AnagramTest {
    @Test
    public void testGeneratesAllCombinations() {
        CharCount charCountLimit = charCounter.countChars("abbb");
        CharCountCombinationGenerator generator = createGenerator()
                .withDictionary(dictionary)
                .withTotalCharsLimit(3)
                .withCharCountLimit(charCountLimit);
        Set<CharCountCombination> generatedCombinations = generator.generateAllWithinLimits();
        Set<Combination<CharCount>> unwrappedCombinations = unwrapCombinations(generatedCombinations);
        assertThat(unwrappedCombinations).containsOnly(
                charCountCombinationOf("a"),
                charCountCombinationOf("b"),
                charCountCombinationOf("ab"),
                charCountCombinationOf("bb"),
                charCountCombinationOf("abb"),
                charCountCombinationOf("bbb"),
                charCountCombinationOf("a", "b"),
                charCountCombinationOf("b", "b"),
                charCountCombinationOf("a", "bb"),
                charCountCombinationOf("b", "ab"),
                charCountCombinationOf("b", "bb"),
                charCountCombinationOf("a", "b", "b"),
                charCountCombinationOf("b", "b", "b")
        );
    }

    private Set<Combination<CharCount>> unwrapCombinations(Set<CharCountCombination> wrapped) {
        return wrapped.stream()
                .map(CharCountCombination::unwrap)
                .collect(Collectors.toSet());
    }
}