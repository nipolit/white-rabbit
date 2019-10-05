package com.politaev.whiterabbit.reader.filter;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;

public class AllowedCharactersFilter implements Predicate<String> {
    private final Set<Character> allowedCharacters;

    public AllowedCharactersFilter() {
        allowedCharacters = new HashSet<>();
    }

    @Override
    public boolean test(String s) {
        return s.chars()
                .mapToObj(c -> (char) c)
                .allMatch(allowedCharacters::contains);
    }

    public void allow(Character character) {
        allowedCharacters.add(character);
    }
}
