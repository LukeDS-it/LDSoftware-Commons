package it.ldsoftware.commons.vaadin.editors.security;

import com.vaadin.ui.Button;
import it.ldsoftware.commons.dto.security.GroupDTO;
import it.ldsoftware.commons.entities.security.Group;
import it.ldsoftware.commons.i18n.LocalizationService;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import it.ldsoftware.commons.vaadin.components.DTOGrid;
import it.ldsoftware.commons.vaadin.layouts.AbstractDetailTab;
import it.ldsoftware.commons.vaadin.util.GroupAdder;

import java.util.List;

/**
 * Created by luca on 22/04/16.
 * This kind of detail tab is used to add groups to an object
 */
public class GroupsDetailTab extends AbstractDetailTab<Group, GroupDTO> {

    private GroupAdder father;

    public GroupsDetailTab(LocalizationService localizationService, DatabaseService databaseService, String labelCaption, GroupAdder father) {
        super(localizationService, databaseService, labelCaption);
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
        return null;
    }

    @Override
    public String getCaption(Object o) {
        return ((Group) o).getTranslation(getLocale()).getContent();
    }
}
