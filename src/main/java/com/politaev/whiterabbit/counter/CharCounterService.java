package com.politaev.whiterabbit.counter;

public class CharCounterService {
    public CharCount countChars(String string) {
        LatinCharCounter latinCharCounter = new LatinCharCounter(string);
        return latinCharCounter.countChars();
    }
}
