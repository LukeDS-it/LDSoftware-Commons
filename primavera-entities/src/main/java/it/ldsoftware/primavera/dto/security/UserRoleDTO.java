package it.ldsoftware.primavera.dto.security;

import it.ldsoftware.primavera.entities.security.GroupRole;
import it.ldsoftware.primavera.entities.security.UserRole;

import java.util.Locale;

/**
 * Created by luca on 22/04/16.
 * This DTO does not extend the base dto, as the user-role relationship is
 * not a base entity.
 */
public class UserRoleDTO {
    public static final String COLUMN_ROLE_NAME = "roleName", COLUMN_ROLE_DESC = "roleDesc",
            COLUMN_INSERT_ALLOWED = "insertAllowed", COLUMN_EDIT_ALLOWED = "editAllowed",
            COLUMN_DELETE_ALLOWED = "deleteAllowed", COLUMN_EXECUTE_ALLOWED = "executeAllowed";

    private String roleName, roleDesc;
    private boolean insertAllowed, editAllowed, deleteAllowed, executeAllowed;

    public UserRoleDTO(UserRole entity, Locale locale) {
        roleName = entity.getAuthority();
        roleDesc = entity.getRole().getTranslation(locale).getDescription();
        insertAllowed = entity.isInsertAllowed();
        editAllowed = entity.isEditAllowed();
        deleteAllowed = entity.isDeleteAllowed();
        executeAllowed = entity.isExecuteAllowed();
    }

    public UserRoleDTO(GroupRole entity, Locale locale) {
        roleName = entity.getAuthority();
        roleDesc = entity.getRole().getTranslation(locale).getDescription();
        insertAllowed = entity.isInsertAllowed();
        editAllowed = entity.isEditAllowed();
        deleteAllowed = entity.isDeleteAllowed();
        executeAllowed = entity.isExecuteAllowed();
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDesc() {
        return roleDesc;
    }

    public void setRoleDesc(String roleDesc) {
        this.roleDesc = roleDesc;
    }

    public boolean isInsertAllowed() {
        return insertAllowed;
    }

    public void setInsertAllowed(boolean insertAllowed) {
        this.insertAllowed = insertAllowed;
    }

    public boolean isEditAllowed() {
        return editAllowed;
    }

    public void setEditAllowed(boolean editAllowed) {
        this.editAllowed = editAllowed;
    }

    public boolean isDeleteAllowed() {
        return deleteAllowed;
    }

    public void setDeleteAllowed(boolean deleteAllowed) {
        this.deleteAllowed = deleteAllowed;
    }

    public boolean isExecuteAllowed() {
        return executeAllowed;
    }

    public void setExecuteAllowed(boolean executeAllowed) {
        this.executeAllowed = executeAllowed;
    }
}
