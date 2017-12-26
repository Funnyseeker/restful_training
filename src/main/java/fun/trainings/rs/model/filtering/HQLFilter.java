package fun.trainings.rs.model.filtering;

import fun.trainings.rs.model.HibernateBindKeys;
import fun.trainings.rs.model.impl.EntityUserImpl;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

/**
 * Класс для фильтрации HQL запросов
 * Принцип работы основан на создании "criteria" запроса.
 *
 * @param <T> "entity" класс
 */
public final class HQLFilter<T> extends Filter {

    private Class<T> objectsClass;
    private CriteriaBuilder criteriaBuilder;
    private CriteriaQuery criteriaQuery;
    private CriteriaUpdate criteriaUpdate;
    private CriteriaDelete criteriaDelete;


    public HQLFilter(Class<T> objectsClass, CriteriaBuilder criteriaBuilder) {
        this.objectsClass = objectsClass;
        this.criteriaBuilder = criteriaBuilder;
    }

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
            }
        }
        criteriaQuery.where(predicates);
        return this;
    }

    private HQLFilter<T> buildCriteriaUpdate() {
        criteriaUpdate = criteriaBuilder.createCriteriaUpdate(objectsClass);
        Root root = criteriaUpdate.from(objectsClass);

        for (Field field : EntityUserImpl.class.getDeclaredFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null && getFilterAttribute(column.name()) != null) {
                // id поле не должно изменяться, соответсвенно, если оно присутсвует,
                // то его нужно использовать для задания конкретного объекта для обновления
                if (column.name().equals(HibernateBindKeys.USER_ID_COL)) {
                    criteriaUpdate
                            .where(criteriaBuilder.equal(root.get(field.getName()), getFilterAttribute(column.name())));
                    continue;
                } else {
                    criteriaUpdate.set(field.getName(), getFilterAttribute(column.name()));
                }
            }
        }
        return this;
    }

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

    public CriteriaQuery<T> getCriteriaQuery() {
        return criteriaQuery == null ? buildCriteriaQuery().getCriteriaQuery() : criteriaQuery;
    }

    public CriteriaUpdate<T> getCriteriaUpdate() {
        return criteriaUpdate == null ? buildCriteriaUpdate().getCriteriaUpdate() : criteriaUpdate;
    }

    public CriteriaDelete<T> getCriteriaDelete(){
        return criteriaDelete == null ? buildCriteriaDelete().getCriteriaDelete() : criteriaDelete;
    }
}
