package it.ldsoftware.primavera.mapper.security;

import it.ldsoftware.primavera.mapper.base.LookupMapper;
import it.ldsoftware.primavera.model.security.Role;
import it.ldsoftware.primavera.presentation.security.RoleDTO;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class RoleMapper extends LookupMapper<Role, RoleDTO> {

    @Override
    public Role getLookupInstance(RoleDTO view) {
        return new Role();
    }

    @Override
    public RoleDTO getLookupDTOInstance(Role model) {
        return new RoleDTO();
    }
}
