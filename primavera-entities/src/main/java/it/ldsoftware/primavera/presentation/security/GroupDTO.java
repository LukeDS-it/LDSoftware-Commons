package it.ldsoftware.primavera.presentation.security;

import it.ldsoftware.primavera.presentation.base.LookupDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luca on 12/04/16.
 * DTO for group entity
 */
@Getter @Setter
public class GroupDTO extends LookupDTO {
    List<RoleDTO> roles = new ArrayList<>();
}
