package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import java.util.Set;
import java.util.stream.Collectors;

import static com.politaev.whiterabbit.anagram.search.CharCountCombination.wrap;
import static com.politaev.whiterabbit.anagram.search.CharCountCombinationGenerator.createGenerator;
import static org.fest.assertions.Assertions.assertThat;

public class CharCountCombinationGeneratorTest extends AnagramTest {
    @Test
    public void testGeneratesAllCombinations() {
        CharCount charCountLimit = charCounter.countChars("abbb");
        CharCountCombinationGenerator generator = createGenerator()
                .withDictionary(dictionary)
                .withTotalCharsLimit(3)
                .withCombinationSizeLimit(3)
                .withCharCountLimit(charCountLimit);
        Set<CharCountCombination> generatedCombinations = generator.generateAllWithinLimits();
        assertThat(generatedCombinations).containsOnly(
                wrap(charCountCombinationOf("a")),
                wrap(charCountCombinationOf("b")),
                wrap(charCountCombinationOf("ab")),
                wrap(charCountCombinationOf("bb")),
                wrap(charCountCombinationOf("abb")),
                wrap(charCountCombinationOf("bbb")),
                wrap(charCountCombinationOf("a", "b")),
                wrap(charCountCombinationOf("b", "b")),
                wrap(charCountCombinationOf("a", "bb")),
                wrap(charCountCombinationOf("b", "ab")),
                wrap(charCountCombinationOf("b", "bb")),
                wrap(charCountCombinationOf("a", "b", "b")),
                wrap(charCountCombinationOf("b", "b", "b"))
        );
    }

    private Set<Combination<CharCount>> unwrapCombinations(Set<CharCountCombination> wrapped) {
        return wrapped.stream()
                .map(CharCountCombination::unwrap)
                .collect(Collectors.toSet());
    }

    @Test
    public void testGeneratesAllCombinationsLimitWordCount() {
        CharCount charCountLimit = charCounter.countChars("abbb");
        CharCountCombinationGenerator generator = createGenerator()
                .withDictionary(dictionary)
                .withTotalCharsLimit(3)
                .withCombinationSizeLimit(2)
                .withCharCountLimit(charCountLimit);
        Set<CharCountCombination> generatedCombinations = generator.generateAllWithinLimits();
        assertThat(generatedCombinations).containsOnly(
                wrap(charCountCombinationOf("a")),
                wrap(charCountCombinationOf("b")),
                wrap(charCountCombinationOf("ab")),
                wrap(charCountCombinationOf("bb")),
                wrap(charCountCombinationOf("abb")),
                wrap(charCountCombinationOf("bbb")),
                wrap(charCountCombinationOf("a", "b")),
                wrap(charCountCombinationOf("b", "b")),
                wrap(charCountCombinationOf("a", "bb")),
                wrap(charCountCombinationOf("b", "ab")),
                wrap(charCountCombinationOf("b", "bb"))
        );
    }
}
