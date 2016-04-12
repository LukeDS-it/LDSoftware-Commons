package it.ldsoftware.commons.dto.people;

import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.people.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Stream;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         DTO that represents an user
 */
public class UserDTO extends BaseDTO<User> implements UserDetails {

    private String username, password;
    private Set<GrantedAuthority> authorities = new HashSet<>();
    private boolean enabled;

    public UserDTO(User entity, Locale locale) {
        this(entity);
    }

    public UserDTO(User entity) {
        super(entity);
        setUsername(entity.getUsername());
        setPassword(entity.getPassword());
        setEnabled(entity.isEnabled());
        parseAuthorities(entity);
    }

    private void parseAuthorities(User entity) {
        Stream.concat(entity.getGroups()
                        .stream()
                        .flatMap(group -> group.getGroupRoles().stream())
                        .flatMap(groupRole -> groupRole.getActualRoles().stream()),
                entity.getUserRoles()
                        .stream()
                        .flatMap(userRole -> userRole.getActualRoles().stream()))
                .map(SimpleGrantedAuthority::new)
                .forEach(authorities::add);
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
