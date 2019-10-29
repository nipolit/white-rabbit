package com.politaev.whiterabbit.anagram.output;

import com.politaev.whiterabbit.combinatorics.Combination;
import com.politaev.whiterabbit.combinatorics.CombinationPermutationsGenerator;
import com.politaev.whiterabbit.combinatorics.OneToManySubstitutionFunctionResolver;
import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.dictionary.Dictionary;

import java.util.List;
import java.util.stream.Stream;

public class PhraseResolver {

    private final Dictionary dictionary;

    public PhraseResolver(Dictionary dictionary) {
        this.dictionary = dictionary;
    }

    public Stream<String> resolve(Combination<CharCount> combination) {
        return generatePermutations(combination)
                .flatMap(this::substituteCharCountsWithWords)
                .map(this::joinWords);
    }

    private Stream<List<CharCount>> generatePermutations(Combination<CharCount> combination) {
        CombinationPermutationsGenerator<CharCount> generator = new CombinationPermutationsGenerator<>(combination);
        return generator.stream();
    }

    private Stream<List<String>> substituteCharCountsWithWords(List<CharCount> permutation) {
        OneToManySubstitutionFunctionResolver<CharCount, String> resolver = new OneToManySubstitutionFunctionResolver<>(permutation, dictionary::getWords);
        return resolver.substitutedElementsLists();
    }

    private String joinWords(List<String> words) {
        return String.join(" ", words);
    }
}
