package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.services.interfaces.UserService;
import it.ldsoftware.primavera.util.SecuredUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Created by luca on 11/04/16.
 * This provides a fully fledged user detail service against the database
 * using the entities
 */
@Service
public class DatabaseUserDetailService implements UserDetailsService {

    private final UserService svc;

    @Autowired
    public DatabaseUserDetailService(UserService svc) {
        this.svc = svc;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = svc.findByUsername(username);
        if (user == null)
            throw new UsernameNotFoundException("");

        return SecuredUser.fromUser(user);
    }
}
