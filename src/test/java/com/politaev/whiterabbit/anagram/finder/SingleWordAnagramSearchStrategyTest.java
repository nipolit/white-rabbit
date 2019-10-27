package com.politaev.whiterabbit.anagram.finder;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class SingleWordAnagramSearchStrategyTest extends AnagramSearchStrategyTest {

    @Override
    AnagramSearchStrategy createSearchStrategy() {
        return new SingleWordAnagramSearchStrategy(context);
    }

    @GivenPhrase("aabb")
    @Test
    public void testSearch() {
        assertThat(foundAnagrams).containsOnly(
                charCountCombinationOf("aabb")
        );
    }

    @GivenPhrase("aaabbb")
    @Test
    public void testSearchNoResult() {
        assertThat(foundAnagrams).isEmpty();
    }
}
