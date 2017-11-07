package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.dal.security.GroupDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.presentation.security.RoleDTO;
import it.ldsoftware.primavera.services.interfaces.GroupService;
import it.ldsoftware.primavera.services.interfaces.RoleService;
import it.ldsoftware.primavera.util.PrimaveraConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class GroupBusinessService extends AbstractBusinessService<GroupDTO, Group> implements GroupService {

    private final RoleService roles;

    @Autowired
    public GroupBusinessService(BaseDAL<Group> dal, Mapper<Group, GroupDTO> mapper, RoleService roles) {
        super(dal, mapper);
        this.roles = roles;
    }

    @Override
    public boolean existsByCode(String code) {
        return ((GroupDAL) getDal()).countByCode(code) != 0;
    }

    @Override
    public void initGroups() {
        PrimaveraConstants.BASE_GROUPS.stream()
                .filter(g -> !existsByCode(g.getCode()))
                .peek(this::instantiateRoles)
                .forEach(this::save);
    }

    private void instantiateRoles(GroupDTO dto) {
        dto.getRoles().forEach(r -> {
            RoleDTO role = roles.findByRoleName(r.getCode());
            if (role != null)
                r.setId(role.getId());
        });
    }
}
