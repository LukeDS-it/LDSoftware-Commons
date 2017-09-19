package it.ldsoftware.primavera.mapper.security;

import it.ldsoftware.primavera.mapper.base.LookupMapper;
import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.model.security.GroupRole;
import it.ldsoftware.primavera.model.security.RoleModifiers;
import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.presentation.security.RoleDTO;
import it.ldsoftware.primavera.util.UserUtil;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author Luca Di Stefano
 */
@Service
public class GroupMapper extends LookupMapper<Group, GroupDTO> {
    @Override
    public Group getLookupInstance(GroupDTO view) {
        Group model = new Group();
        view.getRoles().forEach(r -> {
            GroupRole tmp = groupRole(r);
            GroupRole present = model.getGroupRoles().stream().filter(gr -> gr.getRole().getCode().equals(tmp.getRole().getCode())).findFirst().orElse(null);
            if (present != null) {
                present.setModifiers(RoleModifiers.merge(present.getModifiers(), tmp.getModifiers()));
            } else {
                tmp.setGroup(model);
                model.getGroupRoles().add(tmp);
            }
        });
        return model;
    }

    @Override
    public GroupDTO getLookupDTOInstance(Group model) {
        GroupDTO dto = new GroupDTO();
        dto.setRoles(
                model.getGroupRoles().stream()
                        .flatMap(this::expandRoles)
                        .collect(Collectors.toList())
        );
        return dto;
    }

    private GroupRole groupRole(RoleDTO roleDTO) {
        GroupRole gr = new GroupRole();
        gr.setRole(new RoleMapper().convertToModel(roleDTO));
        String roleName = roleDTO.getCode();
        if (roleName.matches(".*(_E|_I|_D|_X)")) {
            gr.getRole().setCode(roleName.substring(0, roleName.length() - 2));
            String modifier = roleName.substring(roleName.length() - 2);
            gr.setModifiers(UserUtil.fromModifier(modifier));
        } else {
            gr.setModifiers(new RoleModifiers());
        }
        return gr;
    }

    private Stream<RoleDTO> expandRoles(GroupRole groupRole) {
        return groupRole.getActualRoles().stream().map(s -> {
            RoleDTO dto = new RoleMapper().convertToView(groupRole.getRole());
            dto.setCode(s);
            return dto;
        });
    }
}
