package com.politaev.whiterabbit.anagram.finder;

import com.politaev.whiterabbit.anagram.AnagramTest;
import com.politaev.whiterabbit.counter.CharCount;
import org.junit.Test;

import static com.politaev.whiterabbit.anagram.finder.CharCountCombinationExtender.createExtender;

public class CharCountCombinationExtenderBuilderTest extends AnagramTest {

    @Test(expected = IllegalArgumentException.class)
    public void testTotalCharsLimitLowerThanThreshold() {
        createExtender(dictionary)
                .withResultTotalCharsNotLowerThan(6)
                .withResultTotalCharsNotHigherThan(5)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testCharCountLimitTotalCharsLowerThanThreshold() {
        String limitString = "youshallnotpass";
        CharCount resultCharCountLimit = charCounter.countChars(limitString);
        createExtender(dictionary)
                .withResultCharCountIncludedIn(resultCharCountLimit)
                .withResultTotalCharsNotLowerThan(resultCharCountLimit.totalChars() + 1)
                .build();
    }
}
