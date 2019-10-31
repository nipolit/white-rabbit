package com.politaev.whiterabbit.controller;

import com.politaev.whiterabbit.anagram.AnagramFinder;
import com.politaev.whiterabbit.counter.AlphabetSubsetCharCounter;
import com.politaev.whiterabbit.counter.CharCounter;
import com.politaev.whiterabbit.counter.LatinCharCounter;
import com.politaev.whiterabbit.dictionary.Dictionary;
import com.politaev.whiterabbit.md5.Md5SumFinder;
import com.politaev.whiterabbit.reader.WordReader;
import com.politaev.whiterabbit.reader.filter.AllowedCharactersFilter;
import com.politaev.whiterabbit.reader.filter.CharCountLimitFilter;
import com.politaev.whiterabbit.reader.filter.NotEmptyFilter;

import java.io.IOException;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ApplicationController {

    private final AnagramFinder anagramFinder;
    private final Md5SumFinder md5SumFinder;

    public static ApplicationControllerBuilder initializeApplicationContext() {
        return new ApplicationControllerBuilder();
    }

    ApplicationController(String givenPhrase, int wordLimit, String[] md5SumsToFind) {
        AnagramFinderInitializer anagramFinderInitializer = new AnagramFinderInitializer(givenPhrase, wordLimit);
        this.anagramFinder = anagramFinderInitializer.createAnagramFinder();
        this.md5SumFinder = createMd5SumFinder(md5SumsToFind);
    }

    private Md5SumFinder createMd5SumFinder(String[] md5SumsToFind) {
        return Md5SumFinder.createFinder()
                .searchingMd5Sums(md5SumsToFind)
                .performingOperationOnFoundItems(this::printFoundAnagram)
                .build();
    }

    private void printFoundAnagram(String md5Sum, String anagram) {
        String message = String.format("%s %s", md5Sum, anagram);
        System.out.println(message);
    }

    public void runApplication() {
        anagramFinder.findAnagrams()
                .forEach(md5SumFinder::check);
    }

    private static class AnagramFinderInitializer {

        private final String givenPhrase;
        private final int wordLimit;
        private final CharCounter charCounter;
        private final Dictionary dictionary;

        private AnagramFinderInitializer(String givenPhrase, int wordLimit) {
            this.givenPhrase = givenPhrase;
            this.wordLimit = wordLimit;
            this.charCounter = createCharCounter();
            this.dictionary = new Dictionary(charCounter);
            populateDictionary();
        }

        private CharCounter createCharCounter() {
            char[] givenPhraseLetters = streamGivenPhraseLetters()
                    .map(Object::toString)
                    .collect(Collectors.joining())
                    .toCharArray();
            return AlphabetSubsetCharCounter.of(new LatinCharCounter(), givenPhraseLetters);
        }

        private Stream<Character> streamGivenPhraseLetters() {
            return givenPhrase.chars()
                    .mapToObj(charIndex -> (char) charIndex)
                    .filter(Character::isLetter)
                    .distinct();
        }

        private void populateDictionary() {
            try (WordReader wordReader = new WordReader("wordlist")) {
                wordFilters()
                        .sequential()
                        .forEach(wordReader::addWordFilter);
                wordReader.words()
                        .sequential()
                        .forEach(dictionary::add);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        private Stream<Predicate<String>> wordFilters() {
            return Stream.of(
                    new NotEmptyFilter(),
                    createAllowedCharactersFilter(),
                    new CharCountLimitFilter(new LatinCharCounter(), givenPhrase)
            );
        }

        private AllowedCharactersFilter createAllowedCharactersFilter() {
            AllowedCharactersFilter allowedCharactersFilter = new AllowedCharactersFilter();
            streamGivenPhraseLetters().forEach(allowedCharactersFilter::allow);
            allowedCharactersFilter.allow('\'');
            return allowedCharactersFilter;
        }

        AnagramFinder createAnagramFinder() {
            return AnagramFinder.createAnagramFinder()
                    .searchingAnagramsWithCharCountSum(charCounter.countChars(givenPhrase))
                    .withWordNumberLimitedBy(wordLimit)
                    .withWordsFromDictionary(dictionary);
        }
    }
}
