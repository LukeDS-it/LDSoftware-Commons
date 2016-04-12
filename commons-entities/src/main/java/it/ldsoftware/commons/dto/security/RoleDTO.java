package it.ldsoftware.commons.dto.security;

import it.ldsoftware.commons.dto.base.LookupDTO;
import it.ldsoftware.commons.entities.security.Role;

import java.util.Locale;

/**
 * Created by luca on 12/04/16.
 * DTO for role
 */
public class RoleDTO extends LookupDTO<Role> {
    public RoleDTO(Role entity, Locale locale) {
        super(entity, locale);
    }
}
