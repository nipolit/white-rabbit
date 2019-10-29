package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.CharCountTaxonomy;

import java.util.stream.Stream;

class CombinationPlusWordAnagramSearchStrategy extends AnagramSearchStrategy {

    private final CharCountTaxonomy taxonomy;

    CombinationPlusWordAnagramSearchStrategy(AnagramSearchContext context) {
        super(context);
        this.taxonomy = getDictionary().getTaxonomy();
    }

    @Override
    public Stream<Combination<CharCount>> search() {
        CombinationWithDesiredCharCountSumComposer anagramComposer = getAnagramComposer();
        return taxonomy.charCountsFromTotalCharsToTotalChars(wordLengthThreshold(), wordLengthLimit())
                .flatMap(anagramComposer::composeEndingWithCharCount);
    }

    private int wordLengthThreshold() {
        return anagramHasEvenLength() ?
                getAnagramCharCount().totalChars() / 2 + 1
                : getAnagramCharCount().totalChars() / 2;
    }

    private int wordLengthLimit() {
        return getAnagramCharCount().totalChars() - 1;
    }
}
