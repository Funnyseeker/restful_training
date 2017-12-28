package fun.trainings.rs.model.filtering;

import fun.trainings.rs.model.impl.EntityUserImpl;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * Класс для фильтрации HQL запросов
 * Принцип работы основан на создании "criteria" запроса.
 *
 * @param <T> "entity" класс
 */
public final class HQLFilter<T> extends Filter {

    private Class<T> objectsClass;
    private CriteriaBuilder criteriaBuilder;
    /**
     * Запрос на выборку данных
     */
    private CriteriaQuery criteriaQuery;

    /**
     * Эталоны для обновления данных
     */
    private Map<String, Object> setStandards = new HashMap<>();
    /**
     * Запрос на обновление данных
     */
    private CriteriaUpdate criteriaUpdate;

    /**
     * Запрос на удаление данных
     */
    private CriteriaDelete criteriaDelete;


    public HQLFilter(Class<T> objectsClass, CriteriaBuilder criteriaBuilder) {
        this.objectsClass = objectsClass;
        this.criteriaBuilder = criteriaBuilder;
    }

    /**
     * Создание запроса на выборку из базы данных соответствующих заданным фильтрам.
     *
     * @return объект фильтра
     */
    private HQLFilter<T> buildCriteriaQuery() {
        Predicate[] predicates = new Predicate[filterStandards.size()];
        criteriaQuery = criteriaBuilder.createQuery(objectsClass);
        Root root = criteriaQuery.from(objectsClass);

        int predicateIndex = 0;
        for (Field field : EntityUserImpl.class.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && getFilterAttribute(column.name()) != null) {
                predicates[predicateIndex] = (criteriaBuilder
                        .equal(root.get(field.getName()), getFilterAttribute(column.name())));
                predicateIndex++;
            }
        }
        criteriaQuery.where(predicates);
        return this;
    }

    /**
     * Создание запроса на обновление в базе данных соответствующих заданным фильтрам.
     *
     * @return объект фильтра
     */
    private HQLFilter<T> buildCriteriaUpdate() {
        Predicate[] predicates = new Predicate[filterStandards.size()];
        criteriaUpdate = criteriaBuilder.createCriteriaUpdate(objectsClass);
        Root root = criteriaUpdate.from(objectsClass);

        int predicateIndex = 0;
        for (Field field : EntityUserImpl.class.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            //если поле указано в фильтрах создает предикат
            if (column != null && getFilterAttribute(column.name()) != null) {
                predicates[predicateIndex] = criteriaBuilder
                        .equal(root.get(field.getName()), getFilterAttribute(column.name()));
            }
            //если поле должно быть изменено, задает set clause
            if (column != null && getSetAttribute(column.name()) != null) {
                criteriaUpdate.set(field.getName(), getSetAttribute(column.name()));
            }
        }
        criteriaUpdate.where(predicates);
        return this;
    }

    /**
     * Создание запроса на удаление в базе данных соответствующих заданным фильтрам.
     *
     * @return объект фильтра
     */
    private HQLFilter<T> buildCriteriaDelete() {
        Predicate[] predicates = new Predicate[filterStandards.size()];
        criteriaDelete = criteriaBuilder.createCriteriaDelete(objectsClass);
        Root root = criteriaDelete.from(objectsClass);

        int predicateIndex = 0;
        for (Field field : EntityUserImpl.class.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && getFilterAttribute(column.name()) != null) {
                predicates[predicateIndex] = (criteriaBuilder
                        .equal(root.get(field.getName()), getFilterAttribute(column.name())));
            }
        }
        criteriaDelete.where(predicates);
        return this;
    }

    /**
     * Получение запроса на выборку данных.
     *
     * @return запрос в базу данных на выборку
     */
    public CriteriaQuery<T> getCriteriaQuery() {
        return criteriaQuery == null ? buildCriteriaQuery().getCriteriaQuery() : criteriaQuery;
    }

    /**
     * Получение запроса на обновление данных.
     *
     * @return запрос в базу данных на обновление
     */
    public CriteriaUpdate<T> getCriteriaUpdate() {
        return criteriaUpdate == null ? buildCriteriaUpdate().getCriteriaUpdate() : criteriaUpdate;
    }

    /**
     * Получение запроса на удаление данных.
     *
     * @return запрос в базу данных на удаление
     */
    public CriteriaDelete<T> getCriteriaDelete() {
        return criteriaDelete == null ? buildCriteriaDelete().getCriteriaDelete() : criteriaDelete;
    }

    /**
     * Задание эталона для обновления и его ключа.
     *
     * @param standardKey ключ эталона для обновления
     * @param value       значение эталона для обновления
     */
    public void addSetAttribute(String standardKey, Object value) {
        setStandards.put(standardKey, value);
    }

    /**
     * Полученине эталона для обновления по ключу.
     *
     * @param standartKey ключ эталона для обновления
     *
     * @return значение эталона для обновления
     */
    private Object getSetAttribute(String standartKey) {
        return setStandards.get(standartKey);
    }
}
