package com.politaev.whiterabbit.anagram;

import com.politaev.whiterabbit.anagram.output.PhraseResolver;
import com.politaev.whiterabbit.anagram.search.AnagramSearchStrategy;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.stream.Stream;

import static com.politaev.whiterabbit.anagram.search.MeetInTheMiddleAnagramSearchStrategy.createSearchStrategy;

public class AnagramFinder {

    private final AnagramSearchStrategy anagramSearchStrategy;
    private final PhraseResolver phraseResolver;

    public static AddAnagramCharCount createAnagramFinder() {
        return anagramCharCount
                -> anagramWordLimit
                -> dictionary
                -> new AnagramFinder(anagramCharCount, anagramWordLimit, dictionary);
    }

    private AnagramFinder(CharCount anagramCharCount, int anagramWordLimit, Dictionary dictionary) {
        anagramSearchStrategy = createSearchStrategy()
                .searchingAnagramsWithCharCountSum(anagramCharCount)
                .withWordNumberLimitedBy(anagramWordLimit)
                .withWordsFromDictionary(dictionary);
        phraseResolver = new PhraseResolver(dictionary);
    }

    public Stream<String> findAnagrams() {
        return anagramSearchStrategy.search()
                .flatMap(phraseResolver::resolve);
    }

    public interface AddAnagramCharCount {
        AddAnagramWordLimit searchingAnagramsWithCharCountSum(CharCount anagramCharCount);
    }

    public interface AddAnagramWordLimit {
        AddDictionary withWordNumberLimitedBy(int anagramWordLimit);
    }

    public interface AddDictionary {
        AnagramFinder withWordsFromDictionary(Dictionary dictionary);
    }
}
