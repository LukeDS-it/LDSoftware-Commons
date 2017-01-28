package it.ldsoftware.rest.payload.base;

import java.io.Serializable;

/**
 * This is the class that specifies wha a payload is.
 * This is the most basic response that a REST web service
 * will return.
 * @author luca
 */
public class BasePayload implements Serializable {

    private long id;
    private long version;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        BasePayload that = (BasePayload) o;

        return id == that.id;
    }

    @Override
    public int hashCode() {
        return (int) (id ^ (id >>> 32));
    }
}
