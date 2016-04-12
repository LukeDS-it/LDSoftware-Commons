package it.ldsoftware.commons.dto.security;

import it.ldsoftware.commons.dto.base.LookupDTO;
import it.ldsoftware.commons.entities.security.Group;

import java.util.Locale;

/**
 * Created by luca on 12/04/16.
 * DTO for group entity
 */
public class GroupDTO extends LookupDTO<Group> {
    public GroupDTO(Group entity, Locale locale) {
        super(entity, locale);
    }
}
