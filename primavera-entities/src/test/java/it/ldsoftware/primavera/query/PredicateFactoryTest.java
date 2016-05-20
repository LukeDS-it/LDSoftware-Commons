package it.ldsoftware.primavera.query;

import com.mysema.query.types.Predicate;
import it.ldsoftware.primavera.entities.people.Contact;
import it.ldsoftware.primavera.entities.people.Person;
import it.ldsoftware.primavera.entities.people.QPerson;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static it.ldsoftware.primavera.query.FilterOperator.AND;
import static it.ldsoftware.primavera.util.ContactType.PHONE;

/**
 * Created by luca on 20/05/16.
 * Testing of the predicate factory
 */
public class PredicateFactoryTest {

    @Test
    public void createPredicateBasic() throws Exception {
        Person person = new Person();
        Contact contact = new Contact().withContactType(PHONE).withValue("123456");
        person.addContact(contact);

        QPerson qPerson = QPerson.person;
        Predicate predicate1 = qPerson.isNotNull().and(qPerson.contacts.any().eq(contact));
        System.out.println(predicate1);

        List<Filter> filters = new ArrayList<>();
        filters.add(new Filter("contacts", contact, false, AND));
        Predicate predicate2 = PredicateFactory.createPredicate(Person.class, filters);
        System.out.println(predicate2);

        Assert.assertEquals(predicate1, predicate2);
    }

    @Test
    public void getFiltersByEntity() throws Exception {

    }

}