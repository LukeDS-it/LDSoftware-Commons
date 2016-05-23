package it.ldsoftware.primavera.query.factories;

import it.ldsoftware.primavera.entities.people.Contact;
import it.ldsoftware.primavera.query.Filter;

import static it.ldsoftware.primavera.dto.people.PersonDTO.FIELD_EMAIL;
import static it.ldsoftware.primavera.dto.people.PersonDTO.FIELD_MOBILE;
import static it.ldsoftware.primavera.dto.people.PersonDTO.FIELD_TELEPHONE;
import static it.ldsoftware.primavera.query.FilterOperator.AND;
import static it.ldsoftware.primavera.util.ContactType.EMAIL;
import static it.ldsoftware.primavera.util.ContactType.MOBILE_PHONE;
import static it.ldsoftware.primavera.util.ContactType.PHONE;

/**
 * Created by luca on 20/05/16.
 * Filter factory for people
 */
public class PersonFilterProcessor extends FilterProcessor {
    @Override
    public Filter createFilterFor(String property, Object strVal) {
        Object value = null;
        String actualProperty = "";
        switch (property) {
            case FIELD_TELEPHONE:
                actualProperty = "contacts";
                value = new Contact().withContactType(PHONE).withValue(strVal.toString());
                break;
            case FIELD_EMAIL:
                actualProperty = "contacts";
                value = new Contact().withContactType(EMAIL).withValue(strVal.toString());
                break;
            case FIELD_MOBILE:
                actualProperty = "contacts";
                value = new Contact().withContactType(MOBILE_PHONE).withValue(strVal.toString());
            default:
                break;
        }
        return new Filter(actualProperty, value, false, AND);
    }
}
