package it.ldsoftware.primavera.vaadin.editors.people;

import com.vaadin.ui.VerticalLayout;
import it.ldsoftware.primavera.presentation.people.PersonDTO;
import it.ldsoftware.primavera.model.people.Person;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditor;
import it.ldsoftware.primavera.vaadin.layouts.AbstractPersonForm;
import it.ldsoftware.primavera.vaadin.util.PeopleAdder;

import java.util.List;

import static it.ldsoftware.primavera.vaadin.i18n.CommonLabels.TAB_CHILDREN;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 22/04/16.
 * Form with data for people, extends the abstract person form, adding the tab
 * to connect "Children" to the record, that are other people/organizations linked
 * to that record.
 */
public class PersonForm extends AbstractPersonForm<Person> implements PeopleAdder {

    public PersonForm(AbstractEditor parent) {
        super(parent);
    }

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
        PeopleTab tab = new PeopleTab(getTranslator(), getDatabaseService(), "", this);
        addTab(tab, getTranslator().translate(TAB_CHILDREN));
    }

    @Override
    public void addPeople(List<PersonDTO> people) {
        getBean().addChildren(people.stream()
                .map(p -> getDatabaseService().findOne(Person.class, p.getId())).collect(toList()));
    }

    @Override
    public void remPeople(List<PersonDTO> people) {
        getBean().remChildren(people.stream()
                .map(p -> getDatabaseService().findOne(Person.class, p.getId())).collect(toList()));
    }
}
