package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.presentation.people.UserDTO;
import it.ldsoftware.primavera.util.SecuredUser;
import it.ldsoftware.primavera.util.UserUtil;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Created by luca on 11/04/16.
 * This class provides a simple user detail service, it is useful
 * to try the different login features such as user not found,
 * password expired, login or password incorrect, etc.
 */
public class DummyUserDetailService implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username.equals("nonexisting"))
            throw new UsernameNotFoundException("nonexisting");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        String password = encoder.encode(username);

        SecuredUser dto = SecuredUser.dummy(username, password);
        switch (username) {
            case "superadmin":
                dto.addAuthority(UserUtil.ROLE_SUPERADMIN);
            case "user":
                dto.enable();
                break;
            case "disabled":
                break;
            case "expired":
                break;
        }

        return dto;
    }

}
