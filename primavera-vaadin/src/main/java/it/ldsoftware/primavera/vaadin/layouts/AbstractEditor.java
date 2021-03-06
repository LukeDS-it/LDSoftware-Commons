package it.ldsoftware.primavera.vaadin.layouts;

import com.vaadin.data.Container.Filter;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import it.ldsoftware.primavera.presentation.base.BaseDTO;
import it.ldsoftware.primavera.model.base.BaseEntity;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.vaadin.components.DTOGrid;
import it.ldsoftware.primavera.vaadin.controllers.ExportController;
import it.ldsoftware.primavera.vaadin.dialogs.AbstractFilterDialog;
import it.ldsoftware.primavera.vaadin.util.DownloadSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.dao.DataIntegrityViolationException;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static com.vaadin.server.FontAwesome.*;
import static com.vaadin.ui.themes.ValoTheme.*;
import static it.ldsoftware.primavera.i18n.CommonErrors.*;
import static it.ldsoftware.primavera.i18n.CommonLabels.*;
import static it.ldsoftware.primavera.i18n.CommonMessages.*;
import static it.ldsoftware.primavera.util.UserUtil.*;
import static it.ldsoftware.primavera.vaadin.i18n.CommonLabels.LABEL_FILTER;
import static it.ldsoftware.primavera.vaadin.i18n.CommonLabels.LABEL_REM_FILTERS;
import static it.ldsoftware.primavera.vaadin.util.NotificationBuilder.showNotification;

/**
 * This abstract class sets the basic layout for viewing and editing a list of elements.
 * It hosts a menu bar, a grid to show data and the editor form.
 *
 * The abstract editor also autowires the database service and the localization service,
 * so its optimal use is to create classes that will be loaded at runtime in an user
 * interface using the @UIScope annotation.
 * For this reason try to keep the database queries at minimum during creation to avoid
 * long waiting times.
 *
 * @param <E> the entity from the database that will parametrize the form content.
 * @param <D> the DTO that the base entity E refers to, to parametrize the grid.
 * @author luca
 */
public abstract class AbstractEditor<E extends BaseEntity, D extends BaseDTO<E>> extends MVerticalLayout {

    private CssLayout menuBar;
    private DTOGrid<E, D> grid;
    private AbstractEditorForm<E> editorForm;

    private Button btnNew, btnSave, btnDelete;

    @Autowired
    private DatabaseService svc;

    private LocalizationService translator;

    @Autowired
    private MessageSource msg;

    @PostConstruct
    public void init() {
        setSizeFull();
        Locale locale = UI.getCurrent().getLocale();
        translator = new LocalizationService(msg, locale);

        createMenu();
        createGrid();
        createEditor();

        VerticalSplitPanel splitPanel = new VerticalSplitPanel();
        splitPanel.setSizeFull();
        splitPanel.setStyleName(SPLITPANEL_LARGE);
        splitPanel.addComponent(grid);
        splitPanel.addComponent(editorForm);

        addComponents(menuBar, splitPanel);
        setExpandRatio(splitPanel, 1.0f);
    }

    @Override
    public void attach() {
        super.attach();
        executeNew();
        checkSecurity();
    }

    private void createMenu() {
        menuBar = new MCssLayout().withStyleName(LAYOUT_COMPONENT_GROUP);

        btnNew = new MButton(FILE_O).withListener(this::newAction);
        btnDelete = new MButton(TRASH_O).withListener(this::deleteAction);
        Button btnDuplicate = new MButton(COPY).withListener(this::duplicateAction);
        Button btnExport = new MButton(DOWNLOAD).withListener(this::exportAction);

        Button btnMulti = new MButton(CHECK_SQUARE_O).withListener(this::multiAction);
        btnSave = new MButton(SAVE).withListener(this::saveAction);

        menuBar.addComponents(btnNew, btnSave, btnDuplicate, btnDelete, createFilterMenu(), btnMulti, btnExport);
        customizeMenu(menuBar);
    }

