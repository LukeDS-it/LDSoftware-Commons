package it.ldsoftware.commons.entities.security;

import it.ldsoftware.commons.util.UserUtil;

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
public class RoleModifiers {
    private boolean insert, edit, delete, execute;

    public boolean isInsert() {
        return insert;
    }

    public void setInsert(boolean insert) {
        this.insert = insert;
    }

    public boolean isEdit() {
        return edit;
    }

    public void setEdit(boolean edit) {
        this.edit = edit;
    }

    public boolean isDelete() {
        return delete;
    }

    public void setDelete(boolean delete) {
        this.delete = delete;
    }

    public boolean isExecute() {
        return execute;
    }

    public void setExecute(boolean execute) {
        this.execute = execute;
    }

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
}
