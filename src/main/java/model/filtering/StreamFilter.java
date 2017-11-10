package model.filtering;

import java.util.function.Predicate;

public abstract class StreamFilter<T> extends Filter {
    Predicate<T> predicate;

    public abstract StreamFilter<T> buildPredicate();

    public Predicate<T> getPredicate() {
        return predicate == null ? buildPredicate().getPredicate() : predicate;
    }
}
