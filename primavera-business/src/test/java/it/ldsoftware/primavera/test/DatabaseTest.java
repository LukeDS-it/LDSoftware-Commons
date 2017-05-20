package it.ldsoftware.primavera.test;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.JPAExpressions;
import it.ldsoftware.primavera.dal.people.PersonDAL;
import it.ldsoftware.primavera.model.people.Contact;
import it.ldsoftware.primavera.model.people.Person;
import it.ldsoftware.primavera.model.people.QContact;
import it.ldsoftware.primavera.model.people.QPerson;
import it.ldsoftware.primavera.presentation.lang.ShortTranslationDTO;
import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.query.Filter;
import it.ldsoftware.primavera.query.PredicateFactory;
import it.ldsoftware.primavera.services.interfaces.GroupService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static it.ldsoftware.primavera.presentation.enums.ContactType.EMAIL;
import static it.ldsoftware.primavera.presentation.enums.ContactType.PHONE;
import static it.ldsoftware.primavera.query.FilterOperator.AND;
import static it.ldsoftware.primavera.query.PredicateFactory.createPredicate;
import static java.util.Collections.singletonList;
import static java.util.stream.Collectors.toList;
import static java.util.stream.StreamSupport.stream;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.assertThat;

/**
 * Created by luca on 21/04/16.
 * Class that tests database creation.
 * Manual inspection of the database is suggested for optimal results.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = TestApplication.class)
public class DatabaseTest {

    private static final String CAPTION_1 = "Caption 1", CAPTION_2 = "Caption 2";

    @Autowired
    private GroupService gService;

    @Autowired
    private PersonDAL personDAL;

    @Test
    public void contextLoads() {

    }

    @Test
    public void languageTest() {
        GroupDTO saved = new GroupDTO();
        saved.addTranslation("it", new ShortTranslationDTO().withContent(CAPTION_1));
        saved.addTranslation("en", new ShortTranslationDTO().withContent(CAPTION_2));
        gService.save(saved);

        GroupDTO retrieved = gService.findOne(1L);
        assertThat(retrieved.getTranslations(), samePropertyValuesAs(saved.getTranslations()));
    }

    @Test
    public void predicateTest() throws Exception {
        String cVal = "123456";

        Person person = new Person();
        person.addContact(new Contact().withContactType(PHONE).withValue("not cVal"));
        person.addContact(new Contact().withContactType(EMAIL).withValue(cVal));
        person.setName("Luca");
        person.setSurname("Di Stefano");
        person.setFullName("Luca Di Stefano");

        personDAL.save(person);

        QPerson qp = QPerson.person;
        QContact qc = QContact.contact;

        Predicate predicate1 = qp.isNotNull()
                .and(qp.contacts.any().in(
                        JPAExpressions.selectFrom(qc)
                                .where(qc.contactType.eq(PHONE)
                                        .and(qc.contactValue.eq(cVal)))));

        List<Person> manual = stream(personDAL.findAll(predicate1).spliterator(), false).collect(toList());

        Contact noResults = new Contact().withContactType(PHONE).withValue(cVal);
        Predicate predicate2 = createPredicate(Person.class, singletonList(new Filter("contacts", noResults, false, AND)));

        List<Person> auto = stream(personDAL.findAll(predicate2).spliterator(), false).collect(toList());

        Assert.assertEquals(manual.size(), auto.size());

        Contact results = new Contact().withContactType(EMAIL).withValue(cVal);
        auto = stream(
                personDAL.findAll(createPredicate(Person.class, singletonList(new Filter("contacts", results, false, AND))))
                        .spliterator(), false)
                .collect(toList());

        Assert.assertEquals(1, auto.size());
    }

    public void testExample() throws Exception {
        Person p = new Person();
        PredicateFactory.getFiltersByEntity(Person.class, p);
    }

}
