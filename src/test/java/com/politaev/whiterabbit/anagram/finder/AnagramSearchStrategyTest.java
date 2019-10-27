package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.util.AnnotationValueRule;
import org.junit.Before;
import org.junit.Rule;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.politaev.whiterabbit.anagram.finder.AnagramSearchContext.createAnagramSearchContext;
import static com.politaev.whiterabbit.anagram.finder.CharCountCombinationGenerator.createGenerator;
import static com.politaev.whiterabbit.anagram.finder.CombinationWithDesiredCharCountSumComposer.createCombinationComposer;

public abstract class AnagramSearchStrategyTest extends AnagramTest {

    @Rule
    public AnnotationValueRule<GivenPhrase, String> givenPhrase = new AnnotationValueRule<>(GivenPhrase.class);

    @Rule
    public AnnotationValueRule<SizeLimit, Integer> sizeLimit = new AnnotationValueRule<>(SizeLimit.class);

    CharCount givenPhraseCharCount;
    Set<CharCountCombination> combinationsNotOverHalfAnagramLength;
    AnagramSearchContext context;
    AnagramSearchStrategy searchStrategy;
    List<Combination<CharCount>> foundAnagrams;

    @Override
    @Before
    public void setUp() {
        initializeContext();
        performSearch();
    }

    private void initializeContext() {
        super.setUp();
        givenPhraseCharCount = charCounter.countChars(givenPhrase.getValue());
        combinationsNotOverHalfAnagramLength = computeCombinationsUnderHalfGivenPhraseLength();
        context = createAnagramSearchContext()
                .toSearchAnagramsWithCharCountSum(givenPhraseCharCount)
                .withWordNumberLimitedBy(getSizeLimit())
                .withWordsFromDictionary(dictionary)
                .withAnagramComposer(createAnagramComposer())
                .usingCombinationsGeneratedInAdvance(combinationsNotOverHalfAnagramLength);
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

    private CombinationWithDesiredCharCountSumComposer createAnagramComposer() {
        return createCombinationComposer()
                .composingCombinationsWithSumEqualTo(givenPhraseCharCount)
                .andSizeLimitedBy(getSizeLimit())
                .bySelectingAdditionsFrom(combinationsNotOverHalfAnagramLength);
    }

    private void performSearch() {
        searchStrategy = createSearchStrategy();
        foundAnagrams = searchStrategy.search().collect(Collectors.toList());
    }

    abstract AnagramSearchStrategy createSearchStrategy();
}
