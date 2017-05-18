package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.presentation.people.UserDTO;
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
        UserDTO dto = new UserDTO();

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        dto.setUsername(username);
        dto.setPassword(encoder.encode(username));

        switch (username) {
            case "superadmin":
//                dto.addAuthority(UserUtil.ROLE_SUPERADMIN);
                enableUser(dto);
                break;
            case "user":
                enableUser(dto);
                break;
            case "disabled":
                break;
            case "expired":
                break;
        }

//        return dto;
        return null;
    }

    private void enableUser(UserDTO dto) {
        dto.setEnabled(true);
    }
}
