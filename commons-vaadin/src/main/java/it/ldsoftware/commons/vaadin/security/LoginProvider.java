package it.ldsoftware.commons.vaadin.security;

import it.ldsoftware.commons.dto.people.UserDTO;
import it.ldsoftware.commons.entities.people.QUser;
import it.ldsoftware.commons.entities.people.User;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

/**
 * Created by luca on 02/05/16.
 * This service is used to create authentications from the OAuth response.
 * <p>
 * If the user exists, it uses the existing user, if not first tries to create it
 */
@Service
public class LoginProvider {

    @Autowired
    private DatabaseService svc;

    public Authentication getAuthentication(GoogleResponse googleResponse) {
        QUser qUser = QUser.user;
        User u = svc.findOne(User.class, qUser.primaryEmail.eq(googleResponse.getPrincipalMail())
                .or(qUser.primaryEmail.eq(googleResponse.getPrincipalMail())));

        if (u == null) {
            u = new User();
            u.setEnabled(true);
            u.setPrimaryEmail(googleResponse.getPrincipalMail());
            u.setName(googleResponse.getName().getGivenName());
            u.setSurname(googleResponse.getName().getFamilyName());
            u.setUsername(googleResponse.getPrincipalMail());
        }

        UserDTO userDTO = new UserDTO(u);
        return new UsernamePasswordAuthenticationToken(userDTO, null, userDTO.getAuthorities());
    }

}
