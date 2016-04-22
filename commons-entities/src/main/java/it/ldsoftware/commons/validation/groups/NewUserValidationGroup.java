package it.ldsoftware.commons.validation.groups;

/**
 * Created by luca on 12/04/16.
 * This class specifies the group for validating a new user.
 * <p>
 * A new user must have a physical person's fields inserted
 * such as name and surname, and since we're talking about
 * a new user, also the password and the password confirmation,
 * which must be identical.
 */
public class NewUserValidationGroup extends UserValidationGroup {
}
