package it.ldsoftware.primavera.query;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import it.ldsoftware.primavera.entities.people.Contact;
import it.ldsoftware.primavera.entities.people.Person;
import it.ldsoftware.primavera.entities.people.QPerson;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

import static it.ldsoftware.primavera.query.FilterOperator.AND;
import static it.ldsoftware.primavera.util.ContactType.PHONE;
import static java.util.Collections.singletonList;

/**
 * Created by luca on 20/05/16.
 * This class tests the creation of the predicates only, does not perform
 * actual search on database.
 */
public class PredicateFactoryTest {

    @Test
    @Ignore("Momentarily, because Boolean Builder orders the clauses randomly")
    public void testNestedPredicate() throws Exception {
        Person parent = new Person();
        parent.setName("Luc%");
        parent.setSurname("Di%");
        List<Filter> filterList = singletonList(new Filter("parent", parent, false, AND));

        Predicate predicate = PredicateFactory.createPredicate(Person.class, filterList);

        QPerson qp = QPerson.person;
        BooleanBuilder bb = new BooleanBuilder();

        Predicate expected = bb
                .and(qp.parent.surname.startsWithIgnoreCase("Di"))
                .and(qp.parent.name.startsWithIgnoreCase("Luc"));

        Assert.assertEquals(expected, predicate);
    }

    @Test
    public void testCollectionPredicate() {
        Contact contact = new Contact().withContactType(PHONE).withValue("123456");
        List<Filter> filterList = singletonList(new Filter("contacts", contact, false, AND));

        Predicate p = PredicateFactory.createPredicate(Person.class, filterList);
        System.out.println(p);
    }

    @Test
    public void testStringPredicate() {
        List<Filter> filterList = singletonList(new Filter("name", "Luc%", false, AND));

        Predicate predicate = PredicateFactory.createPredicate(Person.class, filterList);

        QPerson qp = QPerson.person;
        Predicate expected = new BooleanBuilder().and(qp.name.startsWithIgnoreCase("Luc")).getValue();

        Assert.assertEquals(expected, predicate);
    }

}