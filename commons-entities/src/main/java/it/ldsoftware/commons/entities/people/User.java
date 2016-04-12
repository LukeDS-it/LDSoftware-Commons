package it.ldsoftware.commons.entities.people;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         An User is a @{link Person} that can log in to the program
 */
@Entity
@Table(name = "sw_user")
public class User extends Person {

    private String username;

    private String password;

    @Transient
    private String confirmPassword;

    @Override
    public String toString() {
        return super.toString() + ", " + getUsername();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}
