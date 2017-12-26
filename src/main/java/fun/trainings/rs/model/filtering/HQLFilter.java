package fun.trainings.rs.model.filtering;

import fun.trainings.rs.model.impl.EntityUserImpl;

import javax.persistence.Column;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.lang.reflect.Field;

public class HQLFilter<T> extends Filter {

    protected Class<T> objectsClass;
    protected CriteriaBuilder criteriaBuilder;
    protected CriteriaQuery criteriaQuery;

    public HQLFilter(Class<T> objectsClass, CriteriaBuilder criteriaBuilder) {
        this.objectsClass = objectsClass;
        this.criteriaBuilder = criteriaBuilder;
    }

    public HQLFilter buildCriteriaQuery() {
        Predicate[] predicates = new Predicate[filterStandards.size()];
        criteriaQuery = criteriaBuilder.createQuery(objectsClass);
        Root root = criteriaQuery.from(objectsClass);

        int clauseIndex = 0;
        for (Field field : new EntityUserImpl().getClass().getFields()) {
            Column column = field.getAnnotation(Column.class);
            if (column != null) {
                predicates[clauseIndex] = (criteriaBuilder
                        .equal(root.get(column.name()), getFilterAttribute(column.name())));
            }
        }
        criteriaQuery.where(predicates);
        return this;
    }

    public CriteriaQuery<T> getCriteriaQuery() {
        return criteriaQuery == null ? buildCriteriaQuery().getCriteriaQuery() : criteriaQuery;
    }
}
