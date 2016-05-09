package it.ldsoftware.primavera.dal.security;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.entities.security.Role;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 12/04/16.
 * DAL for roles
 */
@Repository
public interface RoleDAL extends BaseDAL<Role> {
}
