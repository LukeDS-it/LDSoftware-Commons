package it.ldsoftware.commons.vaadin.editors.security;

import it.ldsoftware.commons.dto.security.GroupDTO;
import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.vaadin.layouts.AbstractEditorForm;
import it.ldsoftware.commons.vaadin.layouts.AbstractLookupEditor;

import static it.ldsoftware.commons.util.UserUtil.ROLE_GROUP_ADMIN;

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
        return new GroupForm();
    }

    @Override
    public Group createNewObject() {
        return new Group();
    }

    @Override
    protected String getBasePermission() {
        return ROLE_GROUP_ADMIN;
    }
}