    private Component createFilterMenu() {
        MenuBar mnuFilter = new MenuBar();
        MenuBar.MenuItem dropDown = mnuFilter.addItem("", FILTER, null);
        dropDown.addItem(translator.translate(LABEL_FILTER), this::filterAction);
        dropDown.addItem(translator.translate(LABEL_REM_FILTERS), this::removeFilters);

        return mnuFilter;
    }

    private void newAction(ClickEvent event) {
        if (editorForm.hasNotSavedChanges()) {
            ConfirmDialog.show(getUI(), translator.translate(TITLE_CONFIRM_NOT_SAVED), translator.translate(MSG_CONFIRM_NOT_SAVED), translator.translate(YES),
                    translator.translate(NO), question -> {
                        if (question.isConfirmed()) {
                            executeNew();
                        }
                    });
        } else {
            executeNew();
        }
    }

    private void executeNew() {
        E entity = createNewObject();
        editorForm.setBean(entity);
        editorForm.selectFirstField();
    }

    private void deleteAction(ClickEvent event) {
        final Collection<?> coll = grid.getSelectedRows();

        String message = (coll.size() == 1 ? MSG_CONFIRM_DELETE_SINGLE : MSG_CONFIRM_DELETE_MULTI);

        ConfirmDialog.show(getUI(), translator.translate(TITLE_CONFIRM_DELETE),
                translator.translate(message, coll.size()), translator.translate(YES),
                translator.translate(NO), question -> {
                    if (question.isConfirmed()) {
                        coll.stream().forEach(this::tryDeletion);
                        refreshGrid();
                        executeNew();
                        showNotification(translator.translate(TITLE_DELETE_SUCCESS),
                                translator.translate(MSG_DELETE_SUCCESS),
                                NOTIFICATION_SUCCESS);
                    }
                });
    }

    private void tryDeletion(Object dto) {
        try {
            svc.delete(getEntityClass(), (getDTOClass().cast(dto)).getId());
        } catch (DataIntegrityViolationException dataIntegrity) {
            showNotification(translator.translate(TITLE_DATA_INTEGRITY),
                    translator.translate(ERROR_DATA_INTEGRITY), NOTIFICATION_FAILURE);
        }
    }

    private void duplicateAction(ClickEvent event) {
        // TODO find a way to duplicate entities
    }

    private void exportAction(ClickEvent event) {
        if (grid.getSelectedRows().size() != 0) {
            List<D> dList = new ArrayList<>();
            grid.getSelectedRows().stream().map(getDTOClass()::cast).forEach(dList::add);
            String id = DownloadSession.addToQueue(dList);
            getUI().getPage().open(ExportController.ADDRESS_EXPORT + id, "_blank");
        } else {
            showNotification(translator.translate(TITLE_GENERIC_WARNING), translator.translate(WARNING_SELECT_ITEMS),
                    NOTIFICATION_WARNING);
        }
    }

    private void filterAction(MenuBar.MenuItem item) {
        getFilterDialog().withFilterConsumer(this::useFilters).popup(TXT_FILTER);
    }

    private void removeFilters(MenuBar.MenuItem item) {
        grid.removeAllFilters();
    }

    private void useFilters(Collection<Filter> filters) {
        grid.filterBy(filters);
    }

    private void multiAction(ClickEvent event) {
        grid.toggleMultiSelection();
    }

