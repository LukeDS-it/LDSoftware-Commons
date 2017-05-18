package it.ldsoftware.primavera.vaadin.layouts;

import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Label;
import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.vaadin.components.DTOGrid;
import it.ldsoftware.primavera.vaadin.dialogs.SelectDialog;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.ArrayList;
import java.util.List;

import static com.vaadin.server.FontAwesome.ELLIPSIS_H;
import static com.vaadin.server.FontAwesome.REFRESH;
import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.AbstractSelect.ItemCaptionMode.EXPLICIT_DEFAULTS_ID;
import static it.ldsoftware.primavera.i18n.CommonLabels.*;
import static it.ldsoftware.primavera.vaadin.theme.MetricConstants.FIELD_WIDTH;

/**
 * Created by luca on 22/04/16.
 * This represents an abstract additional tab to add to the
 * editor forms, it features a "detail" grid to list
 * detailed information about the primary object.
 * <p>
 * It also allows inserting/removing the details from the grid via either
 * a combo box or a select dialog.
 */
public abstract class AbstractDetailTab<E extends BaseEntity, D extends BaseDTO<E>> extends MVerticalLayout {

    private DTOGrid<E, D> grid;
    private ComboBox combo;
    private Button btnRefresh, btnAdd, btnBrowse;

    private LocalizationService localizationService;
    private DatabaseService databaseService;

    public AbstractDetailTab(LocalizationService localizationService, DatabaseService databaseService, String labelCaption) {
        this.localizationService = localizationService;
        this.databaseService = databaseService;
        init(labelCaption);
    }

    public void refreshGrid(List<D> entities) {
        grid.refresh(entities);
    }

    protected Object getDetailValue() {
        return combo.getValue();
    }

    public AbstractDetailTab<E, D> withBrowseButton() {
        btnBrowse.setVisible(true);
        hideComboBox();
        return this;
    }

    private void hideComboBox() {
        combo.setVisible(false);
        btnRefresh.setVisible(false);
        btnAdd.setVisible(false);
    }

    private void init(String labelCaption) {
        grid = new DTOGrid<>(new ArrayList<>(), getDTOClass())
                .withLocalizationService(localizationService)
                .withHeight(200, PIXELS)
                .withDeleteColumn(this::removeFromMaster);
        customizeGrid(grid);

        Label label = new Label(localizationService.translate(labelCaption));
        combo = new ComboBox();
        combo.setWidth(FIELD_WIDTH);

        btnRefresh = new MButton(REFRESH).withListener(this::refreshCombo).withDescription(localizationService.translate(TOOLTIP_REFRESH));
        btnAdd = new MButton().withListener(this::addToMaster).withCaption(localizationService.translate(BTN_ADD));
        btnBrowse = new MButton(ELLIPSIS_H).withVisible(false).withListener(this::openBrowseWindow);

        addComponents(grid, new MHorizontalLayout(label, combo, btnRefresh, btnBrowse, btnAdd));
        refreshCombo(null);
    }

    private void openBrowseWindow(ClickEvent event) {
        SelectDialog<E, D> sd = new SelectDialog<>(databaseService, getEntityClass(), getDTOClass(), localizationService)
                .withSelectionHandler(this::addListToMaster);
        customizeSelectDialog(sd);
        sd.popup(getDialogTitle());
    }

    private void refreshCombo(ClickEvent event) {
        BeanItemContainer<E> container = new BeanItemContainer<>(getEntityClass(), getDetailChoice());

        combo.setContainerDataSource(container);
        combo.setItemCaptionMode(EXPLICIT_DEFAULTS_ID);

        combo.getItemIds()
                .stream()
                .forEach(ogg -> combo.setItemCaption(ogg, getCaption(ogg)));
    }

    protected LocalizationService getLocalizationService() {
        return localizationService;
    }

    protected DatabaseService getDatabaseService() {
        return databaseService;
    }

    /**
     * Override this if you need to customize the select dialog
     *
     * @param sd the select dialog to customize
     */
    protected void customizeSelectDialog(SelectDialog<E, D> sd) {

    }

    /**
     * Override this if you want to set the select dialog title to something else
     *
     * @return a string with the title of the select dialog
     */
    protected String getDialogTitle() {
        return localizationService.translate(TITLE_SELECT_ELEMENTS);
    }

    /**
     * Override this if you want to customize the grid
     *
     * @param grid the grid to customize
     */
    protected void customizeGrid(DTOGrid<E, D> grid) {

    }

    public abstract void addToMaster(ClickEvent e);

    public abstract void addListToMaster(List<D> dList);

    public abstract void removeFromMaster(List<D> dList);

    public abstract Class<E> getEntityClass();

    public abstract Class<D> getDTOClass();

    public abstract List<E> getDetailChoice();

    public abstract String getCaption(Object o);

}
