package com.politaev.whiterabbit.anagram.search;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.politaev.whiterabbit.anagram.search.AnagramSearchContext.createAnagramSearchContext;
import static com.politaev.whiterabbit.anagram.search.CharCountCombinationGenerator.createGenerator;
import static com.politaev.whiterabbit.anagram.search.CombinationWithDesiredCharCountSumComposer.createCombinationComposer;
import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

public class MeetInTheMiddleAnagramSearchStrategy extends CompositeAnagramSearchStrategy {

    public static AddAnagramCharCount createSearchStrategy() {
        return anagramCharCount
                -> anagramWordLimit
                -> dictionary
                -> {
            SearchContextBuilder contextBuilder = new SearchContextBuilder(anagramCharCount, anagramWordLimit, dictionary);
            return new MeetInTheMiddleAnagramSearchStrategy(contextBuilder.createContext());
        };
    }

    MeetInTheMiddleAnagramSearchStrategy(AnagramSearchContext context) {
        super(context);
    }

    @Override
    List<AnagramSearchStrategy> createAnagramSearchStrategies() {
        AnagramSearchContext context = getContext();
        List<AnagramSearchStrategy> necessaryStrategies = new ArrayList<>();
        if (anagramHasEvenLength()) {
            necessaryStrategies.add(new TwoCombinationsAnagramSearchStrategy(context));
        }
        necessaryStrategies.addAll(asList(
                new SingleWordAnagramSearchStrategy(context),
                new CombinationPlusWordAnagramSearchStrategy(context),
                new WordInTheMiddleAnagramSearchStrategy(context)
        ));
        return unmodifiableList(necessaryStrategies);
    }

    static class SearchContextBuilder {

        private CharCount anagramCharCount;
        private int anagramWordLimit;
        private Dictionary dictionary;
        private CharCountCombinationGenerator generator;
        private Set<CharCountCombination> combinationsNotOverHalfAnagramLength;
        private CombinationWithDesiredCharCountSumComposer anagramComposer;

        SearchContextBuilder(CharCount anagramCharCount, int anagramWordLimit, Dictionary dictionary) {
            this.anagramCharCount = anagramCharCount;
            this.anagramWordLimit = anagramWordLimit;
            this.dictionary = dictionary;
            computeCombinationsUnderHalfAnagramLength();
            createAnagramComposer();
        }

        private void computeCombinationsUnderHalfAnagramLength() {
            createCombinationsGenerator();
            combinationsNotOverHalfAnagramLength = generator.generateAllWithinLimits();
        }

        private void createCombinationsGenerator() {
            generator = createGenerator()
                    .withDictionary(dictionary)
                    .withTotalCharsLimit(anagramCharCount.totalChars() / 2)
                    .withCombinationSizeLimit(anagramWordLimit - 1)
                    .withCharCountLimit(anagramCharCount);
        }

        private void createAnagramComposer() {
            anagramComposer = createCombinationComposer()
                    .composingCombinationsWithSumEqualTo(anagramCharCount)
                    .andSizeLimitedBy(anagramWordLimit)
                    .bySelectingAdditionsFrom(combinationsNotOverHalfAnagramLength);
        }

        AnagramSearchContext createContext() {
            return createAnagramSearchContext()
                    .toSearchAnagramsWithCharCountSum(anagramCharCount)
                    .withWordNumberLimitedBy(anagramWordLimit)
                    .withWordsFromDictionary(dictionary)
                    .withAnagramComposer(anagramComposer)
                    .usingCombinationsGeneratedInAdvance(combinationsNotOverHalfAnagramLength);
        }

    }

    public interface AddAnagramCharCount {
        AddAnagramWordLimit searchingAnagramsWithCharCountSum(CharCount anagramCharCount);
    }

    public interface AddAnagramWordLimit {
        AddDictionary withWordNumberLimitedBy(int anagramWordLimit);
    }

    public interface AddDictionary {
        MeetInTheMiddleAnagramSearchStrategy withWordsFromDictionary(Dictionary dictionary);
    }
}
