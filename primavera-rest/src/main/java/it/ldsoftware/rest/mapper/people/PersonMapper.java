package it.ldsoftware.rest.mapper.people;

import it.ldsoftware.primavera.entities.people.Person;
import it.ldsoftware.rest.mapper.base.BaseMapper;
import it.ldsoftware.rest.payload.people.PersonPayload;
import it.ldsoftware.rest.payload.util.PersonType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static java.util.stream.Collectors.toSet;

@Service
public class PersonMapper extends BaseMapper<Person, PersonPayload> {

    private final ContactMapper contactMapper;

    @Autowired
    public PersonMapper(ContactMapper contactMapper) {
        this.contactMapper = contactMapper;
    }

    @Override
    public Person newEntity() {
        return new Person();
    }

    @Override
    public PersonPayload newPayload() {
        return new PersonPayload();
    }

    @Override
    public void initEntity(Person entity, PersonPayload payload) {
        entity.setBirthDate(payload.getBirthDate());
        payload.getContacts().stream()
                .map(contactMapper::translatePayload)
                .forEach(entity::addContact);
        entity.setFullName(payload.getFullName());
        entity.setName(payload.getName());
        entity.setPersonType(it.ldsoftware.primavera.util.PersonType.valueOf(payload.getType().name()));
        entity.setPeople(payload.getPeople().stream().map(this::translatePayload).collect(toSet()));
        entity.setSex(payload.getSex());
        entity.setSurname(payload.getSurname());
        entity.setUniqueId(payload.getUniqueId());
        entity.setVatInfo(payload.getVatInfo());
    }

    @Override
    public void initPayload(PersonPayload payload, Person entity) {
        payload.setBirthDate(entity.getBirthDate());
        payload.setContacts(entity.getContacts().stream()
                .map(contactMapper::translateEntity)
                .collect(toSet()));
        payload.setFullName(entity.getFullName());
        payload.setName(entity.getName());
        // TODO set people
        payload.setType(PersonType.valueOf(entity.getPersonType().name()));
        payload.setSex(entity.getSex());
        payload.setSurname(entity.getSurname());
        payload.setUniqueId(entity.getUniqueId());
        payload.setVatInfo(entity.getVatInfo());
    }
}
