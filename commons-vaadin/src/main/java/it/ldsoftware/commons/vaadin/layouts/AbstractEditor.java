package it.ldsoftware.commons.vaadin.layouts;

import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalSplitPanel;
import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.base.BaseEntity;
import it.ldsoftware.commons.i18n.LocalizationService;
import it.ldsoftware.commons.query.Filter;
import it.ldsoftware.commons.query.PredicateFactory;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import it.ldsoftware.commons.vaadin.components.DTOGrid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.vaadin.server.FontAwesome.*;
import static com.vaadin.ui.themes.ValoTheme.*;
import static it.ldsoftware.commons.i18n.CommonLabels.*;
import static it.ldsoftware.commons.vaadin.util.NotificationBuilder.showNotification;

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
    private List<Filter> filters = new ArrayList<>();

    @Autowired
    private DatabaseService svc;
    @Autowired
    private LocalizationService translator;

    @PostConstruct
    public void init() {
        setSizeFull();
        translator.setLocale(UI.getCurrent().getLocale());

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

    private void createMenu() {
        menuBar = new MCssLayout().withStyleName(LAYOUT_COMPONENT_GROUP);

        Button btnNew = new MButton(FILE_O).withListener(this::newAction);
        Button btnDelete = new MButton(TRASH_O).withListener(this::deleteAction);
        Button btnDuplicate = new MButton(COPY).withListener(this::duplicateAction);
        Button btnExport = new MButton(DOWNLOAD).withListener(this::exportAction);
        Button btnFilter = new MButton(FILTER).withListener(this::filterAction);
        Button btnMulti = new MButton(CHECK_SQUARE_O).withListener(this::multiAction);
        Button btnSave = new MButton(SAVE).withListener(this::saveAction);

        menuBar.addComponents(btnNew, btnSave, btnDuplicate, btnDelete, btnFilter, btnMulti, btnExport);
        customizeMenu(menuBar);
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
                        showNotification(translator.translate("title.delete.success"),
                                translator.translate("msg.delete.success"),
                                NOTIFICATION_SUCCESS);
                    }
                });
    }

    private void tryDeletion(Object dto) {
        try {
            svc.delete(getEntityClass(), (getDTOClass().cast(dto)).getId());
        } catch (DataIntegrityViolationException dataIntegrity) {
            showNotification(translator.translate("title.data.integrity"),
                    translator.translate("error.data.integrity"), NOTIFICATION_FAILURE);
        }
    }

    private void duplicateAction(ClickEvent event) {
        // TODO find a way to duplicate entities
    }

    private void exportAction(ClickEvent event) {
        // TODO export selected grid items
    }

    private void filterAction(ClickEvent event) {
        // TODO show generic filtering dialog and call customise method
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
                showNotification(translator.translate("title.save.error"),
                        translator.translate("msg.save.error", errors), NOTIFICATION_ERROR);

                customRollback();
            }
        } else {
            String errors = translator.translate("msg.validation.error").concat("<ul>");

            for (String vr : validationResult) {
                errors = errors.concat("<li>").concat(vr).concat("</li>");
            }

            errors += "</ul>";
            showNotification(translator.translate("title.save.error"), errors, NOTIFICATION_FAILURE);
        }
    }

    protected void refreshGrid() {
        // FIXME dynamic grid refresh is needed
        grid.refresh(svc.findAllDTO(getEntityClass(), getDTOClass(),
                PredicateFactory.createPredicate(getEntityClass(), filters), getLocale()));
    }

    private void createGrid() {
        grid = new DTOGrid<>(svc, getEntityClass(), getDTOClass())
                .withFilterRow(getDTOClass())
                .withSelectionListeners(this::onSelectItem);
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
        editorForm.setParentLayout(this);
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

}
