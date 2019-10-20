package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.CharCountTaxonomy;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombinationExtender.createExtender;
import static java.util.Collections.unmodifiableSet;

class CharCountCombinationGenerator {
    private final Dictionary dictionary;
    private final int totalCharsLimit;
    private final CharCount charCountLimit;
    private final CharCountCombinationExtender extender;

    static AddDictionary createGenerator() {
        return dictionary
                -> totalCharsLimit
                -> charCountLimit
                -> new CharCountCombinationGenerator(dictionary, totalCharsLimit, charCountLimit);
    }

    private CharCountCombinationGenerator(Dictionary dictionary,
                                          int totalCharsLimit,
                                          CharCount charCountLimit) {
        this.dictionary = dictionary;
        this.totalCharsLimit = totalCharsLimit;
        this.charCountLimit = charCountLimit;
        this.extender = initializeExtender();
    }

    private CharCountCombinationExtender initializeExtender() {
        return createExtender(dictionary)
                .withResultTotalCharsNotHigherThan(totalCharsLimit)
                .withResultCharCountIncludedIn(charCountLimit)
                .build();
    }

    Set<CharCountCombination> generateAllWithinLimits() {
        Set<CharCountCombination> allCombinations = new HashSet<>();
        Set<CharCountCombination> generationStepResult = generateOneWordCombinations();
        while (!generationStepResult.isEmpty()) {
            allCombinations.addAll(generationStepResult);
            generationStepResult = extendCombinationsByOneWord(generationStepResult);
        }
        return unmodifiableSet(allCombinations);
    }

    private Set<CharCountCombination> generateOneWordCombinations() {
        CharCountTaxonomy taxonomy = dictionary.getTaxonomy();
        return taxonomy.charCountsOfLimitedTotalChars(totalCharsLimit)
                .filter(charCountLimit::includes)
                .map(Combination<CharCount>::new)
                .map(CharCountCombination::wrap)
                .collect(Collectors.toSet());
    }

    private Set<CharCountCombination> extendCombinationsByOneWord(Set<CharCountCombination> generatedOnPreviousStep) {
        return streamCombinationsToBeExtended(generatedOnPreviousStep).parallel()
                .map(extender::extend)
                .flatMap(Function.identity())
                .map(CharCountCombination::wrap)
                .collect(Collectors.toSet());
    }

    private Stream<CharCountCombination> streamCombinationsToBeExtended(Set<CharCountCombination> combinations) {
        return combinations.stream()
                .filter(this::canBeExtended);
    }

    private boolean canBeExtended(CharCountCombination combination) {
        return combination.getCurrentLast().totalChars() <= totalCharsLimit - combination.getTotalChars();
    }

    interface AddDictionary {
        AddTotalCharsLimit withDictionary(Dictionary dictionary);
    }

    interface AddTotalCharsLimit {
        AddCharCountLimit withTotalCharsLimit(int totalCharsLimit);
    }

    interface AddCharCountLimit {
        CharCountCombinationGenerator withCharCountLimit(CharCount charCountLimit);
    }
}
