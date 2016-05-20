package it.ldsoftware.primavera.query;


import com.mysema.query.jpa.JPASubQuery;
import com.mysema.query.types.expr.BooleanExpression;
import com.mysema.query.types.path.CollectionPath;
import com.mysema.query.types.path.PathBuilder;
import com.mysema.query.types.path.StringPath;
import it.ldsoftware.primavera.entities.base.BaseEntity;
import org.apache.log4j.Logger;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.*;

import static it.ldsoftware.primavera.query.FilterOperator.AND;
import static it.ldsoftware.primavera.util.CalendarUtil.endOfDay;
import static it.ldsoftware.primavera.util.CalendarUtil.fromDate;
import static it.ldsoftware.primavera.util.ReflectionUtil.getPropertyFromMethod;

/**
 * This class serves as a dynamic QueryDSL predicate factory.
 * Using reflection and dynamic paths, this class can easily create predicates based on a filter list
 * or on an example entity.
 *
 * @author luca
 */
public class PredicateFactory {
    private static final Logger logger = Logger.getLogger(PredicateFactory.class);

    /**
     * This function returns the boolean expression that corresponds to given entity and filters.
     *
     * @param eClass  entity class used to build the dynamic path
     * @param filters collection of filters
     * @param <E>     parameter for type-safe handling
     * @return the predicate in the form of a {@link BooleanExpression}
     * @see Filter How to create filtering parameters
     */
    public static <E extends BaseEntity> BooleanExpression createPredicate(Class<E> eClass, Collection<Filter> filters) {
        PathBuilder<E> pb = getPathBuilder(eClass);
        BooleanExpression partial = pb.isNotNull();

        Set<String> betweenParsed = new HashSet<>();

        for (Filter filter : filters) {
            try {
                Field f = eClass.getDeclaredField(filter.getProperty());
                partial = getBasicExpression(pb, partial, filter, f);
            } catch (NoSuchFieldException ignored) {
                String fname = filter.getProperty();
                if (fname.toLowerCase().endsWith("from") || fname.toLowerCase().endsWith("to")) {
                    if (!betweenParsed.contains(getBetweenFieldName(fname)))
                        partial = handleBetween(pb, partial, filter, filters);
                    betweenParsed.add(getBetweenFieldName(fname));

                } else {
                    // TODO try to get a custom filter factory first to see if the field can be inferred
                    logger.debug("The field " + filter.getProperty()
                            + " does not exist in the entity. Remember to add any custom query after the call.");
                }
            }
        }

        return partial;
    }

    /**
     * Creates a predicate based on the entity passed as an example
     *
     * @param entityClass the class of the entity we are querying
     * @param entity the entity to take as an example
     * @param <E> parameter for type-safe handling
     * @return the predicate in the form of a {@link BooleanExpression}
     */
    public static <E extends BaseEntity> BooleanExpression createPredicate(Class<E> entityClass, E entity) {
        return createPredicate(entityClass, getFiltersByEntity(entityClass, entity));
    }

    /**
     * Creates a list of {@link Filter} based on the entity given as example
     * @param entityClass the class of the entity we are querying
     * @param entity the entity to take as an example
     * @param <E> parameter for type-safe handling
     * @return the list of {@link Filter} that describe the entity
     */
    public static <E extends BaseEntity> List<Filter> getFiltersByEntity(Class<E> entityClass, E entity) {
        List<Filter> filters = new ArrayList<>();
        Arrays.stream(entityClass.getDeclaredMethods()).filter(PredicateFactory::isCandidate)
                .map(m -> filterFromMethod(m, entity)).filter(f -> f != null).forEach(filters::add);
        return filters;
    }

    private static <E extends BaseEntity> PathBuilder<E> getPathBuilder(Class<E> eClass) {
        String entityName = eClass.getSimpleName();
        entityName = entityName.substring(0, 1).toLowerCase() + entityName.substring(1);
        if (entityName.equals("group")) entityName += "1";
        return new PathBuilder<>(eClass, entityName);
    }

