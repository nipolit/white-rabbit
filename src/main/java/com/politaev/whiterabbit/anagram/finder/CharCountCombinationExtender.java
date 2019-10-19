package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.CharCountTaxonomy;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.stream.Stream;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombination.wrap;

class CharCountCombinationExtender {
    private final CharCountTaxonomy charCountTaxonomy;
    private final Integer totalCharsThreshold;
    private final Integer totalCharsLimit;
    private final CharCount resultCharCountLimit;

    static CharCountCombinationExtenderBuilder createExtender(Dictionary dictionary) {
        return new CharCountCombinationExtenderBuilder(dictionary);
    }

    CharCountCombinationExtender(Dictionary dictionary,
                                 Integer totalCharsThreshold,
                                 Integer totalCharsLimit,
                                 CharCount resultCharCountLimit) {
        this.charCountTaxonomy = dictionary.getTaxonomy();
        this.totalCharsThreshold = totalCharsThreshold;
        this.totalCharsLimit = totalCharsLimit;
        this.resultCharCountLimit = resultCharCountLimit;
    }

    Stream<Combination<CharCount>> extend(Combination<CharCount> originalCombination) {
        CharCountCombination charCountCombination = wrap(originalCombination);
        requireCombinationCompatibleWithBoundaries(charCountCombination);
        AdditionFinder additionFinder = new AdditionFinder(charCountCombination);
        return additionFinder.findAdditions()
                .map(charCountCombination::add);
    }

    private void requireCombinationCompatibleWithBoundaries(CharCountCombination combination) {
        requireCombinationCompatibleWithUpperBoundary(combination);
        requireCombinationCompatibleWithLowerBoundary(combination);
    }

    private void requireCombinationCompatibleWithUpperBoundary(CharCountCombination combination) {
        if (resultCharCountLimit != null) {
            requireCombinationIncludedInCharCountLimit(combination);
        }
        if (totalCharsLimit != null) {
            requireTotalCharsLowerThanLimit(combination);
        }
    }

    private void requireCombinationIncludedInCharCountLimit(CharCountCombination combination) {
        if (!resultCharCountLimit.includes(combination.getCharCountSum())) {
            throw new IllegalArgumentException("Combination not included in CharCount limit");
        }
    }

    private void requireTotalCharsLowerThanLimit(CharCountCombination combination) {
        if (combination.getTotalChars() >= totalCharsLimit) {
            throw new IllegalArgumentException("Combination total chars not lower than limit");
        }
    }

    private void requireCombinationCompatibleWithLowerBoundary(CharCountCombination combination) {
        if (totalCharsThreshold != null) {
            requireTotalCharsNotHigherThanThreshold(combination);
        }
    }

    private void requireTotalCharsNotHigherThanThreshold(CharCountCombination combination) {
        if (combination.getTotalChars() > totalCharsThreshold) {
            throw new IllegalArgumentException("Combination total chars higher than threshold");
        }
    }

    private class AdditionFinder {
        private final CharCountCombination originalCombination;
        private final int additionTotalCharsThreshold;
        private final int additionTotalCharsLimit;
        private final CharCount additionCharCountLimit;

        private AdditionFinder(CharCountCombination originalCombination) {
            this.originalCombination = originalCombination;
            this.additionTotalCharsThreshold = determineAdditionTotalCharsThreshold();
            this.additionTotalCharsLimit = determineAdditionTotalCharsLimit();
            this.additionCharCountLimit = determineAdditionCharCountLimit();
        }

        private int determineAdditionTotalCharsThreshold() {
            return (totalCharsThreshold != null) ? totalCharsThreshold - originalCombination.getTotalChars() : 0;
        }

        private int determineAdditionTotalCharsLimit() {
            return (totalCharsLimit != null) ? totalCharsLimit - originalCombination.getTotalChars() : Integer.MAX_VALUE;
        }

        private CharCount determineAdditionCharCountLimit() {
            return (resultCharCountLimit != null) ? resultCharCountLimit.subtract(originalCombination.getCharCountSum()) : null;
        }

        Stream<CharCount> findAdditions() {
            Stream<CharCount> additionsWithinTotalCharsBoundaries = findAdditionsWithinTotalCharsBoundaries();
            if (additionCharCountLimit != null) {
                return filterAdditionsIncludedInCharCountLimit(additionsWithinTotalCharsBoundaries);
            } else {
                return additionsWithinTotalCharsBoundaries;
            }
        }

        Stream<CharCount> findAdditionsWithinTotalCharsBoundaries() {
            if (isThresholdEffectiveLowerBoundary()) {
                return charCountTaxonomy.charCountsFromTotalCharsToTotalChars(additionTotalCharsThreshold, additionTotalCharsLimit);
            } else {
                return charCountTaxonomy.charCountsFromElementToTotalChars(getCurrentLast(), additionTotalCharsLimit);
            }
        }

        private boolean isThresholdEffectiveLowerBoundary() {
            return getCurrentLast().totalChars() < additionTotalCharsThreshold;
        }

        private CharCount getCurrentLast() {
            return originalCombination.get(originalCombination.size() - 1);
        }

        private Stream<CharCount> filterAdditionsIncludedInCharCountLimit(Stream<CharCount> additions) {
            return additions.filter(additionCharCountLimit::includes);
        }
    }
}