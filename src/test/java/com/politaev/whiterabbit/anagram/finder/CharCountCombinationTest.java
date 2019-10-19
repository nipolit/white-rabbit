package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombination.wrap;
import static org.junit.Assert.assertEquals;

public class CharCountCombinationTest extends AnagramTest {

    @Test(expected = IllegalArgumentException.class)
    public void testWrapEmptyCombination() {
        Combination<CharCount> emptyCombination = new Combination<>();
        wrap(emptyCombination);
    }

    @Test
    public void getCharCountSum() {
        String[] combinationWords = new String[]{"dave", "dee", "dozy", "beaky", "mick", "tich"};
        Combination<CharCount> combination = charCountCombinationOf(combinationWords);
        CharCountCombination charCountCombination = wrap(combination);
        String joinedCombinationWords = String.join("", combinationWords);
        CharCount expectedCharCountSum = charCounter.countChars(joinedCombinationWords);
        assertEquals(expectedCharCountSum, charCountCombination.getCharCountSum());
    }

    @Test
    public void getTotalChars() {
        String[] combinationWords = new String[]{"chocolate", "starfish", "hot", "dog", "flavored", "water"};
        Combination<CharCount> combination = charCountCombinationOf(combinationWords);
        CharCountCombination charCountCombination = wrap(combination);
        String joinedCombinationWords = String.join("", combinationWords);
        assertEquals(joinedCombinationWords.length(), charCountCombination.getTotalChars());
    }
}
