package it.ldsoftware.primavera.mapper.people;

import it.ldsoftware.primavera.mapper.security.GroupMapper;
import it.ldsoftware.primavera.mapper.security.RoleMapper;
import it.ldsoftware.primavera.model.people.User;
import it.ldsoftware.primavera.model.security.UserRole;
import it.ldsoftware.primavera.presentation.people.UserVM;

import static java.util.stream.Collectors.toList;

/**
 * This is a special mapper that maps only the minimal fields for front-end
 * viewing of an user. Since there already is a mapper for the User class,
 * this one will not implement the {@link it.ldsoftware.primavera.mapper.base.BaseMapper}
 */
public class UserViewMapper {

    public UserVM convertToView(User model) {
        UserVM view = new UserVM();

        view.setEnabled(model.isEnabled());
        view.setFullName(model.getFullName());
        view.setName(model.getName());
        view.setPrimaryEmail(model.getPrimaryEmail());
        view.setSurname(model.getSurname());
        view.setUsername(model.getUsername());

        view.setRoles(model.getUserRoles().stream().map(UserRole::getRole).map(new RoleMapper()::convertToView)
                .collect(toList()));
        view.setGroups(model.getGroups().stream().map(new GroupMapper()::convertToView).collect(toList()));

        return view;
    }

}
