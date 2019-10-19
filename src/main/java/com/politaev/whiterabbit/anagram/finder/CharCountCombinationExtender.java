package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.CharCountTaxonomy;
import com.politaev.whiterabbit.dictionary.Dictionary;

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
}
