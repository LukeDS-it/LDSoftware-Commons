package it.ldsoftware.primavera.presentation.security;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by luca on 22/04/16.
 * This DTO does not extend the base dto, as the user-role relationship is
 * not a base entity.
 */
@Getter @Setter
public class UserRoleDTO {
    public static final String COLUMN_ROLE_NAME = "roleName", COLUMN_ROLE_DESC = "roleDesc",
            COLUMN_INSERT_ALLOWED = "insertAllowed", COLUMN_EDIT_ALLOWED = "editAllowed",
            COLUMN_DELETE_ALLOWED = "deleteAllowed", COLUMN_EXECUTE_ALLOWED = "executeAllowed";

    private String roleName, roleDesc;
    private boolean insertAllowed, editAllowed, deleteAllowed, executeAllowed;

    // TODO move to a mapper
//    public UserRoleDTO(UserRole entity, Locale locale) {
//        roleName = entity.getAuthority();
//        roleDesc = entity.getRole().getTranslation(locale).getDescription();
//        insertAllowed = entity.isInsertAllowed();
//        editAllowed = entity.isEditAllowed();
//        deleteAllowed = entity.isDeleteAllowed();
//        executeAllowed = entity.isExecuteAllowed();
//    }
//
//    public UserRoleDTO(GroupRole entity, Locale locale) {
//        roleName = entity.getAuthority();
//        roleDesc = entity.getRole().getTranslation(locale).getDescription();
//        insertAllowed = entity.isInsertAllowed();
//        editAllowed = entity.isEditAllowed();
//        deleteAllowed = entity.isDeleteAllowed();
//        executeAllowed = entity.isExecuteAllowed();
//    }

}
