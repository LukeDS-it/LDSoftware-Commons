package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.model.people.QUser;
import it.ldsoftware.primavera.presentation.people.UserDTO;
import it.ldsoftware.primavera.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by luca on 11/04/16.
 * This provides a fully fledged user detail service against the database
 * using the entities
 */
public class DatabaseUserDetailService implements UserDetailsService {

    private final UserService svc;

    @Autowired
    public DatabaseUserDetailService(UserService svc) {
        this.svc = svc;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = svc.findOne(QUser.user.username.eq(username));
        if (user == null)
            throw new UsernameNotFoundException("");

//        return new UserDTO(user);
        return null;
    }
}
