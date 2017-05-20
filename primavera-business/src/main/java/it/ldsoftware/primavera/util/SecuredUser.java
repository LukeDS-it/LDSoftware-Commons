package it.ldsoftware.primavera.util;

import it.ldsoftware.primavera.model.people.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;


/**
 * This is the implementation of the {@link UserDetails} that serves
 * as the authenticated user in the whole application.
 *
 * @author Luca Di Stefano
 */
public class SecuredUser implements UserDetails {

    private boolean enabled;
    private String username, password;
    private List<GrantedAuthority> authorities = new ArrayList<>();

    private SecuredUser(User user) {
        authorities = Stream.concat(user.getGroups()
                        .stream()
                        .flatMap(group -> group.getGroupRoles().stream())
                        .flatMap(groupRole -> groupRole.getActualRoles().stream()),
                user.getUserRoles()
                        .stream()
                        .flatMap(userRole -> userRole.getActualRoles().stream()))
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());

        enabled = user.isEnabled();
        username = user.getUsername();
        password = user.getPassword();
    }

    public static UserDetails fromUser(User user) {
        return new SecuredUser(user);
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
        return enabled;
    }
}
