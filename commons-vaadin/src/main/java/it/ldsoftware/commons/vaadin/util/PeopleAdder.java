package it.ldsoftware.commons.vaadin.util;

import it.ldsoftware.commons.dto.people.PersonDTO;

import java.util.List;

/**
 * Created by luca on 02/05/16.
 * This interface specifies that an object can add and remove people from
 * its properties
 */
public interface PeopleAdder {
    void addPeople(List<PersonDTO> people);

    void remPeople(List<PersonDTO> people);
}
