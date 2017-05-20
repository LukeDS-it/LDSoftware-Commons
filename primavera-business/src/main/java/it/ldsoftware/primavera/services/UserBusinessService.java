package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.presentation.people.UserDTO;
import it.ldsoftware.primavera.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static it.ldsoftware.primavera.model.people.QUser.user;

/**
 * @author Luca Di Stefano
 */
@Service
public class UserBusinessService extends AbstractBusinessService<UserDTO, User> implements UserService {

    @Autowired
    public UserBusinessService(BaseDAL<User> dal, Mapper<User, UserDTO> mapper) {
        super(dal, mapper);
    }

    @Override
    public User findByUsername(String username) {
        return getDal().findOne(user.username.eq(username).or(user.primaryEmail.eq(username)));
    }
}