    private static <E extends BaseEntity> BooleanExpression getBasicExpression(PathBuilder<E> pb, BooleanExpression partial, Filter filter, Field f) {
        if (Collection.class.isAssignableFrom(f.getType())) {
            partial = handleCollection(pb, partial, filter);
        } else if (String.class.isAssignableFrom(f.getType())) {
            partial = handleString(pb, partial, filter);
        } else if (BaseEntity.class.isAssignableFrom(f.getType())) {
            partial = handleEntity(pb, partial, filter, f);
        } else {
            partial = handleOther(pb, partial, filter);
        }
        return partial;
    }
    private static boolean isCandidate(Method method) {
        return (method.getName().startsWith("get") || method.getName().startsWith("is"))
                && !(method.getName().equals("getId") || method.getName().equals("getVersion")
                || method.getName().equals("getSysFields"))
                && !(Collection.class.isAssignableFrom(method.getReturnType()));
    }

    private static <E extends BaseEntity> Filter filterFromMethod(Method method, E entity) {
        String property = getPropertyFromMethod(method.getName());
        Object value;
        try {
            value = method.invoke(entity);
        } catch (Exception ignored) {
            // This does never happen because the methods always exist
            return null;
        }
        if (value == null)
            return null;
        return new Filter(property, value, false, AND);
    }

    @SuppressWarnings("unchecked")
    private static <E extends BaseEntity> BooleanExpression handleCollection(PathBuilder<E> pb, BooleanExpression base, Filter filter) {
        // TODO if the object is a collection the query will state "contains all of the elements in the collection"
        Object o = filter.getValue();
        Class<E> eClass = (Class<E>) o.getClass();
        CollectionPath path = pb.getCollection(filter.getProperty(), eClass);

        PathBuilder subPb = getPathBuilder(eClass);

        BooleanExpression subClause = subPb.isNotNull();
        subClause = subClause.and(createPredicate(eClass, (E)o));

        JPASubQuery q = new JPASubQuery().from(subPb).where(subClause);
        switch (filter.getOperator()) {
            case AND:
                base = base.and(findNegation(path.any().in(q.list()), filter));
                break;
            case OR:
                base = base.or(findNegation(path.any().in(q.list()), filter));
                break;
        }
        return base;
    }

    private static BooleanExpression handleString(PathBuilder<?> pb, BooleanExpression base, Filter filter) {
        String s = filter.getValue().toString();
        boolean startsWith = s.endsWith("%");
        boolean endsWith = s.startsWith("%");
        StringPath sp = pb.getString(filter.getProperty());
        if (startsWith) {
            s = s.substring(0, s.length() - 1);
        }
        if (endsWith) {
            s = s.substring(1);
        }

        switch (filter.getOperator()) {
            case AND:
                if (startsWith && endsWith) {
                    base = base.and(findNegation(sp.containsIgnoreCase(s), filter));
                } else if (startsWith) {
                    base = base.and(findNegation(sp.startsWithIgnoreCase(s), filter));
                } else if (endsWith) {
                    base = base.and(findNegation(sp.endsWithIgnoreCase(s), filter));
                } else {
                    base = base.and(findNegation(sp.equalsIgnoreCase(s), filter));
                }
                break;
            case OR:
                if (startsWith && endsWith) {
                    base = base.or(findNegation(sp.containsIgnoreCase(s), filter));
                } else if (startsWith) {
                    base = base.or(findNegation(sp.startsWithIgnoreCase(s), filter));
                } else if (endsWith) {
                    base = base.or(findNegation(sp.endsWithIgnoreCase(s), filter));
                } else {
                    base = base.or(findNegation(sp.equalsIgnoreCase(s), filter));
                }
                break;
        }
        return base;
    }

