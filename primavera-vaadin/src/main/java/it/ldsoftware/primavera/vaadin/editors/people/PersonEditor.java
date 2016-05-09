package it.ldsoftware.primavera.vaadin.editors.people;

import it.ldsoftware.primavera.dto.people.PersonDTO;
import it.ldsoftware.primavera.entities.people.Person;
import it.ldsoftware.primavera.util.UserUtil;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditor;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditorForm;

/**
 * Created by luca on 22/04/16.
 * Basic editor for people
 *
 */
public class PersonEditor extends AbstractEditor<Person, PersonDTO> {
    @Override
    public Class<Person> getEntityClass() {
        return Person.class;
    }

    @Override
    public Class<PersonDTO> getDTOClass() {
        return PersonDTO.class;
    }

    @Override
    public AbstractEditorForm<Person> getEditorInstance() {
        return null;
    }

    @Override
    public Person createNewObject() {
        return new Person();
    }

    @Override
    protected String getBasePermission() {
        return UserUtil.ROLE_PEOPLE_ADMIN;
    }
}
