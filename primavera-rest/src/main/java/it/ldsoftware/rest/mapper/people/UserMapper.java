package it.ldsoftware.rest.mapper.people;

import it.ldsoftware.primavera.entities.people.User;
import it.ldsoftware.rest.mapper.base.BaseMapper;
import it.ldsoftware.rest.mapper.security.GroupMapper;
import it.ldsoftware.rest.mapper.security.RoleMapper;
import it.ldsoftware.rest.payload.people.UserPayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

import static java.util.stream.Collectors.toSet;

@Service
public class UserMapper extends BaseMapper<User, UserPayload> {

    private PersonMapper personMapper;

    private RoleMapper roleMapper;

    private GroupMapper groupMapper;

    @Override
    public User newEntity() {
        return new User();
    }

    @Override
    public void initEntity(User entity, UserPayload payload) {
        personMapper.initEntity(entity, payload);
        entity.setConfirmPassword(payload.getConfirmPassword());
        entity.setEnabled(payload.isEnabled());
        entity.setGroups(payload.getGroups().stream()
                .map(groupMapper::translatePayload).collect(toSet()));
        entity.setPassword(payload.getPassword());
        entity.setPrimaryEmail(payload.getPrimaryEmail());
        entity.setUsername(payload.getUsername());
//        entity.setUserRoles(payload.getUserRoles().stream() TODO
//                .map(roleMapper::translatePayload).collect(toSet()));
    }

    @Override
    public UserPayload newPayload() {
        return new UserPayload();
    }

    @Override
    public void initPayload(UserPayload payload, User entity) {
        personMapper.initPayload(payload, entity);
        payload.setConfirmPassword(entity.getConfirmPassword());
        payload.setEnabled(entity.isEnabled());
        // payload.setGroups(); TODO
        payload.setPassword(entity.getPassword());
        payload.setPrimaryEmail(entity.getPrimaryEmail());
        payload.setUsername(entity.getUsername());
        // payload.setUserRoles(); TODO
    }
    @Autowired
    public void setPersonMapper(PersonMapper personMapper) {
        this.personMapper = personMapper;
    }

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Autowired
    public void setGroupMapper(GroupMapper groupMapper) {
        this.groupMapper = groupMapper;
    }

}
