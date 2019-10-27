package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.Set;
import java.util.stream.Stream;

class TwoCombinationsAnagramSearchStrategy extends AnagramSearchStrategy {

    private final Set<CharCountCombination> combinations;
    private final CombinationWithDesiredCharCountSumComposer anagramComposer;
    private final int requiredTotalCharsForStartingCombination;

    TwoCombinationsAnagramSearchStrategy(CharCount anagramCharCount, Set<CharCountCombination> combinations, CombinationWithDesiredCharCountSumComposer anagramComposer) {
        super(anagramCharCount);
        this.combinations = combinations;
        this.anagramComposer = anagramComposer;
        this.requiredTotalCharsForStartingCombination = determineRequiredTotalCharsForStartingCombination();
    }

    private int determineRequiredTotalCharsForStartingCombination() {
        return anagramHasEvenLength() ?
                anagramCharCount.totalChars() / 2
                : anagramCharCount.totalChars() / 2 + 1;
    }

    @Override
    public Stream<Combination<CharCount>> search() {
        return combinations.stream()
                .filter(charCountCombination -> charCountCombination.getTotalChars() >= requiredTotalCharsForStartingCombination)
                .flatMap(anagramComposer::composeStartingWithCombination);
    }
}
