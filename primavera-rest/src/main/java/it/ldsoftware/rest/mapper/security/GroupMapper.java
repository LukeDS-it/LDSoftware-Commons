package it.ldsoftware.rest.mapper.security;

import it.ldsoftware.primavera.entities.security.Group;
import it.ldsoftware.primavera.entities.security.GroupRole;
import it.ldsoftware.primavera.entities.security.RoleModifiers;
import it.ldsoftware.primavera.util.RoleCollector;
import it.ldsoftware.rest.mapper.base.LookupMapper;
import it.ldsoftware.rest.payload.security.GroupPayload;
import it.ldsoftware.rest.payload.security.RolePayload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toSet;

@Service
public class GroupMapper extends LookupMapper<Group, GroupPayload> {

    private RoleMapper roleMapper;

    @Autowired
    public void setRoleMapper(RoleMapper roleMapper) {
        this.roleMapper = roleMapper;
    }

    @Override
    public Group newEntity() {
        return new Group();
    }

    @Override
    public GroupPayload newPayload() {
        return new GroupPayload();
    }

    @Override
    public void initLookup(Group entity, GroupPayload payload) {
        payload.getRoles()
                .stream()
                .map(this::getRoleCollector)
                .forEach(entity::addRole);
    }

    private RoleCollector getRoleCollector(RolePayload rolePayload) {
        GroupRole collector = new GroupRole();
        collector.setRole(roleMapper.translatePayload(rolePayload));
        RoleModifiers modifiers = new RoleModifiers();
        modifiers.setDelete(rolePayload.isDelete());
        modifiers.setEdit(rolePayload.isEdit());
        modifiers.setExecute(rolePayload.isExecute());
        modifiers.setInsert(rolePayload.isInsert());
        collector.setModifiers(modifiers);
        return collector;
    }

    @Override
    public void initLookupPayload(GroupPayload payload, Group entity) {
        payload.setRoles(entity.getGroupRoles()
                .stream()
                .map(this::getRole)
                .collect(toSet()));
    }

    private RolePayload getRole(GroupRole groupRole) {
        RolePayload rolePayload = roleMapper.translateEntity(groupRole.getRole());
        rolePayload.setDelete(groupRole.isDeleteAllowed());
        rolePayload.setEdit(groupRole.isEditAllowed());
        rolePayload.setExecute(groupRole.isExecuteAllowed());
        rolePayload.setInsert(groupRole.isInsertAllowed());
        return rolePayload;
    }
}
