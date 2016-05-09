package it.ldsoftware.primavera.dal.security;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.entities.security.Group;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 12/04/16.
 * DAL class for the groups
 */
@Repository
public interface GroupDAL extends BaseDAL<Group> {
}
