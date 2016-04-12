package it.ldsoftware.commons.dal.security;

import it.ldsoftware.commons.dal.base.BaseDAL;
import it.ldsoftware.commons.entities.security.Group;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 12/04/16.
 * DAL class for the groups
 */
@Repository
public interface GroupDAL extends BaseDAL<Group> {
}
