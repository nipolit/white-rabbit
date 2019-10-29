package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.Set;
import java.util.stream.Stream;

import static com.politaev.whiterabbit.anagram.search.AnagramSearchContext.createAnagramSearchContext;
import static com.politaev.whiterabbit.anagram.search.CharCountCombinationGenerator.createGenerator;
import static com.politaev.whiterabbit.anagram.search.CombinationWithDesiredCharCountSumComposer.createCombinationComposer;

public class AnagramFinder {

    private final Dictionary dictionary;
    private final CharCount anagramCharCount;
    private final int anagramWordLimit;
    private final Set<CharCountCombination> combinationsNotOverHalfAnagramLength;
    private final AnagramSearchStrategy anagramSearchStrategy;

    public static AddAnagramCharCount createAnagramFinder() {
        return anagramCharCount
                -> anagramWordLimit
                -> dictionary
                -> new AnagramFinder(anagramCharCount, anagramWordLimit, dictionary);
    }

    private AnagramFinder(CharCount anagramCharCount, int anagramWordLimit, Dictionary dictionary) {
        this.anagramCharCount = anagramCharCount;
        this.anagramWordLimit = anagramWordLimit;
        this.dictionary = dictionary;
        this.combinationsNotOverHalfAnagramLength = computeCombinationsUnderHalfAnagramLength();
        this.anagramSearchStrategy = new MeetInTheMiddleAnagramSearchStrategy(createContext());
    }

    private Set<CharCountCombination> computeCombinationsUnderHalfAnagramLength() {
        CharCountCombinationGenerator generator = createGenerator()
                .withDictionary(dictionary)
                .withTotalCharsLimit(anagramCharCount.totalChars() / 2)
                .withCombinationSizeLimit(anagramWordLimit - 1)
                .withCharCountLimit(anagramCharCount);
        return generator.generateAllWithinLimits();
    }

    private AnagramSearchContext createContext() {
        return createAnagramSearchContext()
                .toSearchAnagramsWithCharCountSum(anagramCharCount)
                .withWordNumberLimitedBy(anagramWordLimit)
                .withWordsFromDictionary(dictionary)
                .withAnagramComposer(createAnagramComposer())
                .usingCombinationsGeneratedInAdvance(combinationsNotOverHalfAnagramLength);
    }

    private CombinationWithDesiredCharCountSumComposer createAnagramComposer() {
        return createCombinationComposer()
                .composingCombinationsWithSumEqualTo(anagramCharCount)
                .andSizeLimitedBy(anagramWordLimit)
                .bySelectingAdditionsFrom(combinationsNotOverHalfAnagramLength);
    }

    public Stream<Combination<CharCount>> findAllCombinations() {
        return anagramSearchStrategy.search();
    }

    public interface AddAnagramCharCount {
        AddAnagramWordLimit searchingAnagramsWithCharCountSum(CharCount anagramCharCount);
    }

    public interface AddAnagramWordLimit {
        AddDictionary withWordNumberLimitedBy(int anagramWordLimit);
    }

    public interface AddDictionary {
        AnagramFinder withWordsFromDictionary(Dictionary dictionary);
    }
}
