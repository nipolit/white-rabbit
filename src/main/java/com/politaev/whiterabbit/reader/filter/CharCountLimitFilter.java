package com.politaev.whiterabbit.reader.filter;

import com.politaev.whiterabbit.counter.CharCount;
import com.politaev.whiterabbit.counter.CharCounter;

import java.util.function.Predicate;

public class CharCountLimitFilter implements Predicate<String> {
    private final CharCount limit;
    private final CharCounter charCounter;

    public CharCountLimitFilter(CharCounter charCounter, String limitString) {
        this.limit = charCounter.countChars(limitString);
        this.charCounter = charCounter;
    }

    @Override
    public boolean test(String s) {
        CharCount charCount = charCounter.countChars(s);
        return limit.includes(charCount);
    }
}
