package it.ldsoftware.commons.test;

import org.junit.Test;
import org.junit.runner.RunWith;
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

    @Test
    public void contextLoads() {

    }

}
