package it.ldsoftware.primavera.mapper.people;

import it.ldsoftware.primavera.mapper.base.BaseMapper;
import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.presentation.people.UserDTO;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Luca Di Stefano
 */
public class UserMapper extends BaseMapper<User, UserDTO> {

    @Override
    public User getModelInstance(UserDTO view) {
        User model = new User();

        new PersonMapper().fillModelFields(model, view);

        model.setEnabled(view.isEnabled());
        model.setPassword(view.getPassword());
        model.setPrimaryEmail(view.getPrimaryEmail());
        model.setUsername(view.getUsername());

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

        view.setRoles(Stream.concat(model.getGroups()
                        .stream()
                        .flatMap(group -> group.getGroupRoles().stream())
                        .flatMap(groupRole -> groupRole.getActualRoles().stream()),
                model.getUserRoles()
                        .stream()
                        .flatMap(userRole -> userRole.getActualRoles().stream()))
                .collect(Collectors.toList()));

        return view;
    }
}
