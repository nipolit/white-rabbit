package com.politaev.whiterabbit.counter;

import org.junit.Before;
import org.junit.Test;

import static com.politaev.whiterabbit.counter.Alphabet.LATIN;
import static org.junit.Assert.*;

public class CharCountTest {
    private LatinCharCounter latinCharCounter;

    @Before
    public void setUp() {
        latinCharCounter = new LatinCharCounter();
    }

    private CharCount countLatinChars(String string) {
        return latinCharCounter.countChars(string);
    }

    @Test
    public void testCharCountSum() {
        String string1 = "benedict", string2 = "cumberbatch";
        CharCount charCount1 = countLatinChars(string1);
        CharCount charCount2 = countLatinChars(string2);
        CharCount actualSum = charCount1.add(charCount2);
        CharCount expectedSum = countLatinChars(string1 + string2);
        assertEquals(expectedSum, actualSum);
    }

    @Test
    public void testAddNotChangesState() {
        String string = "sharks";
        CharCount initialCharCount = countLatinChars(string);
        initialCharCount.add(countLatinChars("lasers"));
        assertEquals(countLatinChars(string), initialCharCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testAddCharCountWithDifferentAlphabet() {
        String string1 = "dumbledore", string2 = "gandalf";
        CharCount latinCharCount = countLatinChars(string1);
        CharCount customCharCount = countAlphabetSubset(string2, 'a', 'd', 'f', 'g', 'l', 'n');
        latinCharCount.add(customCharCount);
    }

    private CharCount countAlphabetSubset(String string, char... latinAlphabetSubset) {
        CharCounter charCounter = alphabetSubsetCharCounter(latinAlphabetSubset);
        return charCounter.countChars(string);
    }

    private CharCounter alphabetSubsetCharCounter(char... latinAlphabetSubset) {
        return AlphabetSubsetCharCounter.of(latinCharCounter, latinAlphabetSubset);
    }

    @Test
    public void testAddCharCountWithCustomAlphabet() {
        String string1 = "gandalf", string2 = "saruman";
        CharCounter alphabetSubsetCharCounter = alphabetSubsetCharCounter('a', 'd', 'f', 'g', 'l', 'm', 'n', 'r', 's', 'u');
        CharCount charCount1 = alphabetSubsetCharCounter.countChars(string1);
        CharCount charCount2 = alphabetSubsetCharCounter.countChars(string2);
        CharCount actualSum = charCount1.add(charCount2);
        CharCount expectedSum = alphabetSubsetCharCounter.countChars(string1 + string2);
        assertEquals(expectedSum, actualSum);
    }

    @Test
    public void testCharCountDiff() {
        String string1 = "pineapple", string2 = "pine";
        CharCount charCount1 = countLatinChars(string1);
        CharCount charCount2 = countLatinChars(string2);
        CharCount actualDiff = charCount1.subtract(charCount2);
        CharCount expectedDiff = countLatinChars("apple");
        assertEquals(expectedDiff, actualDiff);
    }

    @Test
    public void testSubtractNotChangesState() {
        String string = "sharkswithlasers";
        CharCount initialCharCount = countLatinChars(string);
        initialCharCount.subtract(countLatinChars("lasers"));
        assertEquals(countLatinChars(string), initialCharCount);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testSubtractCharCountWithDifferentAlphabet() {
        String string1 = "mortalkombat", string2 = "chunli";
        CharCount latinCharCount = countLatinChars(string1);
        CharCount customCharCount = countAlphabetSubset(string2, 'c', 'h', 'i', 'l', 'n', 'u');
        latinCharCount.subtract(customCharCount);
    }

    @Test
    public void testSubtractCharCountWithCustomAlphabet() {
        String string1 = "mortalkombat", string2 = "motaro";
        CharCounter alphabetSubsetCharCounter = alphabetSubsetCharCounter('a', 'b', 'k', 'l', 'm', 'o', 'r', 't');
        CharCount charCount1 = alphabetSubsetCharCounter.countChars(string1);
        CharCount charCount2 = alphabetSubsetCharCounter.countChars(string2);
        CharCount actualDiff = charCount1.subtract(charCount2);
        CharCount expectedDiff = alphabetSubsetCharCounter.countChars("lkmbat");
        assertEquals(expectedDiff, actualDiff);
    }

    @Test
    public void testCharCountNotIncludesOther() {
        String string = "team", notIncludedString = "i";
        assertFalse(
                countLatinChars(string).includes(countLatinChars(notIncludedString))
        );
    }

    @Test
    public void testCharCountIncludesOther() {
        String string = "team", includedString = "me";
        assertTrue(
                countLatinChars(string).includes(countLatinChars(includedString))
        );
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIncludesCharCountWithDifferentAlphabet() {
        String string1 = "baratheon", string2 = "batman";
        CharCount latinCharCount = countLatinChars(string1);
        CharCount customCharCount = countAlphabetSubset(string2, 'a', 'b', 'e', 'h', 'n', 'o', 'r', 't');
        latinCharCount.subtract(customCharCount);
    }

    @Test
    public void testIncludesCharCountWithCustomAlphabet() {
        String string1 = "baratheon", string2 = "robert";
        CharCounter alphabetSubsetCharCounter = alphabetSubsetCharCounter('a', 'b', 'e', 'h', 'n', 'o', 'r', 't');
        CharCount charCount1 = alphabetSubsetCharCounter.countChars(string1);
        CharCount charCount2 = alphabetSubsetCharCounter.countChars(string2);
        assertFalse(charCount1.includes(charCount2));
    }

    @Test
    public void testCompressCharCount() {
        String string = "abaca";
        Alphabet targetAlphabet = Alphabet.ofChars('a', 'b', 'c');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(targetAlphabet);
        CharCount charCount = countLatinChars(string).compress(alphabetCompression);
        assertEquals(targetAlphabet, charCount.getAlphabet());
    }

    @Test
    public void testCompressNotChangesState() {
        String string = "abaca";
        CharCount initialCharCount = countLatinChars(string);
        AlphabetCompression alphabetCompression = fromLatinToCustom('a', 'b', 'c');
        initialCharCount.compress(alphabetCompression);
        assertEquals(countLatinChars(string), initialCharCount);
    }

    private AlphabetCompression fromLatinToCustom(char... customAlphabetChars) {
        Alphabet customAlphabet = Alphabet.ofChars(customAlphabetChars);
        return AlphabetCompression.from(LATIN).to(customAlphabet);
    }

    @Test
    public void testCharCountTotalChars() {
        String string = "nebuchadnezzar";
        CharCount charCount = countLatinChars(string);
        int expectedTotalChars = string.length();
        assertEquals(expectedTotalChars, charCount.totalChars());
    }

    @Test
    public void testCharCountTotalCharsWithCustomAlphabet() {
        String string = "nebuchadnezzar";
        CharCount charCount = countAlphabetSubset(string, 'a', 'b', 'c', 'd', 'e', 'h', 'n', 'r', 'u', 'z');
        int expectedTotalChars = string.length();
        assertEquals(expectedTotalChars, charCount.totalChars());
    }
}
