package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

class CharCountCombinationExtenderBuilder {
    private Dictionary dictionary;
    private Integer totalCharsThreshold;
    private Integer totalCharsLimit;
    private CharCount resultCharCountLimit;

    CharCountCombinationExtenderBuilder(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    CharCountCombinationExtenderBuilder withResultTotalCharsNotLowerThan(int totalCharsThreshold) {
        this.totalCharsThreshold = totalCharsThreshold;
        return this;
    }

    CharCountCombinationExtenderBuilder withResultTotalCharsNotHigherThan(int totalCharsLimit) {
        this.totalCharsLimit = totalCharsLimit;
        return this;
    }

    CharCountCombinationExtenderBuilder withExpectedTotalChars(int expectedTotalChars) {
        this.totalCharsThreshold = expectedTotalChars;
        this.totalCharsLimit = expectedTotalChars;
        return this;
    }

    CharCountCombinationExtenderBuilder withResultCharCountIncludedIn(CharCount resultCharCountLimit) {
        this.resultCharCountLimit = resultCharCountLimit;
        return this;
    }

    CharCountCombinationExtender build() {
        adjustTotalCharsLimit();
        validateBoundaries();
        return new CharCountCombinationExtender(dictionary, totalCharsThreshold, totalCharsLimit, resultCharCountLimit);
    }

    private void adjustTotalCharsLimit() {
        if (resultCharCountLimit != null) setEffectiveTotalCharsLimit();
    }

    private void setEffectiveTotalCharsLimit() {
        totalCharsLimit = (totalCharsLimit == null) ?
                resultCharCountLimit.totalChars() : Math.min(totalCharsLimit, resultCharCountLimit.totalChars());
    }

    private void validateBoundaries() {
        if (totalCharsThreshold != null && totalCharsLimit != null) {
            validateThresholdNotLowerThanLimit();
        }
    }

    private void validateThresholdNotLowerThanLimit() {
        if (totalCharsLimit < totalCharsThreshold) {
            throw new IllegalArgumentException("totalCharsThreshold higher than specified upper boundary");
        }
    }
}
