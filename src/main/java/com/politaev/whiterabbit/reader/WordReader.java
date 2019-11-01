package com.politaev.whiterabbit.reader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class WordReader implements Closeable {
    private final BufferedReader bufferedReader;
    private final List<Predicate<String>> wordFilters;

    public WordReader(String pathToResource) throws IOException {
        URL resourceURL = getResourceURL(pathToResource);
        bufferedReader = new BufferedReader(new InputStreamReader(resourceURL.openStream()));
        wordFilters = new ArrayList<>();
    }

    private URL getResourceURL(String resourceName) {
        ClassLoader classLoader = getClass().getClassLoader();
        URL resourceURL = classLoader.getResource(resourceName);
        Objects.requireNonNull(resourceURL, "Resource not found");
        return resourceURL;
    }

    public void addWordFilter(Predicate<String> wordFilter) {
        wordFilters.add(wordFilter);
    }

    public Stream<String> words() {
        Predicate<String> composedWordFilter = composeFilter();
        return bufferedReader.lines()
                .filter(composedWordFilter);
    }

    private Predicate<String> composeFilter() {
        return wordFilters.stream()
                .reduce(Predicate::and)
                .orElse(s -> true);
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
