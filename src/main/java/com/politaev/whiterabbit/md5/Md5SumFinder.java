package com.politaev.whiterabbit.md5;

import java.util.Collection;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.apache.commons.codec.digest.DigestUtils.md5Hex;

public class Md5SumFinder {

    private final Set<String> md5SumsToFind;
    private final FoundItemsConsumer foundItemsConsumer;

    public static Md5SumFinderBuilder createFinder() {
        return new Md5SumFinderBuilder();
    }

    private Md5SumFinder(Collection<String> md5Sums, FoundItemsConsumer foundItemsConsumer) {
        this.md5SumsToFind = new HashSet<>(md5Sums);
        this.foundItemsConsumer = foundItemsConsumer;
    }

    public void check(String input) {
        String inputMd5Sum = md5Hex(input);
        if (md5SumsToFind.contains(inputMd5Sum)) {
            foundItemsConsumer.accept(inputMd5Sum, input);
        }
    }

    public static class Md5SumFinderBuilder {

        private Pattern md5SumRegex = Pattern.compile("[0-9a-fA-F]{32}");
        private String[] md5Sums;
        private FoundItemsConsumer foundItemsConsumer;

        public Md5SumFinderBuilder searchingMd5Sums(String... md5Sums) {
            this.md5Sums = md5Sums;
            requireValidMd5Sums();
            return this;
        }

        private void requireValidMd5Sums() {
            requireNotEmpty();
            requireMatchPattern();
        }

        private void requireNotEmpty() {
            if (md5Sums.length == 0) {
                throw new IllegalArgumentException("No md5 sums to find");
            }
        }

        private void requireMatchPattern() {
            Stream.of(md5Sums).forEach(this::requireMatchPattern);
        }

        private void requireMatchPattern(String md5Sum) {
            if (!md5SumRegex.matcher(md5Sum).matches()) {
                throw new IllegalArgumentException("Invalid md5 sum string");
            }
        }

        public Md5SumFinderBuilder performingOperationOnFoundItems(FoundItemsConsumer foundItemsConsumer) {
            this.foundItemsConsumer = foundItemsConsumer;
            return this;
        }

        public Md5SumFinder build() {
            Objects.requireNonNull(md5Sums);
            Objects.requireNonNull(foundItemsConsumer);
            return new Md5SumFinder(md5SumsToLowerCase(), foundItemsConsumer);
        }

        private Collection<String> md5SumsToLowerCase() {
            return Stream.of(md5Sums)
                    .map(String::toLowerCase)
                    .collect(Collectors.toList());
        }
    }

    public interface FoundItemsConsumer {
        void accept(String md5Sum, String input);
    }
}
