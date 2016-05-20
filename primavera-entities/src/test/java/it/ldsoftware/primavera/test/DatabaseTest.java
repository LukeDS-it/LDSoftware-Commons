package it.ldsoftware.primavera.test;

import com.mysema.query.types.Predicate;
import it.ldsoftware.primavera.entities.people.Contact;
import it.ldsoftware.primavera.entities.people.Person;
import it.ldsoftware.primavera.entities.people.QContact;
import it.ldsoftware.primavera.entities.people.QPerson;
import it.ldsoftware.primavera.entities.security.Group;
import it.ldsoftware.primavera.entities.security.GroupTranslation;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.util.ContactType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

import static it.ldsoftware.primavera.util.ContactType.PHONE;

/**
 * Created by luca on 21/04/16.
 * Class that tests database creation.
 * Manual inspection of the database is suggested for optimal results.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = TestApplication.class)
public class DatabaseTest {

    @Autowired
    DatabaseService svc;

    @Test
    public void contextLoads() {

    }

    private static final String CAPTION_1 = "Caption 1", CAPTION_2 = "Caption 2";

    @Test
    public void languageTest() {
        Group g = new Group();
        g.addTranslation("it", (GroupTranslation) new GroupTranslation().withContent(CAPTION_1).withLang("it"));
        g.addTranslation("en", (GroupTranslation) new GroupTranslation().withContent(CAPTION_2).withLang("en"));
        svc.save(Group.class, g);

        Group up = svc.findOne(Group.class, 1);
        assert up.getTranslations().size() == 2;
        assert up.getTranslation("it").getDescription().equals(CAPTION_1);
        assert up.getTranslation("en").getDescription().equals(CAPTION_2);
    }

    @Test
    public void findPredicate() throws Exception {
        String cVal = "123456";

        Person person = new Person();
        person.addContact(new Contact().withContactType(PHONE).withValue("not my value"));
        person.addContact(new Contact().withContactType(ContactType.EMAIL).withValue(cVal));
        person.setName("Luca");
        person.setSurname("Di Stefano");

        svc.save(Person.class, person);

        QPerson qPerson = QPerson.person;
        QContact qContact = qPerson.contacts.any();
        qPerson.contacts.contains(qContact);
        // qContact.contactType.eq(PHONE).and(qContact.contactValue.eq(cVal)))
        Predicate predicate1 = qPerson.isNotNull()
                .and(qContact.contactType.eq(PHONE).and(qContact.contactValue.eq(cVal)));
        List<Person> all = svc.findAll(Person.class, predicate1);
        Assert.assertNotEquals(all.size(), 0);
    }

}
