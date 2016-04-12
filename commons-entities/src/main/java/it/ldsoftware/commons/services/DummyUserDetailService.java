package it.ldsoftware.commons.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Created by luca on 11/04/16.
 * This class provides a simple user detail service, it is useful
 * to try the different login features such as user not found,
 * password expired, login or password incorrect, etc.
 */
public class DummyUserDetailService implements UserDetailsService {
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return null;
    }
}
