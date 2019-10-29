package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.anagram.GivenPhrase;
import com.politaev.whiterabbit.anagram.SizeLimit;
import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.util.AnnotationValueRule;
import org.junit.Before;
import org.junit.Rule;

import java.util.List;
import java.util.stream.Collectors;

public abstract class AnagramSearchStrategyTest extends AnagramTest {

    @Rule
    public AnnotationValueRule<GivenPhrase, String> givenPhrase = new AnnotationValueRule<>(GivenPhrase.class);

    @Rule
    public AnnotationValueRule<SizeLimit, Integer> sizeLimit = new AnnotationValueRule<>(SizeLimit.class);

    CharCount givenPhraseCharCount;
    int sizeLimitValue;
    AnagramSearchContext context;
    AnagramSearchStrategy searchStrategy;
    List<Combination<CharCount>> foundAnagrams;

    @Override
    @Before
    public void setUp() {
        super.setUp();
        initializeContext();
        performSearch();
    }

    private void initializeContext() {
        getRuleValues();
        MeetInTheMiddleAnagramSearchStrategy.SearchContextBuilder contextBuilder =
                new MeetInTheMiddleAnagramSearchStrategy.SearchContextBuilder(givenPhraseCharCount, sizeLimitValue, dictionary);
        context = contextBuilder.createContext();
    }

    private void getRuleValues() {
        getGivenPhrase();
        getSizeLimit();
    }

    private void getGivenPhrase() {
        givenPhraseCharCount = charCounter.countChars(givenPhrase.getValue());
    }

    private void getSizeLimit() {
        sizeLimitValue = (sizeLimit.getValue() != null) ? sizeLimit.getValue() : givenPhraseCharCount.totalChars();
    }

    private void performSearch() {
        searchStrategy = createSearchStrategy();
        foundAnagrams = searchStrategy.search().collect(Collectors.toList());
    }

    abstract AnagramSearchStrategy createSearchStrategy();
}
