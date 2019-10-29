package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.Set;

class AnagramSearchContext {

    private final CharCount anagramCharCount;
    private final int anagramWordLimit;
    private final Dictionary dictionary;
    private final CombinationWithDesiredCharCountSumComposer anagramComposer;
    private final Set<CharCountCombination> combinations;

    static AddAnagramCharCount createAnagramSearchContext() {
        return anagramCharCount
                -> anagramWordLimit
                -> dictionary
                -> anagramComposer
                -> combinations
                -> new AnagramSearchContext(anagramCharCount, anagramWordLimit, dictionary, anagramComposer, combinations);
    }

    private AnagramSearchContext(CharCount anagramCharCount,
                                 int anagramWordLimit,
                                 Dictionary dictionary,
                                 CombinationWithDesiredCharCountSumComposer anagramComposer,
                                 Set<CharCountCombination> combinations) {
        this.anagramCharCount = anagramCharCount;
        this.anagramWordLimit = anagramWordLimit;
        this.dictionary = dictionary;
        this.anagramComposer = anagramComposer;
        this.combinations = combinations;
    }

    CharCount getAnagramCharCount() {
        return anagramCharCount;
    }

    int getAnagramWordLimit() {
        return anagramWordLimit;
    }

    Dictionary getDictionary() {
        return dictionary;
    }

    CombinationWithDesiredCharCountSumComposer getAnagramComposer() {
        return anagramComposer;
    }

    Set<CharCountCombination> getCombinations() {
        return combinations;
    }

    interface AddAnagramCharCount {
        AddAnagramWordLimit toSearchAnagramsWithCharCountSum(CharCount anagramCharCounts);
    }

    interface AddAnagramWordLimit {
        AddDictionary withWordNumberLimitedBy(int anagramWordLimit);
    }

    interface AddDictionary {
        AddAnagramComposer withWordsFromDictionary(Dictionary dictionary);
    }

    interface AddAnagramComposer {
        AddCombinations withAnagramComposer(CombinationWithDesiredCharCountSumComposer anagramComposer);
    }

    interface AddCombinations {
        AnagramSearchContext usingCombinationsGeneratedInAdvance(Set<CharCountCombination> combinations);
    }
}
