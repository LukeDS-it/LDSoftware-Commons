package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.mapper.people.UserViewMapper;
import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.presentation.people.UserDTO;
import it.ldsoftware.primavera.presentation.people.UserVM;
import it.ldsoftware.primavera.services.interfaces.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static it.ldsoftware.primavera.model.people.QUser.user;

/**
 * @author Luca Di Stefano
 */
@Service
public class UserBusinessService extends AbstractBusinessService<UserDTO, User> implements UserService {

    private UserViewMapper vmMapper;

    @Autowired
    public UserBusinessService(BaseDAL<User> dal, Mapper<User, UserDTO> mapper) {
        super(dal, mapper);
        vmMapper = new UserViewMapper();
    }

    @Override
    public User findByUsername(String username) {
        return getDal().findOne(user.username.eq(username).or(user.primaryEmail.eq(username)));
    }

    @Override
    public UserVM findVMById(Long id) {
        return vmMapper.convertToView(getDal().findOne(id));
    }
}
