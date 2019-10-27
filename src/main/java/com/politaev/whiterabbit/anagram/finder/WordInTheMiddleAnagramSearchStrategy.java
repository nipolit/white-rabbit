package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.stream.Stream;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombinationExtender.createExtender;

public class WordInTheMiddleAnagramSearchStrategy extends AnagramSearchStrategy {

    private final int minLengthOfCombinationExtendedWithWord;

    WordInTheMiddleAnagramSearchStrategy(AnagramSearchContext context) {
        super(context);
        this.minLengthOfCombinationExtendedWithWord = determineMinLengthOfExtendedCombination();
    }

    private int determineMinLengthOfExtendedCombination() {
        return getAnagramCharCount().totalChars() / 2 + 1;
    }

    @Override
    Stream<Combination<CharCount>> search() {
        CombinationWithDesiredCharCountSumComposer anagramComposer = getAnagramComposer();
        return getCombinations().stream()
                .filter(this::validBaseForWordInTheMiddleAnagram)
                .flatMap(this::streamExtendedCombinations)
                .flatMap(anagramComposer::composeStartingWithCombination);
    }

    private boolean validBaseForWordInTheMiddleAnagram(CharCountCombination combination) {
        return atLeastTwoWordsCanBeAdded(combination)
                && addedWordCanBeginAndEndInDifferentHalvesOfResult(combination)
                && addedWordLeavesSpaceForFollowingWords(combination);
    }

    private boolean atLeastTwoWordsCanBeAdded(CharCountCombination combination) {
        return combination.size() <= getAnagramWordLimit() - 2;
    }

    private boolean addedWordCanBeginAndEndInDifferentHalvesOfResult(CharCountCombination combination) {
        return !anagramHasEvenLength()
                || combination.getTotalChars() < getAnagramCharCount().totalChars() / 2;
    }

    private boolean addedWordLeavesSpaceForFollowingWords(CharCountCombination combination) {
        int minLengthOfAddedWord = Math.max(
                combination.getLastElement().totalChars(),
                minLengthOfCombinationExtendedWithWord - combination.getTotalChars()
        );
        return getFreeSpace(combination) >= 2 * minLengthOfAddedWord;
    }

    private int getFreeSpace(CharCountCombination combination) {
        return getAnagramCharCount().totalChars() - combination.getTotalChars();
    }

    private Stream<CharCountCombination> streamExtendedCombinations(CharCountCombination combination) {
        CharCountCombinationExtender wordInTheMiddleAdder = createWordInTheMiddleAdder(combination);
        return wordInTheMiddleAdder.extend(combination)
                .map(CharCountCombination::wrap);
    }

    private CharCountCombinationExtender createWordInTheMiddleAdder(CharCountCombination combination) {
        return createExtender(getDictionary())
                .withResultCharCountIncludedIn(getAnagramCharCount())
                .withResultTotalCharsNotLowerThan(minLengthOfCombinationExtendedWithWord)
                .withResultTotalCharsNotHigherThan(combination.getTotalChars() + getFreeSpace(combination) / 2)
                .build();
    }
}
