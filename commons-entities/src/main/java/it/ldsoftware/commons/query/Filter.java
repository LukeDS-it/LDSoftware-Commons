package it.ldsoftware.commons.query;

import java.io.Serializable;

/**
 * Created by luca on 11/04/16.
 * Simple filter class
 */
public class Filter implements Serializable {
    String property;
    FilterOperator operator;
    boolean negative;
    Object value;

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
