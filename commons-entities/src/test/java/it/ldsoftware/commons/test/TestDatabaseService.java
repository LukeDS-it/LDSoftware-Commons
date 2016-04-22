package it.ldsoftware.commons.test;

import it.ldsoftware.commons.services.AbstractDatabaseService;
import org.springframework.stereotype.Service;

/**
 * Created by luca on 22/04/16.
 * This extends the abstract database service to be
 * useable in tests
 */
@Service
public class TestDatabaseService extends AbstractDatabaseService {
}
