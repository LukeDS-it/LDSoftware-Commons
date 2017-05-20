package it.ldsoftware.primavera.presentation.people;

import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.presentation.enums.PersonType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.*;

/**
 * DTO that represents a person
 *
 * @author luca
 */
@Getter @Setter
public class PersonDTO extends BaseDTO {

    public final static String FIELD_NAME = "name", FIELD_SURNAME = "", FIELD_FULL_NAME = "fullName",
            FIELD_TELEPHONE = "telephone", FIELD_MOBILE = "mobile", FIELD_EMAIL = "email",
            FIELD_RECORD_TYPE = "recordType", FIELD_UNIQUE_ID = "uniqueId",
            FIELD_VAT_INFO = "vatInfo", FIELD_SEX = "sex", FIELD_BIRTH_DATE = "birthDate";

    private String fullName, name, surname, uniqueId, vatInfo, sex;

    private LocalDate birthDate;

    private PersonType personType;

    private Set<ContactDTO> contacts = new HashSet<>();

    @Override
    public List<String> _fields() {
        List<String> tmp = super._fields();
        tmp.addAll(Arrays.asList(FIELD_FULL_NAME, FIELD_TELEPHONE, FIELD_MOBILE, FIELD_EMAIL,
                FIELD_RECORD_TYPE, FIELD_UNIQUE_ID, FIELD_VAT_INFO, FIELD_SEX, FIELD_BIRTH_DATE));
        return tmp;
    }

}
