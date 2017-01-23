package it.ldsoftware.rest.payload.base;

import it.ldsoftware.rest.payload.util.PropertyType;

/**
 * Payload that specifies an appliation property
 */
public class PropertyPayload {

    private String key;
    private Long longVal;
    private Boolean boolVal;
    private Float floatVal;
    private Integer intVal;
    private String jsonVal;
    private String stringVal;
    private PropertyType type;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Long getLongVal() {
        return longVal;
    }

    public void setLongVal(Long longVal) {
        this.longVal = longVal;
    }

    public Boolean getBoolVal() {
        return boolVal;
    }

    public void setBoolVal(Boolean boolVal) {
        this.boolVal = boolVal;
    }

    public Float getFloatVal() {
        return floatVal;
    }

    public void setFloatVal(Float floatVal) {
        this.floatVal = floatVal;
    }

    public Integer getIntVal() {
        return intVal;
    }

    public void setIntVal(Integer intVal) {
        this.intVal = intVal;
    }

    public String getJsonVal() {
        return jsonVal;
    }

    public void setJsonVal(String jsonVal) {
        this.jsonVal = jsonVal;
    }

    public String getStringVal() {
        return stringVal;
    }

    public void setStringVal(String stringVal) {
        this.stringVal = stringVal;
    }

    public PropertyType getType() {
        return type;
    }

    public void setType(PropertyType type) {
        this.type = type;
    }
}
