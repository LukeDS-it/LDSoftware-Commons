package it.ldsoftware.commons.vaadin.editors.people;

import com.vaadin.ui.VerticalLayout;
import it.ldsoftware.commons.entities.people.Person;
import it.ldsoftware.commons.vaadin.layouts.AbstractPersonForm;

/**
 * Created by luca on 22/04/16.
 * Form with data for people, extends the abstract person form, adding the tab
 * to connect "Children" to the record, that are other people/organizations linked
 * to that record.
 */
public class PersonForm extends AbstractPersonForm<Person> {



    @Override
    public Person findPerson(long id) {
        return getDatabaseService().findOne(Person.class, id);
    }

    @Override
    public void addPersonalFields(VerticalLayout generalTab) {

    }

    @Override
    public void addOtherTabs() {
        super.addOtherTabs();
        // TODO
    }
}
