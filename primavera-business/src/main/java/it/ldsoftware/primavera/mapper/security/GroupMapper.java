package it.ldsoftware.primavera.mapper.security;

import it.ldsoftware.primavera.mapper.base.LookupMapper;
import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.presentation.security.GroupDTO;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class GroupMapper extends LookupMapper<Group, GroupDTO> {
    @Override
    public Group getLookupInstance(GroupDTO view) {
        return new Group();
    }

    @Override
    public GroupDTO getLookupDTOInstance(Group model) {
        return new GroupDTO();
    }
}
