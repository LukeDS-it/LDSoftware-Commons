package it.ldsoftware.primavera.vaadin.editors.security;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.renderers.ImageRenderer;
import de.datenhahn.vaadin.componentrenderer.ComponentRenderer;
import it.ldsoftware.primavera.presentation.security.UserRoleDTO;
import it.ldsoftware.primavera.model.security.QRole;
import it.ldsoftware.primavera.model.security.Role;
import it.ldsoftware.primavera.model.security.UserRole;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.vaadin.data.util.converters.BoolConverter;
import it.ldsoftware.primavera.vaadin.util.RoleAdder;
import it.ldsoftware.primavera.vaadin.theme.MetricConstants;
import org.springframework.security.core.GrantedAuthority;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.ArrayList;
import java.util.List;

import static com.vaadin.server.FontAwesome.REFRESH;
import static com.vaadin.server.FontAwesome.TRASH_O;
import static com.vaadin.ui.AbstractSelect.ItemCaptionMode.EXPLICIT_DEFAULTS_ID;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_BORDERLESS;
import static it.ldsoftware.primavera.presentation.security.UserRoleDTO.*;
import static it.ldsoftware.primavera.i18n.CommonLabels.*;
import static it.ldsoftware.primavera.i18n.LanguageUtils.getCheckboxName;
import static it.ldsoftware.primavera.util.UserUtil.getCurrentUser;
import static it.ldsoftware.primavera.util.UserUtil.isCurrentUserSuperAdmin;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 22/04/16.
 * Detail tab for adding roles
 */
public class RolesDetailTab extends MVerticalLayout {

    private DatabaseService databaseService;
    private LocalizationService localizationService;

    private RoleAdder master;

    private ComboBox perms;
    private CheckBox deleteAllowed, editAllowed, executeAllowed, insertAllowed;

    private BeanItemContainer<UserRoleDTO> bic;

    public RolesDetailTab(LocalizationService localizationService, DatabaseService databaseService, RoleAdder master) {
        this.localizationService = localizationService;
        this.databaseService = databaseService;
        this.master = master;
        buildContent();
    }

    public void refreshGrid(List<UserRoleDTO> list) {
        bic.removeAllItems();
        bic.addAll(list);
    }

    private void addToMaster(ClickEvent e) {
        Role r = (Role) perms.getValue();
        UserRole userRole = new UserRole()
                .withRole(r)
                .withDeleteAllowed(deleteAllowed.getValue())
                .withEditAllowed(editAllowed.getValue())
                .withExecuteAllowed(executeAllowed.getValue())
                .withInsertAllowed(insertAllowed.getValue());
        master.addRoles(userRole);
    }

    private void removeFromMaster(UserRoleDTO roleDTO) {
        UserRole userRole = new UserRole()
                .withRole(databaseService.findOne(Role.class, QRole.role.code.eq(roleDTO.getRoleName())));
        master.remRoles(userRole);
    }

    private List<Role> getDetailChoice() {
        if (isCurrentUserSuperAdmin())
            return databaseService.findAll(Role.class);

        return getCurrentUser().getAuthorities().stream().map(GrantedAuthority::getAuthority)
                .map(str -> databaseService.findOne(Role.class, QRole.role.code.eq(str)))
                .collect(toList());
    }

    private String getCaption(Object o) {
        return ((Role) o).getTranslation(UI.getCurrent().getLocale()).getDescription();
    }

    private void buildContent() {
        Grid grid = new Grid();
        customizeGrid(grid);

        perms = new ComboBox();
        perms.setWidth(MetricConstants.FIELD_WIDTH);

        deleteAllowed = new CheckBox(localizationService.translate(getCheckboxName("deleteAllowed")));
        editAllowed = new CheckBox(localizationService.translate(getCheckboxName("editAllowed")));
        executeAllowed = new CheckBox(localizationService.translate(getCheckboxName("executeAllowed")));
        insertAllowed = new CheckBox(localizationService.translate(getCheckboxName("insertAllowed")));

        Button btnRefresh = new MButton(REFRESH).withListener(this::refreshCombo)
                .withDescription(localizationService.translate(TOOLTIP_REFRESH));
        Button btnAdd = new MButton().withListener(this::addToMaster)
                .withCaption(localizationService.translate(ADD));

        addComponent(grid);
        addComponent(new MHorizontalLayout(new Label(localizationService.translate("cmb.role")), perms, btnRefresh));
        addComponent(new MHorizontalLayout(insertAllowed, editAllowed, deleteAllowed, executeAllowed));
        addComponent(new MHorizontalLayout(btnAdd));

        refreshCombo(null);
    }

    private void customizeGrid(Grid grid) {
        bic = new BeanItemContainer<>(UserRoleDTO.class, new ArrayList<>());
        GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(bic);

        gpc.addGeneratedProperty("delete", new PropertyValueGenerator<Component>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getValue(Item item, Object itemId, Object propertyId) {
                return new MButton(TRASH_O)
                        .withListener(l -> removeFromMaster((UserRoleDTO) itemId))
                        .withStyleName(BUTTON_BORDERLESS);
            }

            @Override
            public Class<Component> getType() {
                return Component.class;
            }
        });

        grid.setSizeFull();
        grid.setHeight("200px");

        grid.setContainerDataSource(gpc);

        grid.setColumnOrder("delete", COLUMN_ROLE_NAME, COLUMN_ROLE_DESC, COLUMN_INSERT_ALLOWED, COLUMN_EDIT_ALLOWED,
                COLUMN_DELETE_ALLOWED, COLUMN_EXECUTE_ALLOWED);

        BoolConverter bc = new BoolConverter();

        grid.getColumn(COLUMN_ROLE_NAME).setWidth(250);
        grid.getColumn(COLUMN_INSERT_ALLOWED).setRenderer(new ImageRenderer(), bc).setWidth(80);
        grid.getColumn(COLUMN_EDIT_ALLOWED).setRenderer(new ImageRenderer(), bc).setWidth(80);
        grid.getColumn(COLUMN_DELETE_ALLOWED).setRenderer(new ImageRenderer(), bc).setWidth(80);
        grid.getColumn(COLUMN_EXECUTE_ALLOWED).setRenderer(new ImageRenderer(), bc).setWidth(80);

        grid.getColumns().forEach(column -> column.setHeaderCaption(
                localizationService.translate("column.".concat(column.getPropertyId().toString()))));

        grid.getColumn("delete").setRenderer(new ComponentRenderer()).setWidth(50).setHeaderCaption("");

    }

    private void refreshCombo(ClickEvent e) {
        BeanItemContainer<Role> container = new BeanItemContainer<>(Role.class, getDetailChoice());

        perms.setContainerDataSource(container);
        perms.setItemCaptionMode(EXPLICIT_DEFAULTS_ID);

        perms.getItemIds()
                .stream()
                .forEach(ogg -> perms.setItemCaption(ogg, getCaption(ogg)));
    }
}
