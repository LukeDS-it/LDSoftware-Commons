package it.ldsoftware.rest.payload.security;

import it.ldsoftware.rest.payload.base.LookupPayload;

/**
 * Payload for a role, contains role name and description as well
 * as modifiers that specify if the role allows insertion, deletion,
 * editing, execution of a particular application area
 */
public class RolePayload extends LookupPayload {
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
}
