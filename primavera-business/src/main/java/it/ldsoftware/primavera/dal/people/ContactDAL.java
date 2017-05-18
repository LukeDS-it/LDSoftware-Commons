package it.ldsoftware.primavera.dal.people;

import it.ldsoftware.primavera.dal.base.BaseDAL;
import it.ldsoftware.primavera.model.people.Contact;
import org.springframework.stereotype.Repository;

/**
 * Created by luca on 11/05/16.
 * DAL for contacts
 */
@Repository
public interface ContactDAL extends BaseDAL<Contact> {
}
