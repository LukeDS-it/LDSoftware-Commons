package it.ldsoftware.primavera.vaadin.editors.security;

import it.ldsoftware.primavera.presentation.security.GroupDTO;
import it.ldsoftware.primavera.model.security.Group;
import it.ldsoftware.primavera.vaadin.dialogs.AbstractFilterDialog;
import it.ldsoftware.primavera.vaadin.layouts.AbstractEditorForm;
import it.ldsoftware.primavera.vaadin.layouts.AbstractLookupEditor;

import static it.ldsoftware.primavera.util.UserUtil.ROLE_GROUP_ADMIN;

/**
 * Created by luca on 03/05/16.
 * This is the basic editor for the groups
 */
public class GroupEditor extends AbstractLookupEditor<Group, GroupDTO> {

    @Override
    public Class<Group> getEntityClass() {
        return Group.class;
    }

    @Override
    public Class<GroupDTO> getDTOClass() {
        return GroupDTO.class;
    }

    @Override
    public AbstractEditorForm<Group> getEditorInstance() {
        return new GroupForm(this);
    }

    @Override
    public Group createNewObject() {
        return new Group();
    }

    @Override
    protected String getBasePermission() {
        return ROLE_GROUP_ADMIN;
    }

    @Override
    public AbstractFilterDialog getFilterDialog() {
        return null; // TODO
    }
}
