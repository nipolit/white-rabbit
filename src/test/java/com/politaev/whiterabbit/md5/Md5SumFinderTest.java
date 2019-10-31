package com.politaev.whiterabbit.md5;

import org.fest.assertions.MapAssert;
import org.junit.Before;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.politaev.whiterabbit.md5.Md5SumFinder.createFinder;
import static org.fest.assertions.Assertions.assertThat;
import static org.fest.assertions.MapAssert.entry;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class Md5SumFinderTest {

    private Map<String, String> foundMd5Sums;

    @Before
    public void setUp() {
        foundMd5Sums = new HashMap<>();
    }

    private void acceptFoundItems(String md5Sum, String input) {
        foundMd5Sums.put(md5Sum, input);
    }

    @Test
    public void testFindPhrase() {
        String givenMd5Sum = "4cdc360161274f086833f20a276b0113";
        String phraseToFind = "spiral out keep going";
        Md5SumFinder finder = createTestFinder(givenMd5Sum);
        finder.check(phraseToFind);
        assertEquals(phraseToFind, foundMd5Sums.get(givenMd5Sum));
    }

    private Md5SumFinder createTestFinder(String... givenMd5Sums) {
        return createFinder()
                .searchingMd5Sums(givenMd5Sums)
                .performingOperationOnFoundItems(this::acceptFoundItems)
                .build();
    }

    @Test
    public void testNotFindPhrase() {
        String givenMd5Sum = "4cdc360161274f086833f20a276b0113";
        String someOtherPhrase = "reaching out to embrace the random";
        Md5SumFinder finder = createTestFinder(givenMd5Sum);
        finder.check(someOtherPhrase);
        assertFalse(foundMd5Sums.containsKey(givenMd5Sum));
    }

    @Test
    public void testFindPhraseUpperCaseMd5() {
        String givenMd5Sum = "C740E99E31762436A9C79FC38B79CA6F";
        String phraseToFind = "over thinking over analyzing";
        Md5SumFinder finder = createTestFinder(givenMd5Sum);
        finder.check(phraseToFind);
        assertEquals(phraseToFind, foundMd5Sums.get(givenMd5Sum.toLowerCase()));
    }

    @Test
    public void testFindMultiplePhrases() {
        String[] givenMd5Sums = new String[]{
                "13d466a23dbcea55e7deb9934e4e5256",
                "3e6c5745201e7f5e9561cd3d3b160f9d",
                "3d37a795f5e92427a831e9d4c7ce0552",
                "1acd3cd2d0d8d8cc6a86f27aa245ca91"};
        String[] phrasesToFind = new String[]{
                "and following our will and wind",
                "we may just go where no one's been",
                "we'll ride the spiral to the end",
                "and may just go where no one's been"};
        Md5SumFinder finder = createTestFinder(givenMd5Sums);
        Stream.of(phrasesToFind).sequential()
                .forEach(finder::check);
        MapAssert.Entry[] expectedEntries = IntStream.range(0, givenMd5Sums.length)
                .mapToObj(i -> entry(givenMd5Sums[i], phrasesToFind[i]))
                .toArray(MapAssert.Entry[]::new);
        assertThat(foundMd5Sums).includes(expectedEntries);
    }

    @Test(expected = NullPointerException.class)
    public void testNoGivenMd5Sums() {
        createFinder()
                .performingOperationOnFoundItems(this::acceptFoundItems)
                .build();
    }

    @Test(expected = NullPointerException.class)
    public void testNoConsumerFunction() {
        createFinder()
                .searchingMd5Sums("13d466a23dbcea55e7deb9934e4e5256", "3e6c5745201e7f5e9561cd3d3b160f9d")
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyGivenMd5Sums() {
        createFinder()
                .searchingMd5Sums()
                .performingOperationOnFoundItems(this::acceptFoundItems)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testWrongLengthGivenMd5Sums() {
        createFinder()
                .searchingMd5Sums("13d466a23dbcea55e7deb9934e4e")
                .performingOperationOnFoundItems(this::acceptFoundItems)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testIllegalSymbolsInGivenMd5Sums() {
        createFinder()
                .searchingMd5Sums("ILLEGALzILLEGALzILLEGALzILLEGALz")
                .performingOperationOnFoundItems(this::acceptFoundItems)
                .build();
    }
}
