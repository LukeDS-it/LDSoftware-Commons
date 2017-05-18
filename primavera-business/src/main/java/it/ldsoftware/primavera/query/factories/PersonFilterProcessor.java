package it.ldsoftware.primavera.query.factories;

import it.ldsoftware.primavera.model.people.Contact;
import it.ldsoftware.primavera.query.Filter;

import static it.ldsoftware.primavera.presentation.enums.ContactType.*;
import static it.ldsoftware.primavera.presentation.people.PersonDTO.*;
import static it.ldsoftware.primavera.query.FilterOperator.AND;

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
