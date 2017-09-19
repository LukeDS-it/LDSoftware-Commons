package it.ldsoftware.primavera.services.interfaces;

import it.ldsoftware.primavera.presentation.security.RoleDTO;

/**
 * @author Luca Di Stefano
 */
public interface RoleService extends BusinessService<RoleDTO> {
    RoleDTO findByRoleName(String roleName);
    boolean existsByRoleName(String roleName);
}
