package it.ldsoftware.commons.services;

import it.ldsoftware.commons.dto.people.UserDTO;
import it.ldsoftware.commons.entities.people.QUser;
import it.ldsoftware.commons.entities.people.User;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
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

    @Autowired
    DatabaseService svc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = svc.findOne(User.class, QUser.user.username.eq(username));
        if (user == null)
            throw new UsernameNotFoundException("");

        return new UserDTO(user);
    }
}
