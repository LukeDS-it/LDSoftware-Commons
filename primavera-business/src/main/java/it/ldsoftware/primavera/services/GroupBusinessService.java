package it.ldsoftware.primavera.services;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.mapper.Mapper;
import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.services.interfaces.GroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Luca Di Stefano
 */
@Service
public class GroupBusinessService extends AbstractBusinessService<GroupDTO, Group> implements GroupService {

    @Autowired
    public GroupBusinessService(BaseDAL<Group> dal, Mapper<Group, GroupDTO> mapper) {
        super(dal, mapper);
    }

}
