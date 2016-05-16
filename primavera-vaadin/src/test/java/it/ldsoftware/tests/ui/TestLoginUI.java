package it.ldsoftware.tests.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

/**
 * Created by luca on 16/05/16.
 * Secured UI accessible only after login
 */
@SpringUI(path = "/secured")
public class TestLoginUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

    }

}
