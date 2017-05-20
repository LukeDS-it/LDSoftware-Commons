package it.ldsoftware.primavera.mapper.people;

import it.ldsoftware.primavera.mapper.base.BaseMapper;
import it.ldsoftware.primavera.model.people.Person;
import it.ldsoftware.primavera.presentation.people.PersonDTO;

import static java.util.stream.Collectors.toSet;

/**
 * @author Luca Di Stefano
 */
@SuppressWarnings("Duplicates")
public class PersonMapper extends BaseMapper<Person, PersonDTO> {

    @Override
    public Person getModelInstance(PersonDTO view) {
        Person model = new Person();

        fillModelFields(model, view);

        return model;
    }

    void fillModelFields(Person model, PersonDTO view) {
        model.setBirthDate(view.getBirthDate());
        model.setFullName(view.getFullName());
        model.setName(view.getName());
        model.setPersonType(view.getPersonType());
        model.setSex(view.getSex());
        model.setSurname(view.getSurname());
        model.setUniqueId(view.getUniqueId());
        model.setVatInfo(view.getVatInfo());
        model.setId(view.getId());

        model.setContacts(view.getContacts().stream()
                .map(new ContactMapper()::getModelInstance).collect(toSet()));
    }

    @Override
    public PersonDTO getViewInstance(Person model) {
        PersonDTO view = new PersonDTO();

        fillViewFields(view, model);

        return view;
    }

    void fillViewFields(PersonDTO view, Person model) {
        view.setBirthDate(model.getBirthDate());
        view.setFullName(model.getFullName());
        view.setName(model.getName());
        view.setPersonType(model.getPersonType());
        view.setSex(model.getSex());
        view.setSurname(model.getSurname());
        view.setUniqueId(model.getUniqueId());
        view.setVatInfo(model.getVatInfo());
        view.setId(model.getId());

        view.setContacts(model.getContacts().stream()
                .map(new ContactMapper()::getViewInstance).collect(toSet()));
    }
}
