package com.politaev.whiterabbit.reader;

import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.stream.Stream;

public class WordReader implements Closeable {
    private final BufferedReader bufferedReader;

    public WordReader(String pathToResource) throws FileNotFoundException {
        File file = getFileFromResources(pathToResource);
        bufferedReader = new BufferedReader(new FileReader(file));
    }

    private static File getFileFromResources(String fileName) {
        ClassLoader classLoader = WordReader.class.getClassLoader();
        URL resource = classLoader.getResource(fileName);
        Objects.requireNonNull(resource, "File not found");
        return new File(resource.getFile());
    }

    public Stream<String> words() {
        return bufferedReader.lines();
    }

    @Override
    public void close() throws IOException {
        bufferedReader.close();
    }
}
