package it.ldsoftware.commons.entities.base;

import it.ldsoftware.commons.util.PropertyType;

import javax.persistence.*;

import static javax.persistence.EnumType.STRING;

/**
 * Created by luca on 12/04/16.
 *
 * @author luca
 *         This class is used to save on the database
 *         some configurations that can be modified by the application users
 *         and don't belong to a configuration file.
 */
@Entity
@Table(name = "zz_properties")
public class AppProperty extends BaseEntity {

    private String key;
    private Long longVal;
    private Boolean boolVal;
    private Float floatVal;
    private Integer intVal;

    @Column(length = 4096)
    private String jsonVal;

    @Column(length = 512)
    private String stringVal;

    @Enumerated(STRING)
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
