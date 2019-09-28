package com.politaev.whiterabbit.counter;

import java.util.Objects;

public class AlphabetSubsetCharCounter implements CharCounter {
    private final CharCounter fullAlphabetCharCounter;
    private final AlphabetCompression alphabetCompression;

    public static AlphabetSubsetCharCounter of(CharCounter fullAlphabetCharCounter, char... alphabetSubset) {
        Objects.requireNonNull(fullAlphabetCharCounter);
        Alphabet sourceAlphabet = fullAlphabetCharCounter.getAlphabet();
        Alphabet targetAlphabet = Alphabet.ofChars(alphabetSubset);
        AlphabetCompression alphabetCompression = AlphabetCompression.from(sourceAlphabet).to(targetAlphabet);
        return new AlphabetSubsetCharCounter(fullAlphabetCharCounter, alphabetCompression);
    }

    private AlphabetSubsetCharCounter(CharCounter fullAlphabetCharCounter, AlphabetCompression alphabetCompression) {
        this.fullAlphabetCharCounter = fullAlphabetCharCounter;
        this.alphabetCompression = alphabetCompression;
    }

    @Override
    public CharCount countChars(String string) {
        return fullAlphabetCharCounter.countChars(string).compress(alphabetCompression);
    }

    @Override
    public Alphabet getAlphabet() {
        return alphabetCompression.getTargetAlphabet();
    }
}
