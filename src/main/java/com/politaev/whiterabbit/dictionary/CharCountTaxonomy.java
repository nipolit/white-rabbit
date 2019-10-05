package com.politaev.whiterabbit.dictionary;

import com.politaev.whiterabbit.counter.CharCount;

import java.util.NavigableMap;
import java.util.NavigableSet;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Stream;

import static com.politaev.whiterabbit.counter.CharCount.max;
import static java.util.Collections.emptyNavigableSet;

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

    public Stream<CharCount> charCountsOfLimitedTotalChars(int totalCharsLimit) {
        NavigableSet<CharCount> subSetWithRequestedLimit = subSetWithRequestedLimit(totalCharsLimit);
        return subSetWithRequestedLimit.stream();
    }

    private NavigableSet<CharCount> subSetWithRequestedLimit(int totalCharsLimit) {
        if (elementsLowerThanTotalCharsLimitPresent(totalCharsLimit)) {
            CharCount toCharCount = findBoundaryElement(totalCharsLimit);
            return navigableCharCounts.headSet(toCharCount, true);
        } else {
            return emptyNavigableSet();
        }
    }

    private boolean elementsLowerThanTotalCharsLimitPresent(int totalCharsLimit) {
        if (navigableCharCounts.isEmpty()) return false;
        CharCount firstElement = navigableCharCounts.first();
        return firstElement.totalChars() <= totalCharsLimit;
    }

    private CharCount findBoundaryElement(int totalCharsLimit) {
        Integer boundaryElementKey = greatestElementsByTotalChars.floorKey(totalCharsLimit);
        return greatestElementsByTotalChars.get(boundaryElementKey);
    }

    public Stream<CharCount> charCountsOfLimitedTotalCharsStartingFromElement(CharCount fromElement, int totalCharsLimit) {
        requireValidFromElement(fromElement, totalCharsLimit);
        CharCount toCharCount = findBoundaryElement(totalCharsLimit);
        NavigableSet<CharCount> requestedCharCountSubSet = navigableCharCounts.subSet(fromElement, true, toCharCount, true);
        return requestedCharCountSubSet.stream();
    }

    private void requireValidFromElement(CharCount fromElement, int totalCharsLimit) {
        requireFromElementWithinTotalCharsLimit(fromElement, totalCharsLimit);
        requireContainedFromElement(fromElement);
    }

    private void requireFromElementWithinTotalCharsLimit(CharCount fromElement, int totalCharsLimit) {
        if (fromElement.totalChars() > totalCharsLimit) {
            throw new IllegalArgumentException("fromElement must be within the totalCharsLimit");
        }
    }

    private void requireContainedFromElement(CharCount fromElement) {
        if (!navigableCharCounts.contains(fromElement)) {
            throw new IllegalArgumentException("fromElement must be contained in the taxonomy");
        }
    }
}
