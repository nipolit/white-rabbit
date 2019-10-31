package com.politaev.whiterabbit.controller;

import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.String.format;

public class ApplicationControllerBuilder {

    private String givenPhrase;
    private int wordLimit;
    private String[] md5SumsToFind;

    public ApplicationControllerBuilder withGivenPhrase(String givenPhrase) {
        this.givenPhrase = givenPhrase;
        return this;
    }

    public ApplicationControllerBuilder withWordLimit(int wordLimit) {
        this.wordLimit = wordLimit;
        return this;
    }

    public ApplicationControllerBuilder withMd5SumsToFind(String... md5SumsToFind) {
        this.md5SumsToFind = md5SumsToFind;
        return this;
    }

    public ApplicationController createController() {
        validateInput();
        return new ApplicationController(givenPhrase.toLowerCase(), wordLimit, md5SumsToFind);
    }

    private void validateInput() {
        requireValidPhrase();
        requirePositiveWordLimit();
        Objects.requireNonNull(md5SumsToFind);
    }

    private void requireValidPhrase() {
        Objects.requireNonNull(givenPhrase);
        requireNotEmptyPhrase();
        requireAllowedCharacters();
    }

    private void requireNotEmptyPhrase() {
        if (givenPhrase.isEmpty()) {
            throw new IllegalArgumentException("Empty given phrase");
        }
    }

    private void requireAllowedCharacters() {
        Matcher illegalCharacterMatcher = Pattern.compile("[^a-zA-Z ']")
                .matcher(givenPhrase);
        if (illegalCharacterMatcher.find()) {
            throw new IllegalArgumentException(format("Illegal character in given phrase: %s", illegalCharacterMatcher.group()));
        }
    }

    private void requirePositiveWordLimit() {
        if (wordLimit < 1) {
            throw new IllegalArgumentException("Non-positive word limit");
        }
    }
}
