package it.ldsoftware.tests.ui;

import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;

/**
 * Created by luca on 16/05/16.
 * Test UI for all the components in the primavera vaadin framework
 */
@SpringUI(path = "/")
public class TestMainUI extends UI {

    @Override
    protected void init(VaadinRequest request) {

    }

}
