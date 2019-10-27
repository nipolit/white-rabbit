package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.util.AnnotationValueRule;
import org.junit.Before;
import org.junit.Rule;

import java.util.Set;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombinationGenerator.createGenerator;
import static com.politaev.whiterabbit.anagram.finder.CombinationWithDesiredCharCountSumComposer.createCombinationComposer;

public abstract class AnagramSearchStrategyTest extends AnagramTest {

    @Rule
    public AnnotationValueRule<GivenPhrase, String> givenPhrase = new AnnotationValueRule<>(GivenPhrase.class);

    @Rule
    public AnnotationValueRule<SizeLimit, Integer> sizeLimit = new AnnotationValueRule<>(SizeLimit.class);

    CharCount givenPhraseCharCount;
    CombinationWithDesiredCharCountSumComposer anagramComposer;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        givenPhraseCharCount = charCounter.countChars(givenPhrase.getValue());
        anagramComposer = createAnagramComposer();
    }

    private CombinationWithDesiredCharCountSumComposer createAnagramComposer() {
        return createCombinationComposer()
                .composingCombinationsWithSumEqualTo(givenPhraseCharCount)
                .andSizeLimitedBy(getSizeLimit())
                .bySelectingAdditionsFrom(computeCombinationsUnderHalfGivenPhraseLength());
    }

    private int getSizeLimit() {
        Integer sizeLimitValue = sizeLimit.getValue();
        if (sizeLimitValue != null) return sizeLimitValue;
        else return givenPhraseCharCount.totalChars();
    }

    private Set<CharCountCombination> computeCombinationsUnderHalfGivenPhraseLength() {
        CharCountCombinationGenerator generator = createGenerator()
                .withDictionary(dictionary)
                .withTotalCharsLimit(givenPhraseCharCount.totalChars() / 2)
                .withCombinationSizeLimit(getSizeLimit() - 1)
                .withCharCountLimit(givenPhraseCharCount);
        return generator.generateAllWithinLimits();
    }
}
