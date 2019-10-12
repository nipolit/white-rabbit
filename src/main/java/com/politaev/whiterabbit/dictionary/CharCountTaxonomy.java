package com.politaev.whiterabbit.dictionary;

import com.politaev.whiterabbit.counter.CharCount;

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

import static com.politaev.whiterabbit.counter.CharCount.max;

public final class CharCountTaxonomy {
    private final NavigableSet<CharCount> navigableCharCounts;
    private final NavigableMap<Integer, CharCount> greatestElementsByTotalChars;

    CharCountTaxonomy() {
        navigableCharCounts = new TreeSet<>();
        greatestElementsByTotalChars = new TreeMap<>();
    }

    void add(CharCount charCount) {
        boolean added = navigableCharCounts.add(charCount);
        if (added) {
            adjustGreatestElementsMap(charCount);
        }
    }

    private void adjustGreatestElementsMap(CharCount addedCharCount) {
        int totalChars = addedCharCount.totalChars();
        greatestElementsByTotalChars.compute(totalChars,
                (key, currentGreatest) -> (currentGreatest == null) ? addedCharCount : max(currentGreatest, addedCharCount));
    }

    public Stream<CharCount> charCountsFromElementToTotalChars(CharCount fromElement, int toTotalChars) {
        requireContainedFromElement(fromElement);
        CharCount toElement = findToElement(toTotalChars);
        return charCountsFromElementToElement(fromElement, toElement);
    }

    private void requireContainedFromElement(CharCount fromElement) {
        if (!navigableCharCounts.contains(fromElement)) {
            throw new IllegalArgumentException("fromElement must be contained in the taxonomy");
        }
    }

    private CharCount findToElement(int toTotalChars) {
        Integer toElementKey = greatestElementsByTotalChars.floorKey(toTotalChars);
        return greatestElementsByTotalChars.get(toElementKey);
    }

    private CharCount findFromElement(int fromTotalChars) {
        if (navigableCharCounts.first().totalChars() >= fromTotalChars) return navigableCharCounts.first();
        CharCount elementBeforeFrom = findToElement(fromTotalChars - 1);
        return navigableCharCounts.higher(elementBeforeFrom);
    }

    private Stream<CharCount> charCountsFromElementToElement(CharCount fromElement, CharCount toElement) {
        requireValidBoundaries(fromElement, toElement);
        NavigableSet<CharCount> requestedCharCountSubSet = navigableCharCounts.subSet(fromElement, true, toElement, true);
        return requestedCharCountSubSet.stream();
    }

    private void requireValidBoundaries(CharCount fromElement, CharCount toElement) {
        if (fromElement.compareTo(toElement) > 0) {
            throw new IllegalArgumentException("Higher boundary must be greater than lower boundary");
        }
    }

    public Stream<CharCount> charCountsFromTotalCharsToTotalChars(int fromTotalChars, int toTotalChars) {
        requireValidTotalCharsInterval(fromTotalChars, toTotalChars);
        if (!elementsWithinIntervalPresent(fromTotalChars, toTotalChars)) return Stream.empty();
        CharCount fromElement = findFromElement(fromTotalChars);
        CharCount toElement = findToElement(toTotalChars);
        return charCountsFromElementToElement(fromElement, toElement);
    }

    private void requireValidTotalCharsInterval(int fromTotalChars, int toTotalChars) {
        if (fromTotalChars > toTotalChars) {
            throw new IllegalArgumentException("fromTotalChars > toTotalChars");
        }
    }

    private boolean elementsWithinIntervalPresent(int fromTotalChars, int toTotalChars) {
        if (navigableCharCounts.isEmpty()) return false;
        CharCount firstElement = navigableCharCounts.first();
        CharCount lastElement = navigableCharCounts.last();
        return firstElement.totalChars() <= toTotalChars
                && lastElement.totalChars() >= fromTotalChars;
    }

    public Stream<CharCount> charCountsOfLimitedTotalChars(int totalCharsLimit) {
        return charCountsFromTotalCharsToTotalChars(0, totalCharsLimit);
    }
}
