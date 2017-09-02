package it.ldsoftware.primavera.dal.base;

import it.ldsoftware.primavera.model.base.AppProperty;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 11/05/16.
 * DAL for application properties
 */
@Repository
public interface AppPropertyDAL extends BaseDAL<AppProperty> {
    AppProperty findByKey(String key);
}
