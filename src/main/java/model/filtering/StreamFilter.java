package model.filtering;

import annotation.FilterFieldGetter;

import java.lang.reflect.Field;
import java.util.function.Predicate;

/**
 * Класс для фильтрации потока объектов.
 * Принцип работы основан на создании предиката для фильтрации потока.
 *
 * @param <T> тип объектов в потоке
 */
public class StreamFilter<T> extends Filter {
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
            for (Field field : user.getClass().getFields()) {
                FilterFieldGetter ffg = field.getAnnotation(FilterFieldGetter.class);
                if (ffg != null && getFilterAttribute(ffg.value()) != null) {
                    try {
                        if (!compareObjects(field.get(user), getFilterAttribute(ffg.value()))) {
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

    /**
     * Получение предиката, представляющего данный фильтр.
     *
     * @return предикат, представляющий данный фильтр.
     */
    public Predicate<T> getPredicate() {
        return predicate == null ? buildPredicate().getPredicate() : predicate;
    }

    /**
     * Сравнивает фильтруемое поле с полем в объекте.
     * Для строковых объектов использует {@link Object#equals(Object)}
     *
     * @param object1 первый объект для сравнения
     * @param object2 второй объект для сравнения
     *
     * @return true если объекты равны, иначе else
     */
    private boolean compareObjects(Object object1, Object object2) {
        if (object1 instanceof String && object2 instanceof String) {
            return object1.equals(object2);
        }
        return object1 == object2;
    }
}
