package com.politaev.whiterabbit.counter;

public class CharCounterService {
    public CharCount countChars(String string) {
        return CharCount.countLatinChars(string);
    }
}
