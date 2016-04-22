package it.ldsoftware.commons.vaadin.editors.security;

import com.vaadin.ui.Button;
import it.ldsoftware.commons.dto.security.GroupDTO;
import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.i18n.LocalizationService;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import it.ldsoftware.commons.util.UserUtil;
import it.ldsoftware.commons.vaadin.layouts.AbstractDetailTab;
import it.ldsoftware.commons.vaadin.util.GroupAdder;

import java.util.List;

import static it.ldsoftware.commons.i18n.CommonLabels.LABEL_ADD_GROUP;
import static it.ldsoftware.commons.util.UserUtil.isCurrentUserSuperAdmin;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 22/04/16.
 * This kind of detail tab is used to add groups to an object
 */
public class GroupsDetailTab extends AbstractDetailTab<Group, GroupDTO> {

    private GroupAdder father;

    public GroupsDetailTab(LocalizationService localizationService, DatabaseService databaseService, GroupAdder father) {
        super(localizationService, databaseService, LABEL_ADD_GROUP);
        this.father = father;
    }

    @Override
    public void addToMaster(Button.ClickEvent e) {
        father.addGroups((Group) getDetailValue());
    }

    @Override
    public void addListToMaster(List<GroupDTO> groupDTOs) {
        groupDTOs.stream().map(groupDTO -> getDatabaseService().findOne(Group.class, groupDTO.getId()))
                .forEach(father::addGroups);
    }

    @Override
    public void removeFromMaster(List<GroupDTO> groupDTOs) {
        groupDTOs.stream().map(groupDTO -> getDatabaseService().findOne(Group.class, groupDTO.getId()))
                .forEach(father::remGroups);
    }

    @Override
    public Class<Group> getEntityClass() {
        return Group.class;
    }

    @Override
    public Class<GroupDTO> getDTOClass() {
        return GroupDTO.class;
    }

    @Override
    public List<Group> getDetailChoice() {
        // The user can assign all groups if he/she is super administrator
        if (isCurrentUserSuperAdmin())
            return getDatabaseService().findAll(Group.class);

        // Else can assign only the groups he/she is part of
        return UserUtil.getCurrentUser().getGroups().stream().map(dto -> getDatabaseService().findOne(Group.class, dto.getId())).collect(toList());
    }

    @Override
    public String getCaption(Object o) {
        return ((Group) o).getTranslation(getLocale()).getContent();
    }
}
