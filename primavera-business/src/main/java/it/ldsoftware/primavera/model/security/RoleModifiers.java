package it.ldsoftware.primavera.model.security;

import it.ldsoftware.primavera.util.UserUtil;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by luca on 12/04/16.
 * This class represents the detail of the relationship
 * between roles and another object (group or user). This
 * class specifies if the given role, for the given group/user
 * has modifiers.
 * <p>
 * Giving the base role ROLE_X to an user means granting them
 * the permission to see something, while the modifiers add
 * permissions to (I)nsert data, (E)dit data, (D)elete data
 * and e(X)ecute complex actions inside that "something".
 */
@Embeddable
@Getter @Setter
public class RoleModifiers {
    private boolean insert, edit, delete, execute;

    public List<String> getRoleWithModifiers(String role) {
        List<String> tmp = new ArrayList<>();
        if (isDelete())
            tmp.add(UserUtil.deleteVariant(role));
        if (isEdit())
            tmp.add(UserUtil.editVariant(role));
        if (isExecute())
            tmp.add(UserUtil.executeVariant(role));
        if (isInsert())
            tmp.add(UserUtil.insertVariant(role));
        return tmp;
    }

    /**
     * This function is used to merge role modifiers in role recreation from strings.
     */
    public static RoleModifiers merge(RoleModifiers mod1, RoleModifiers mod2) {
        RoleModifiers tmp = new RoleModifiers();
        tmp.insert = mod1.insert || mod2.insert;
        tmp.edit = mod1.edit || mod2.edit;
        tmp.delete = mod1.delete || mod2.delete;
        tmp.execute = mod1.execute || mod2.execute;
        return tmp;
    }
}
