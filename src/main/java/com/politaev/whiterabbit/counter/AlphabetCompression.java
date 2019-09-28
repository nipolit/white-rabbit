package com.politaev.whiterabbit.counter;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

class AlphabetCompression {
    private final Alphabet sourceAlphabet;
    private final Alphabet targetAlphabet;
    private final int[] compressionArray;

    private AlphabetCompression(Alphabet sourceAlphabet, Alphabet targetAlphabet, int[] compressionArray) {
        this.sourceAlphabet = sourceAlphabet;
        this.targetAlphabet = targetAlphabet;
        this.compressionArray = compressionArray;
    }

    public static CompressionBuilder from(Alphabet alphabet) {
        Objects.requireNonNull(alphabet);
        return new CompressionBuilder(alphabet);
    }

    Alphabet getSourceAlphabet() {
        return sourceAlphabet;
    }

    Alphabet getTargetAlphabet() {
        return targetAlphabet;
    }

    int getSourceIndex(int targetIndex) {
        return compressionArray[targetIndex];
    }

    public static class CompressionBuilder {
        private Alphabet sourceAlphabet;
        private Alphabet targetAlphabet;
        private Map<Character, Integer> sourceCharToIndex;
        private int[] compressionArray;

        private CompressionBuilder(Alphabet sourceAlphabet) {
            this.sourceAlphabet = sourceAlphabet;
        }

        public AlphabetCompression to(Alphabet targetAlphabet) {
            validateTargetAlphabet(targetAlphabet);
            this.targetAlphabet = targetAlphabet;
            return build();
        }

        private void validateTargetAlphabet(Alphabet targetAlphabet) {
            Objects.requireNonNull(targetAlphabet);
            requireSourceAlphabetContainTargetAlphabet(targetAlphabet);
        }

        private void requireSourceAlphabetContainTargetAlphabet(Alphabet targetAlphabet) {
            if (!sourceAlphabetContainsTargetAlphabet(targetAlphabet)) {
                throw new IllegalArgumentException("Target alphabet includes characters missing in source.");
            }
        }

        private boolean sourceAlphabetContainsTargetAlphabet(Alphabet targetAlphabet) {
            Set<Character> sourceAlphabetCharsSet = streamCharArrayBoxed(sourceAlphabet.getChars())
                    .collect(Collectors.toSet());
            return streamCharArrayBoxed(targetAlphabet.getChars())
                    .allMatch(sourceAlphabetCharsSet::contains);
        }

        private Stream<Character> streamCharArrayBoxed(char[] charArray) {
            return new String(charArray).chars()
                    .mapToObj(charIndex -> (char) charIndex);
        }

        private AlphabetCompression build() {
            calculateCompressionArray();
            return new AlphabetCompression(sourceAlphabet, targetAlphabet, compressionArray);
        }

        private void calculateCompressionArray() {
            sourceCharToIndex = valueToIndexMap(sourceAlphabet.getChars());
            compressionArray = streamCharArrayBoxed(targetAlphabet.getChars())
                    .mapToInt(this::findSourceIndex)
                    .toArray();
        }


        private Map<Character, Integer> valueToIndexMap(char[] chars) {
            return IntStream.range(0, chars.length)
                    .boxed()
                    .collect(Collectors.toMap(
                            i -> chars[i],
                            Function.identity()
                    ));
        }

        private int findSourceIndex(Character character) {
            return sourceCharToIndex.get(character);
        }
    }
}
