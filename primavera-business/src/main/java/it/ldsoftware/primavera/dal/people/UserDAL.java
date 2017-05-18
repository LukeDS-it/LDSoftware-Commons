package it.ldsoftware.primavera.dal.people;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.model.people.User;
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
