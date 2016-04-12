package it.ldsoftware.commons.entities.base;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.io.Serializable;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by luca on 11/04/16.
 * This is the base entity from which every persisted class will inherit.
 * It features only the version field.
 * <p>
 * Please refer to the documentation to see which class fits your implementation best.
 */
@MappedSuperclass
public abstract class BaseEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private long id;

    @Version
    private long version;


    public BaseEntity withId(long id) {
        this.id = id;
        return this;
    }

    @Override
    public boolean equals(Object obj) {
        return obj != null && getClass().equals(obj.getClass()) && id == ((BaseEntity) obj).getId();

    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + ": " + getId();
    }

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
}
