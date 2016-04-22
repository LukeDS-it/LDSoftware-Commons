package it.ldsoftware.commons.test;

import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.entities.security.GroupTranslation;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

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
        assert up.getTranslation("it").getContent().equals(CAPTION_1);
        assert up.getTranslation("en").getContent().equals(CAPTION_2);
    }

}