    private static <E extends BaseEntity> BooleanExpression handleEntity(PathBuilder<?> pb, BooleanExpression base,
                                                                         Filter filter, Field field) {
        Object o = filter.getValue();
        if (o instanceof BaseEntity) {
            if (o.getClass().equals(field.getType())) {
                if (((BaseEntity) o).getId() != 0) {
                    base = handleOther(pb, base, filter);
                } else {
                    @SuppressWarnings("unchecked")
                    List<Filter> subFilters = getFiltersByEntity((Class<E>) o.getClass(), (E) o);

                    PathBuilder nested = pb.get(filter.getProperty());
                    BooleanExpression partial = nested.isNotNull();
                    for (Filter sf: subFilters) {
                        try {
                            partial = getBasicExpression(nested, partial, sf, o.getClass().getDeclaredField(sf.getProperty()));
                        } catch (NoSuchFieldException e) {
                            // Should not be thrown as we are building filters from example
                            e.printStackTrace();
                        }
                    }

                    switch (filter.getOperator()) {
                        case AND:
                            base = base.and(findNegation(partial, filter));
                            break;
                        case OR:
                            base = base.or(findNegation(partial, filter));
                            break;
                    }
                }
            } else {
                logger.error("An invalid value was passed to the field " + filter.getProperty() + ": expected "
                        + field.getType().getName() + ", got " + filter.getValue().getClass().getName());
            }
        }
        return base;
    }

    private static BooleanExpression handleOther(PathBuilder<?> pb, BooleanExpression base, Filter filter) {
        switch (filter.getOperator()) {
            case AND:
                return base.and(findNegation(pb.get(filter.getProperty()).eq(filter.getValue()), filter));
            case OR:
                return base.or(findNegation(pb.get(filter.getProperty()).eq(filter.getValue()), filter));
        }
        return base;
    }

    private static BooleanExpression handleBetween(PathBuilder<?> pb, BooleanExpression base, Filter filter,
                                                   Collection<Filter> filters) {

        String fromToField = filter.getProperty();
        final String fieldName = getBetweenFieldName(fromToField);
        Object fromValue = null;
        Object toValue = null;

        if (fromToField.toLowerCase().endsWith("from")) {
            fromValue = filter.getValue();
            Optional<Filter> toFilter = filters.stream().filter(f -> f.getProperty().equalsIgnoreCase(fieldName + "to"))
                    .findFirst();

            if (toFilter.isPresent())
                toValue = toFilter.get().getValue();
        } else {
            toValue = filter.getValue();

            Optional<Filter> fromFilter = filters.stream()
                    .filter(f -> f.getProperty().equalsIgnoreCase(fieldName + "from")).findFirst();

            if (fromFilter.isPresent())
                fromValue = fromFilter.get().getValue();
        }

        Object fromToValue = (fromValue != null ? fromValue : toValue);

        assert fromToValue != null;
        if (fromToValue.getClass().isAssignableFrom(Calendar.class)
                || fromToValue.getClass().isAssignableFrom(Date.class)) {
            Calendar f = ensureIsCalendar(fromValue);
            Calendar t = endOfDay(ensureIsCalendar(toValue));
            switch (filter.getOperator()) {
                case AND:
                    return base.and(findNegation(pb.getDate(fieldName, Calendar.class).between(f, t), filter));
                case OR:
                    return base.or(findNegation(pb.getDate(fieldName, Calendar.class).between(f, t), filter));
            }
        } else {
            Comparable<?> f = (Comparable<?>) fromValue;
            Comparable<?> t = (Comparable<?>) toValue;
            switch (filter.getOperator()) {
                case AND:
                    return base.and(findNegation(pb.getComparable(fieldName, Comparable.class).between(f, t), filter));
                case OR:
                    return base.or(findNegation(pb.getComparable(fieldName, Comparable.class).between(f, t), filter));
            }
        }

        return base;
    }

    private static Calendar ensureIsCalendar(Object object) {
        if (object instanceof Date)
            return fromDate((Date) object);
        return (Calendar) object;
    }

    private static String getBetweenFieldName(String field) {
        if (field.toLowerCase().endsWith("from")) {
            return field.substring(0, field.length() - "from".length());
        } else if (field.toLowerCase().endsWith("to")) {
            return field.substring(0, field.length() - "to".length());
        } else {
            return field;
        }
    }

    private static BooleanExpression findNegation(BooleanExpression expr, Filter filter) {
        if (filter.isNegative())
            return expr.not();
        return expr;
    }
}
