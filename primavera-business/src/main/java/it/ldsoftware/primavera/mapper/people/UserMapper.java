package it.ldsoftware.primavera.mapper.people;

import it.ldsoftware.primavera.mapper.base.BaseMapper;
import it.ldsoftware.primavera.mapper.security.GroupMapper;
import it.ldsoftware.primavera.mapper.security.RoleMapper;
import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.model.security.UserRole;
import it.ldsoftware.primavera.presentation.people.UserDTO;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toList;
import static java.util.stream.Collectors.toSet;

/**
 * @author Luca Di Stefano
 */
@Service
public class UserMapper extends BaseMapper<User, UserDTO> {

    @Override
    public User getModelInstance(UserDTO view) {
        User model = new User();

        new PersonMapper().fillModelFields(model, view);

        model.setEnabled(view.isEnabled());
        model.setPassword(view.getPassword());
        model.setPrimaryEmail(view.getPrimaryEmail());
        model.setUsername(view.getUsername());

        // TODO roles
        model.setGroups(view.getGroups().stream().map(new GroupMapper()::convertToModel).collect(toSet()));

        return model;
    }

    @Override
    public UserDTO getViewInstance(User model) {
        UserDTO view = new UserDTO();

        new PersonMapper().fillViewFields(view, model);

        view.setEnabled(model.isEnabled());
        view.setPassword(model.getPassword());
        view.setPrimaryEmail(model.getPrimaryEmail());
        view.setUsername(model.getUsername());

        view.setRoles(model.getUserRoles().stream().map(UserRole::getRole).map(new RoleMapper()::convertToView)
                .collect(toList()));
        view.setGroups(model.getGroups().stream().map(new GroupMapper()::convertToView).collect(toList()));

        return view;
    }
}
