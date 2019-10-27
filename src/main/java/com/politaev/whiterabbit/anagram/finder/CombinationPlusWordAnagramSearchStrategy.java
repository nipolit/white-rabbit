package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.CharCountTaxonomy;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.stream.Stream;

class CombinationPlusWordAnagramSearchStrategy implements AnagramSearchStrategy {

    private final CharCount anagramCharCount;
    private final CharCountTaxonomy taxonomy;
    private final CombinationWithDesiredCharCountSumComposer anagramComposer;

    CombinationPlusWordAnagramSearchStrategy(CharCount anagramCharCount, Dictionary dictionary, CombinationWithDesiredCharCountSumComposer anagramComposer) {
        this.anagramCharCount = anagramCharCount;
        this.taxonomy = dictionary.getTaxonomy();
        this.anagramComposer = anagramComposer;
    }

    @Override
    public Stream<Combination<CharCount>> search() {
        return taxonomy.charCountsFromTotalCharsToTotalChars(wordLengthThreshold(), wordLengthLimit())
                .flatMap(anagramComposer::composeEndingWithCharCount);
    }

    private int wordLengthThreshold() {
        return anagramHasEvenLength() ?
                anagramCharCount.totalChars() / 2 + 1
                : anagramCharCount.totalChars() / 2;
    }

    private boolean anagramHasEvenLength() {
        return anagramCharCount.totalChars() % 2 == 0;
    }

    private int wordLengthLimit() {
        return anagramCharCount.totalChars() - 1;
    }
}
