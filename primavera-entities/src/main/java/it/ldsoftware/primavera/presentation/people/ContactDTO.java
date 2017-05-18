package it.ldsoftware.primavera.presentation.people;

import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.presentation.enums.ContactType;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

/**
 * Created by luca on 22/04/16.
 * Representation of a contact
 */
@Getter @Setter
public class ContactDTO extends BaseDTO {

    public static final String FIELD_CONTACT_TYPE = "contactType",
                                FIELD_CONTACT_VALUE = "contactValue";

    private ContactType contactType;
    private String contactValue;

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_CONTACT_TYPE, FIELD_CONTACT_VALUE));
        return tmp;
    }

}
