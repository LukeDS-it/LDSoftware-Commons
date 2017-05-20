package it.ldsoftware.primavera.query;

import java.io.Serializable;

/**
 * Created by luca on 11/04/16.
 * Simple filter class. Used in combination with the {@link PredicateFactory}
 * to create custom queries.<br />

 * Guidelines to filters creation:
 * <ul>
 *     <li>
 *         {@link String} properties will be interpreted as
 *         full matching, unless you add the % wildcard, to
 *         specify if the string has to start with, end with
 *         or contains the string passed as filter value.
 *     </li>
 *     <li>
 *         Numeric types and dates will be confronted as equals.<br/>
 *         Additionally it is possible to add ranges to this kind of
 *         data, appending "from" and/or "to" to the field name. The
 *         filtering supports both {@link java.util.Calendar} and
 *         {@link java.util.Date}.
 *     </li>
 *     <li>
 *         Entity fields will be interpreted based on the information in them
 *         contained: if the ID field is valorized, then the query will be
 *         by ID. If not, the predicate factory will create a query "by example"
 *         based on the entity passed as value. The rules for the fields in the
 *         example object are the same as above.
 *     </li>
 *     <li>
 *         Providing as "key" the name of a field of type
 *         {@link java.util.Collection} will create a query that
 *         finds all the entities which have in the specified field
 *         collection an object similar (or equal, depending on the parameters
 *         provided, see above on Entity) to the one provided.
 *     </li>
 * </ul>
 */
public class Filter implements Serializable {
    private String property;
    private FilterOperator operator;
    private boolean negative;
    private Object value;

    public Filter(String property, Object value, boolean negative, FilterOperator operator) {
        this.property = property;
        this.operator = operator;
        this.value = value;
        this.negative = negative;
    }

    public Filter withProperty(String property) {
        setProperty(property);
        return this;
    }

    public Filter withValue(Object value) {
        setValue(value);
        return this;
    }

    public Filter withNegative(boolean negative) {
        setNegative(negative);
        return this;
    }

    public Filter withFilterOperator(FilterOperator filterOperator) {
        setOperator(filterOperator);
        return this;
    }

    @Override
    public boolean equals(Object arg0) {
        if (arg0 instanceof Filter) {
            Filter other = (Filter) arg0;
            return other.negative == negative && other.property.equals(property) && other.operator.equals(operator)
                    && other.value.equals(value);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return super.hashCode() + (negative ? 1 : 2) + property.hashCode() + value.hashCode() + operator.hashCode();
    }

    @Override
    public String toString() {
        return operator.toString() + (negative ? " not " : " ") + property + " = " + value.toString();
    }

    public String getProperty() {
        return property;
    }

    public void setProperty(String property) {
        this.property = property;
    }

    public FilterOperator getOperator() {
        return operator;
    }

    public void setOperator(FilterOperator operator) {
        this.operator = operator;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public boolean isNegative() {
        return negative;
    }

    public void setNegative(boolean negative) {
        this.negative = negative;
    }
}
