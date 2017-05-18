package it.ldsoftware.primavera.vaadin.editors.people;

import it.ldsoftware.primavera.presentation.people.PersonDTO;
import it.ldsoftware.primavera.model.people.Person;
import it.ldsoftware.primavera.query.factories.PersonFilterProcessor;
import it.ldsoftware.primavera.util.UserUtil;
import it.ldsoftware.primavera.vaadin.components.DTOGrid;
import it.ldsoftware.primavera.vaadin.dialogs.AbstractFilterDialog;
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
        return new PersonForm(this);
    }

    @Override
    public Person createNewObject() {
        return new Person();
    }

    @Override
    protected String getBasePermission() {
        return UserUtil.ROLE_PEOPLE_ADMIN;
    }

    @Override
    public void customizeGrid(DTOGrid<Person, PersonDTO> grid) {
        super.customizeGrid(grid);
        grid.addCustomFilterProcessor(new PersonFilterProcessor());
    }

    @Override
    public AbstractFilterDialog getFilterDialog() {
        return null; // TODO
    }
}
