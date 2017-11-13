package model.filtering;

import annotation.FilterFieldGetter;

import java.lang.reflect.Field;
import java.util.function.Predicate;

public class StreamFilter<T> extends Filter {
    protected Predicate<T> predicate;

    public StreamFilter<T> buildPredicate() {
        predicate = user -> {
            for (Field field : user.getClass().getFields()) {
                FilterFieldGetter ffg = field.getAnnotation(FilterFieldGetter.class);
                if (ffg != null && getFilterAttribute(ffg.value()) != null) {
                    try {
                        if (field.get(user) != getFilterAttribute(ffg.value())) {
                            return false;
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return true;
        };
        return this;
    }

    public Predicate<T> getPredicate() {
        return predicate == null ? buildPredicate().getPredicate() : predicate;
    }
}
