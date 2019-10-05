package com.politaev.whiterabbit.reader.filter;

import java.util.function.Predicate;

public class NotEmptyFilter implements Predicate<String> {
    @Override
    public boolean test(String s) {
        return s != null && !s.isEmpty();
    }
}
