package it.ldsoftware.primavera.test;

import it.ldsoftware.primavera.dal.security.GroupDAL;
import it.ldsoftware.primavera.model.lang.ShortTranslation;
import it.ldsoftware.primavera.model.security.Group;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
    private GroupDAL groupDAL;

    @Test
    public void contextLoads() {

    }

    @Test
    public void languageTest() {
        Group g = new Group();
        g.addTranslation("it", new ShortTranslation().withContent(CAPTION_1));
        g.addTranslation("en", new ShortTranslation().withContent(CAPTION_2));
        groupDAL.save(g);

        Group up = groupDAL.findOne(1L);
        assert up.getTranslations().size() == 2;
        assert up.getTranslation("it").getDescription().equals(CAPTION_1);
        assert up.getTranslation("en").getDescription().equals(CAPTION_2);
    }

//    @Test
//    public void findPredicate() throws Exception {
//        String cVal = "123456";
//
//        Person person = new Person();
//        person.addContact(new Contact().withContactType(PHONE).withValue("not cVal"));
//        person.addContact(new Contact().withContactType(EMAIL).withValue(cVal));
//        person.setName("Luca");
//        person.setSurname("Di Stefano");
//        person.setFullName("Luca Di Stefano");
//
//        svc.save(person);
//
//        QPerson qp = QPerson.person;
//        QContact qc = QContact.contact;
//
//        Predicate predicate1 = qp.isNotNull()
//                .and(qp.contacts.any().in(
//                        JPAExpressions.selectFrom(qc)
//                                .where(qc.contactType.eq(PHONE)
//                                        .and(qc.contactValue.eq(cVal)))));
//
//        List<Person> manual = svc.findAll(Person.class, predicate1);
//
//        Contact noResults = new Contact().withContactType(PHONE).withValue(cVal);
//        Predicate predicate2 = PredicateFactory.createPredicate(Person.class, singletonList(new Filter("contacts", noResults, false, AND)));
//
//        List<Person> auto = svc.findAll(Person.class, predicate2);
//
//        Assert.assertEquals(manual.size(), auto.size());
//
//        Contact results = new Contact().withContactType(EMAIL).withValue(cVal);
//        auto = svc.findAll(PredicateFactory.createPredicate(Person.class, singletonList(new Filter("contacts", results, false, AND))));
//
//        Assert.assertEquals(1, auto.size());
//    }
//
//    public void testExample() throws Exception {
//        Person p = new Person();
//        PredicateFactory.getFiltersByEntity(Person.class, p);
//    }

}
