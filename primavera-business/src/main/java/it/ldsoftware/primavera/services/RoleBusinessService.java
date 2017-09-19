package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.dal.security.RoleDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.security.Role;
import it.ldsoftware.primavera.presentation.security.RoleDTO;
import it.ldsoftware.primavera.services.interfaces.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class RoleBusinessService extends AbstractBusinessService<RoleDTO, Role> implements RoleService {

    @Autowired
    public RoleBusinessService(BaseDAL<Role> dal, Mapper<Role, RoleDTO> mapper) {
        super(dal, mapper);
    }

    @Override
    public RoleDTO findByRoleName(String roleName) {
        return getMapper().convertToView(((RoleDAL) getDal()).findByCode(roleName));
    }

    @Override
    public boolean existsByRoleName(String roleName) {
        return ((RoleDAL) getDal()).countByCode(roleName) != 0;
    }
}
