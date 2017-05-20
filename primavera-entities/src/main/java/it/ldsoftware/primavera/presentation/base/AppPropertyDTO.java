package it.ldsoftware.primavera.presentation.base;

import it.ldsoftware.primavera.presentation.enums.PropertyType;
import lombok.Getter;
import lombok.Setter;

/**
 * Presentation model of application key
 *
 * @author Luca Di Stefano
 */
@Getter @Setter
public class AppPropertyDTO extends BaseDTO {

    String key;
    Object value;
    PropertyType propertyType;

}
