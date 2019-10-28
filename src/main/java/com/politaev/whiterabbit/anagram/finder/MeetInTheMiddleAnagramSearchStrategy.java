package com.politaev.whiterabbit.anagram.finder;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static java.util.Collections.unmodifiableList;

class MeetInTheMiddleAnagramSearchStrategy extends CompositeAnagramSearchStrategy {

    MeetInTheMiddleAnagramSearchStrategy(AnagramSearchContext context) {
        super(context);
    }

    @Override
    List<AnagramSearchStrategy> createAnagramSearchStrategies() {
        AnagramSearchContext context = getContext();
        List<AnagramSearchStrategy> necessaryStrategies = new ArrayList<>();
        if (anagramHasEvenLength()) {
            necessaryStrategies.add(new TwoCombinationsAnagramSearchStrategy(context));
        }
        necessaryStrategies.addAll(asList(
                new SingleWordAnagramSearchStrategy(context),
                new CombinationPlusWordAnagramSearchStrategy(context),
                new WordInTheMiddleAnagramSearchStrategy(context)
        ));
        return unmodifiableList(necessaryStrategies);
    }
}
