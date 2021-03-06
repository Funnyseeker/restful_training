package fun.trainings.rs.model.filtering;

import fun.trainings.rs.annotations.FilterFieldGetter;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Predicate;

/**
 * Класс для фильтрации потока объектов.
 * Принцип работы основан на создании предиката для фильтрации потока.
 *
 * @param <T> тип объектов в потоке
 */
public final class StreamFilter<T> extends Filter {
    /**
     * Предикат для фильтрации.
     */
    protected Predicate<T> predicate;

    /**
     * Создает предикат для данного фильтра.
     *
     * @return объект данного фильтра
     */
    public StreamFilter<T> buildPredicate() {
        predicate = user -> {
            for (Method method : user.getClass().getMethods()) {
                FilterFieldGetter ffg = method.getAnnotation(FilterFieldGetter.class);
                if (ffg != null && getFilterAttribute(ffg.value()) != null) {
                    try {
                        Object object = method.invoke(user);
//                        field.get(user)
                        if (object.equals(getFilterAttribute(ffg.value()))) {
                            return false;
                        }
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        return false;
                    }
                }
            }
            return true;
        };
        return this;
    }

    /**
     * Получение предиката, представляющего данный фильтр.
     *
     * @return предикат, представляющий данный фильтр.
     */
    public Predicate<T> getPredicate() {
        return predicate == null ? buildPredicate().getPredicate() : predicate;
    }
}
