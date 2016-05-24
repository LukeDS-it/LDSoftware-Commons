package it.ldsoftware.primavera.vaadin.editors.security;

import it.ldsoftware.primavera.dto.security.RoleDTO;
import it.ldsoftware.primavera.entities.security.Role;
import it.ldsoftware.primavera.vaadin.components.DTOGrid;
import it.ldsoftware.primavera.vaadin.dialogs.AbstractFilterDialog;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditorForm;
import it.ldsoftware.primavera.vaadin.layouts.AbstractLookupEditor;

import static it.ldsoftware.primavera.dto.base.LookupDTO.FIELD_CODE;
import static it.ldsoftware.primavera.util.UserUtil.ROLE_PERMISSION_ADMIN;

/**
 * Created by luca on 03/05/16.
 * Base editor for roles
 */
public class RoleEditor extends AbstractLookupEditor<Role, RoleDTO> {

    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public Class<RoleDTO> getDTOClass() {
        return RoleDTO.class;
    }

    @Override
    public AbstractEditorForm<Role> getEditorInstance() {
        return new RoleForm(this);
    }

    @Override
    public Role createNewObject() {
        return new Role();
    }

    @Override
    protected String getBasePermission() {
        return ROLE_PERMISSION_ADMIN;
    }

    @Override
    public void customizeGrid(DTOGrid<Role, RoleDTO> grid) {
        super.customizeGrid(grid);
        grid.getColumn(FIELD_CODE).setWidth(200);
    }

    @Override
    public AbstractFilterDialog getFilterDialog() {
        return null; // TODO
    }
}
