package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.stream.Stream;

class TwoCombinationsAnagramSearchStrategy extends AnagramSearchStrategy {

    private final int requiredTotalCharsForStartingCombination;

    TwoCombinationsAnagramSearchStrategy(AnagramSearchContext context) {
        super(context);
        this.requiredTotalCharsForStartingCombination = determineRequiredTotalCharsForStartingCombination();
    }

    private int determineRequiredTotalCharsForStartingCombination() {
        return anagramHasEvenLength() ?
                getAnagramCharCount().totalChars() / 2
                : getAnagramCharCount().totalChars() / 2 + 1;
    }

    @Override
    public Stream<Combination<CharCount>> search() {
        CombinationWithDesiredCharCountSumComposer anagramComposer = getAnagramComposer();
        return getCombinations().stream()
                .filter(charCountCombination -> charCountCombination.getTotalChars() >= requiredTotalCharsForStartingCombination)
                .flatMap(anagramComposer::composeStartingWithCombination);
    }
}
