package it.ldsoftware.primavera.vaadin.dialogs;

import com.vaadin.data.util.converter.Converter;
import com.vaadin.event.ItemClickEvent;
import com.vaadin.event.SelectionEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.renderers.Renderer;
import it.ldsoftware.primavera.dto.base.BaseDTO;
import it.ldsoftware.primavera.entities.base.BaseEntity;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.services.interfaces.DatabaseService;
import it.ldsoftware.primavera.vaadin.components.DTOGrid;
import it.ldsoftware.primavera.vaadin.listeners.CancelHandler;
import it.ldsoftware.primavera.vaadin.listeners.SelectionHandler;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by luca on 20/04/16.
 * Basic dialog that shows a list of DTO and allows to select one or more
 */
public class SelectDialog<E extends BaseEntity, D extends BaseDTO<E>> extends AbstractDialog {

    private DTOGrid<E, D> grid;

    private boolean doubleClickToSelect = false;

    private SelectionHandler<D> handler;
    private CancelHandler cancel;

    private D tmpSelection;

    public SelectDialog(List<D> dtoList, Class<D> dcLass, LocalizationService service) {
        super(service);

        grid = new DTOGrid<>(dtoList, dcLass).withLocalizationService(service).withFilterRow(dcLass);
        commonInit();
    }

    public SelectDialog(DatabaseService svc, Class<E> eClass, Class<D> dClass, LocalizationService msg) {
        super(msg);

        grid = new DTOGrid<>(svc, eClass, dClass).withLocalizationService(msg).withFilterRow(dClass);
        commonInit();
    }

    public SelectDialog<E, D> withDoubleClickSelection() {
        if (!grid.isMultiSelection()) {
            doubleClickToSelect = true;
            grid.addItemClickListener(this::selectOnDoubleClick);
        }
        return this;
    }

    public SelectDialog<E, D> withSelectionHandler(SelectionHandler<D> handler) {
        this.handler = handler;
        return this;
    }

    public SelectDialog<E, D> withoutColumns(Object... columnsToRemove) {
        grid.removeColumns(columnsToRemove);
        return this;
    }

    public SelectDialog<E, D> withColumnOrder(Object... columns) {
        grid.setColumnOrder(columns);
        return this;
    }

    public <T> SelectDialog<E, D> withColumnRenderer(String column, Renderer<T> renderer, Converter<? extends T, ?> converter) {
        grid.getColumn(column).setRenderer(renderer, converter);
        return this;
    }

    public SelectDialog<E, D> withColumnRenderer(String column, Renderer<?> renderer) {
        grid.getColumn(column).setRenderer(renderer);
        return this;
    }

    public SelectDialog<E, D> withColumnConverter(String column, Converter<?, ?> converter) {
        grid.getColumn(column).setConverter(converter);
        return this;
    }

    /**
     * Enables multiselection of the elements. This is not done if the grid has
     * the doubleclick-to-select feature enabled.
     *
     * @return the SelectDialog for fluent notation
     */
    public SelectDialog<E, D> withMultiSelect() {
        if (!doubleClickToSelect)
            grid.toggleMultiSelection();

        return this;
    }

    public SelectDialog<E, D> withCancelHandler(CancelHandler handler) {
        cancel = handler;
        return this;
    }

    private void commonInit() {
        addComponent(grid);
        addComponent(getToolBar());
        getBtnOk().addClickListener(this::selectItems);
        getBtnCancel().addClickListener(this::cancel);
        grid.addSelectionListener(this::onSelectItem);
    }

    @SuppressWarnings("unchecked")
    private void selectItems(ClickEvent event) {
        List<D> data = new ArrayList<>();

        if (doubleClickToSelect && tmpSelection != null)
            data.add(tmpSelection);
        else
            grid.getSelectedRows().stream().map(r -> (D) r).forEach(data::add);

        handler.handleSelection(data);
    }

    @SuppressWarnings("unchecked")
    private void selectOnDoubleClick(ItemClickEvent event) {
        if (event.isDoubleClick()) {
            tmpSelection = (D) event.getItemId();
            this.selectItems(null);
        }
    }

    private void onSelectItem(SelectionEvent event) {
        getBtnOk().setEnabled(!event.getSelected().isEmpty());
    }

    private void cancel(ClickEvent event) {
        if (cancel != null)
            cancel.cancelEvent();
    }
}
