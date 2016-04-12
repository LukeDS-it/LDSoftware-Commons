package it.ldsoftware.commons.dal.people;

import it.ldsoftware.commons.dal.base.BaseDAL;
import it.ldsoftware.commons.entities.people.User;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 11/04/16.
 *
 * @author luca
 *         DAL for user search
 */
@Repository
public interface UserDAL extends BaseDAL<User> {
}
