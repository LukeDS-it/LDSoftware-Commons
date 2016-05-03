package it.ldsoftware.commons.vaadin.editors.people;

import it.ldsoftware.commons.dto.people.PersonDTO;
import it.ldsoftware.commons.entities.people.Person;
import it.ldsoftware.commons.util.UserUtil;
import it.ldsoftware.commons.vaadin.layouts.AbstractEditor;
import it.ldsoftware.commons.vaadin.layouts.AbstractEditorForm;

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
