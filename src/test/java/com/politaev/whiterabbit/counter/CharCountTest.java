package com.politaev.whiterabbit.counter;

import org.junit.Test;

import static com.politaev.whiterabbit.counter.Alphabet.LATIN;
import static com.politaev.whiterabbit.counter.CharCount.countLatinChars;
import static org.junit.Assert.*;

public class CharCountTest {
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
        Alphabet customAlphabet = Alphabet.ofChars('a', 'd', 'f', 'g', 'l', 'n');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(customAlphabet);
        CharCount customCharCount = countLatinChars(string2).compress(alphabetCompression);
        latinCharCount.add(customCharCount);
    }

    @Test
    public void testAddCharCountWithCustomAlphabet() {
        String string1 = "gandalf", string2 = "saruman";
        Alphabet customAlphabet = Alphabet.ofChars('a', 'd', 'f', 'g', 'l', 'm', 'n', 'r', 's', 'u');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(customAlphabet);
        CharCount charCount1 = countLatinChars(string1).compress(alphabetCompression);
        CharCount charCount2 = countLatinChars(string2).compress(alphabetCompression);
        CharCount actualSum = charCount1.add(charCount2);
        CharCount expectedSum = countLatinChars(string1 + string2).compress(alphabetCompression);
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
        Alphabet customAlphabet = Alphabet.ofChars('c', 'h', 'i', 'l', 'n', 'u');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(customAlphabet);
        CharCount customCharCount = countLatinChars(string2).compress(alphabetCompression);
        latinCharCount.subtract(customCharCount);
    }

    @Test
    public void testSubtractCharCountWithCustomAlphabet() {
        String string1 = "mortalkombat", string2 = "motaro";
        Alphabet customAlphabet = Alphabet.ofChars('a', 'b', 'k', 'l', 'm', 'o', 'r', 't');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(customAlphabet);
        CharCount charCount1 = countLatinChars(string1).compress(alphabetCompression);
        CharCount charCount2 = countLatinChars(string2).compress(alphabetCompression);
        CharCount actualDiff = charCount1.subtract(charCount2);
        CharCount expectedDiff = countLatinChars("lkmbat").compress(alphabetCompression);
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
        Alphabet customAlphabet = Alphabet.ofChars('a', 'b', 'e', 'h', 'n', 'o', 'r', 't');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(customAlphabet);
        CharCount customCharCount = countLatinChars(string2).compress(alphabetCompression);
        latinCharCount.subtract(customCharCount);
    }

    @Test
    public void testIncludesCharCountWithCustomAlphabet() {
        String string1 = "baratheon", string2 = "robert";
        Alphabet customAlphabet = Alphabet.ofChars('a', 'b', 'e', 'h', 'o', 'n', 'r', 't');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(customAlphabet);
        CharCount charCount1 = countLatinChars(string1).compress(alphabetCompression);
        CharCount charCount2 = countLatinChars(string2).compress(alphabetCompression);
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
        Alphabet targetAlphabet = Alphabet.ofChars('a', 'b', 'c');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(targetAlphabet);
        initialCharCount.compress(alphabetCompression);
        assertEquals(countLatinChars(string), initialCharCount);
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
        Alphabet targetAlphabet = Alphabet.ofChars('a', 'b', 'c', 'd', 'e', 'h', 'n', 'r', 'u', 'z');
        AlphabetCompression alphabetCompression = AlphabetCompression.from(LATIN).to(targetAlphabet);
        CharCount charCount = countLatinChars(string).compress(alphabetCompression);
        int expectedTotalChars = string.length();
        assertEquals(expectedTotalChars, charCount.totalChars());
    }
}
