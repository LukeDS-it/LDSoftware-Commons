package it.ldsoftware.commons.dal.security;

import it.ldsoftware.commons.dal.base.BaseDAL;
import it.ldsoftware.commons.entities.security.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 12/04/16.
 * DAL for roles
 */
@Repository
public interface RoleDAL extends BaseDAL<Role> {
}
