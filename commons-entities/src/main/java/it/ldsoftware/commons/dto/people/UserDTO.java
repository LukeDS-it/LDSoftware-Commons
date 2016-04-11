package it.ldsoftware.commons.dto.people;

import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.people.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         DTO that represents an user
 */
public class UserDTO extends BaseDTO<User> implements UserDetails {
    // TODO complete
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
