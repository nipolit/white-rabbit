package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Stream;

import static java.util.Collections.emptyNavigableMap;
import static java.util.stream.Collectors.*;

public class CombinationWithDesiredCharCountSumComposer {
    private final CharCount desiredCharCountSum;
    private final int combinationSizeLimit;
    private final Map<CharCount, NavigableMap<CharCount, Set<Combination<CharCount>>>> organizedAvailablePieces;

    static AddDesiredCharCountSum createCombinationComposer() {
        return desiredCharCountSum
                -> combinationSizeLimit
                -> availablePieces
                -> new CombinationWithDesiredCharCountSumComposer(desiredCharCountSum, combinationSizeLimit, availablePieces);
    }

    private CombinationWithDesiredCharCountSumComposer(CharCount desiredCharCountSum, int combinationSizeLimit, Collection<CharCountCombination> availablePieces) {
        this.desiredCharCountSum = desiredCharCountSum;
        this.combinationSizeLimit = combinationSizeLimit;
        this.organizedAvailablePieces = organizePieces(availablePieces);
    }

    private Map<CharCount, NavigableMap<CharCount, Set<Combination<CharCount>>>> organizePieces(Collection<CharCountCombination> availablePieces) {
        return availablePieces.stream()
                .filter(this::pieceCanBeUsed)
                .collect(groupingByCharCountSumAndFirstElement());
    }

    private boolean pieceCanBeUsed(CharCountCombination piece) {
        return piece.size() < combinationSizeLimit;
    }

    private Collector<CharCountCombination, ?, Map<CharCount, NavigableMap<CharCount, Set<Combination<CharCount>>>>> groupingByCharCountSumAndFirstElement() {
        return groupingBy(
                CharCountCombination::getCharCountSum,
                groupingBy(
                        CharCountCombination::getFirstElement,
                        TreeMap::new,
                        mapping(CharCountCombination::unwrap, toSet())
                )
        );
    }

    Stream<Combination<CharCount>> composeStartingWithCombination(CharCountCombination charCountCombination) {
        return findAdditions(charCountCombination)
                .map(combination -> charCountCombination.unwrap().add(combination));
    }

    private Stream<Combination<CharCount>> findAdditions(CharCountCombination charCountCombination) {
        CharCount complementToDesiredCharCount = getComplementToDesiredCharCount(charCountCombination);
        NavigableMap<CharCount, Set<Combination<CharCount>>> fittingPartOfPieces = getPiecesWithCharCountSum(complementToDesiredCharCount)
                .tailMap(charCountCombination.getLastElement(), true);
        return streamFoundPieces(fittingPartOfPieces.values())
                .filter(fittingPiece -> charCountCombination.size() + fittingPiece.size() <= combinationSizeLimit);
    }

    private NavigableMap<CharCount, Set<Combination<CharCount>>> getPiecesWithCharCountSum(CharCount charCountSum) {
        return organizedAvailablePieces.getOrDefault(charCountSum, emptyNavigableMap());
    }

    private CharCount getComplementToDesiredCharCount(CharCountCombination charCountCombination) {
        return getComplementToDesiredCharCount(charCountCombination.getCharCountSum());
    }

    private CharCount getComplementToDesiredCharCount(CharCount charCount) {
        return desiredCharCountSum.subtract(charCount);
    }

    private Stream<Combination<CharCount>> streamFoundPieces(Collection<Set<Combination<CharCount>>> foundPieces) {
        return foundPieces.stream()
                .flatMap(Collection::stream);
    }

    Stream<Combination<CharCount>> composeEndingWithCharCount(CharCount charCount) {
        return findBaseCombinationsByAddition(charCount)
                .map(combination -> combination.add(charCount));
    }

    private Stream<Combination<CharCount>> findBaseCombinationsByAddition(CharCount charCount) {
        CharCount complementToDesiredCharCount = getComplementToDesiredCharCount(charCount);
        NavigableMap<CharCount, Set<Combination<CharCount>>> fittingPartOfPieces = getPiecesWithCharCountSum(complementToDesiredCharCount);
        return streamFoundPieces(fittingPartOfPieces.values())
                .filter(combination -> charCountCanBeAppendedToCombination(charCount, combination));
    }

    private boolean charCountCanBeAppendedToCombination(CharCount charCount, Combination<CharCount> combination) {
        return combination.get(combination.size() - 1).compareTo(charCount) < 1;
    }

    interface AddDesiredCharCountSum {
        AddCombinationSizeLimit composingCombinationsWithSumEqualTo(CharCount desiredCharCountSum);
    }

    interface AddCombinationSizeLimit {
        AddAvailablePieces andSizeLimitedBy(int combinationSizeLimit);
    }

    interface AddAvailablePieces {
        CombinationWithDesiredCharCountSumComposer bySelectingAdditionsFrom(Collection<CharCountCombination> availablePieces);
    }
}