    public void saveAction(ClickEvent event) {
        List<String> validationResult = editorForm.validate(getValidationGroups());
        if (validationResult.isEmpty()) {
            try {
                preSaveAction();
                E tmp = svc.save(getEntityClass(), editorForm.getBean());
                editorForm.setBean(svc.findFull(getEntityClass(), tmp.getId()));
                editorForm.resetSaveState();
                refreshGrid();
                showNotification(translator.translate(TITLE_SAVE_SUCCESS),
                        translator.translate(MSG_SAVE_SUCCESS), NOTIFICATION_SUCCESS);
            } catch (Exception e) {
                e.printStackTrace();

                String errors = "Unknown error";
                Throwable t = e.getCause();
                if (t != null) {
                    while (t.getCause() != null) {
                        t = t.getCause();
                    }
                    errors = t.getLocalizedMessage();
                    errors = errors.replaceAll("\\{", "");
                }
                showNotification(translator.translate(TITLE_SAVE_ERROR),
                        translator.translate(MSG_SAVE_ERROR, errors), NOTIFICATION_ERROR);

                customRollback();
            }
        } else {
            String errors = translator.translate(MSG_VALIDATION_ERROR).concat("<ul>");

            for (String vr : validationResult) {
                errors = errors.concat("<li>").concat(vr).concat("</li>");
            }

            errors += "</ul>";
            showNotification(translator.translate(TITLE_SAVE_ERROR), errors, NOTIFICATION_FAILURE);
        }
    }

    private void checkSecurity() {
        btnSave.setEnabled(isCurrentUserEnabled(getEditPermission()));
        btnDelete.setEnabled(isCurrentUserEnabled(getDeletePermission()));
        btnNew.setEnabled(isCurrentUserEnabled(getInsertPermission()));
    }

	/*
     *
     * Security methods that
	 * must be overridden if needed
	 *
	 */

    protected String getBasePermission() {
        return ROLE_ANONYMOUS;
    }

    private String getEditPermission() {
        if (getBasePermission().equals(ROLE_ANONYMOUS))
            return getBasePermission();
        return editVariant(getBasePermission());
    }

    private String getDeletePermission() {
        if (getBasePermission().equals(ROLE_ANONYMOUS))
            return getBasePermission();
        return deleteVariant(getBasePermission());
    }

    private String getInsertPermission() {
        if (getBasePermission().equals(ROLE_ANONYMOUS))
            return getBasePermission();
        return insertVariant(getBasePermission());
    }

    protected String getExecutePermission() {
        if (getBasePermission().equals(ROLE_ANONYMOUS))
            return getBasePermission();
        return executeVariant(getBasePermission());
    }

    protected void refreshGrid() {
        grid.refresh();
    }

    private void createGrid() {
        grid = new DTOGrid<>(svc, getEntityClass(), getDTOClass())
                .withLocalizationService(translator)
                .withFilterRow(getDTOClass())
                .withSelectionListeners(this::onSelectItem)
                .withSizeFull();
        customizeGrid(grid);
    }

    private void onSelectItem(SelectionEvent event) {
        if (!grid.isMultiSelection() && grid.getSelectedRows().size() > 0) {
            if (editorForm.hasNotSavedChanges()) {
                ConfirmDialog.show(getUI(), translator.translate(TITLE_CONFIRM_NOT_SAVED),
                        translator.translate(MSG_CONFIRM_NOT_SAVED),
                        translator.translate(YES), translator.translate(NO), question -> {
                            if (question.isConfirmed()) {
                                fillItemForm(event);
                                editorForm.resetSaveState();
                            }
                        });
            } else {
                fillItemForm(event);
            }
        }
    }

    private void fillItemForm(SelectionEvent event) {
        @SuppressWarnings("unchecked")
        D dto = ((D) event.getSelected().iterator().next());
        editorForm.setBean(svc.findFull(getEntityClass(), dto.getId()));
    }

    private void createEditor() {
        editorForm = getEditorInstance();
    }

    protected final DatabaseService getSvc() {
        return svc;
    }

    protected final LocalizationService getTranslator() {
        return translator;
    }

    public Class<?>[] getValidationGroups() {
        return new Class<?>[]{};
    }

    public AbstractEditorForm<E> form() {
        return editorForm;
    }

    public void preSaveAction() {
    }

    public void customRollback() {
    }

    public void customizeGrid(DTOGrid<E, D> grid) {
    }

    public void customizeMenu(CssLayout menu) {
    }

    public abstract Class<E> getEntityClass();

    public abstract Class<D> getDTOClass();

    public abstract AbstractEditorForm<E> getEditorInstance();

    public abstract E createNewObject();

    public abstract AbstractFilterDialog getFilterDialog();

}
