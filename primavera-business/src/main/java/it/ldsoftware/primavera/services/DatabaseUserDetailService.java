package it.ldsoftware.primavera.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by luca on 11/04/16.
 * This provides a fully fledged user detail service against the database
 * using the entities
 */
public class DatabaseUserDetailService implements UserDetailsService {

//    @Autowired
//    DatabaseService svc;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User user = svc.findOne(User.class, QUser.user.username.eq(username));
//        if (user == null)
//            throw new UsernameNotFoundException("");
//
//        return new UserDTO(user);
        return null;
    }
}
