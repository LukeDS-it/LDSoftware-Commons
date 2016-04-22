package it.ldsoftware.commons.vaadin.components;

import com.vaadin.data.Container.Filterable;
import com.vaadin.data.Container.Indexed;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.util.converter.Converter;
import com.vaadin.data.util.filter.SimpleStringFilter;
import com.vaadin.event.FieldEvents.TextChangeEvent;
import com.vaadin.event.FieldEvents.TextChangeListener;
import com.vaadin.event.ItemClickEvent.ItemClickListener;
import com.vaadin.event.SelectionEvent.SelectionListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.Renderer;
import de.datenhahn.vaadin.componentrenderer.ComponentRenderer;
import it.ldsoftware.commons.dto.base.BaseDTO;
import it.ldsoftware.commons.entities.base.BaseEntity;
import it.ldsoftware.commons.i18n.LocalizationService;
import it.ldsoftware.commons.query.Request;
import it.ldsoftware.commons.services.interfaces.DatabaseService;
import it.ldsoftware.commons.vaadin.data.FIlterableLazyListContainer;
import it.ldsoftware.commons.vaadin.data.FilterableLazyList;
import it.ldsoftware.commons.vaadin.listeners.DeleteListener;
import it.ldsoftware.commons.vaadin.listeners.EditListener;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.MessageSource;
import org.springframework.data.domain.PageRequest;
import org.vaadin.dialogs.ConfirmDialog;
import org.vaadin.viritin.ListContainer;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import static com.vaadin.ui.AbstractSelect.ItemCaptionMode.EXPLICIT_DEFAULTS_ID;
import static com.vaadin.ui.Grid.SelectionMode.MULTI;
import static com.vaadin.ui.Grid.SelectionMode.SINGLE;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_BORDERLESS;
import static com.vaadin.ui.themes.ValoTheme.TEXTFIELD_TINY;
import static it.ldsoftware.commons.i18n.CommonLabels.*;
import static it.ldsoftware.commons.query.PredicateFactory.createPredicate;
import static it.ldsoftware.commons.util.ReflectionUtil.getPropertyFromMethod;
import static it.ldsoftware.commons.vaadin.theme.MetricConstants.COLUMN_XS;
import static org.springframework.data.domain.Sort.Direction.ASC;
import static org.springframework.data.domain.Sort.Direction.DESC;

/**
 * @author luca
 *         Created by luca on 19/04/16.
 *         This is an extension of the Vaadin Grid component, that displays DTO objects based on
 *         the database entities.
 *         It can work both in a lazy way, given the service to lookup the entities, or in
 *         eager way, given the list of the entities/dtos to display.
 */
public class DTOGrid<E extends BaseEntity, D extends BaseDTO<E>> extends Grid {

    private static final int PAGESIZE = 15;
    public static final String COLUMN_DELETE = "delete", COLUMN_EDIT = "edit";

    private boolean multiSelection = false;

    private GeneratedPropertyContainer gpc;

    private DeleteListener<D> deleteListener;
    private EditListener<D> editListener;

    private LocalizationService locSvc;

    public DTOGrid(DatabaseService svc, Class<E> eClass, Class<D> dClass) {
        super();
        Locale l = UI.getCurrent().getLocale();
        FilterableLazyList<D> entities = FilterableLazyList.of(
                req -> svc.findAllDTO(eClass, dClass, createPredicate(eClass, req.getFilters()), l),
                req -> svc.countProvider(eClass, createPredicate(eClass, req.getFilters())),
                PAGESIZE
        );
        ListContainer<D> container = new FIlterableLazyListContainer<>(dClass, entities);
        initGPC(container);
        commonSettings();
    }

    public DTOGrid(List<D> entities, Class<D> dClass) {
        super();
        BeanItemContainer<D> container = new BeanItemContainer<>(dClass, entities);
        initGPC(container);
        refresh(entities);
        commonSettings();
    }

    /**
     * Refreshes the current view with given entities
     *
     * @param entities the entities which will overwrite the current ones
     */
    @SuppressWarnings("unchecked")
    public void refresh(List<D> entities) {
        GeneratedPropertyContainer cont = (GeneratedPropertyContainer) getContainerDataSource();
        Indexed ds = cont.getWrappedContainer();

        if (ds instanceof ListContainer)
            ((ListContainer<D>) ds).setCollection(entities);
        if (ds instanceof BeanItemContainer) {
            ((BeanItemContainer<D>) ds).removeAllItems();
            ((BeanItemContainer<D>) ds).addAll(entities);
        }
    }

    /**
     * Adds a Message Source to the grid, and uses it to
     * translate the captions of the columns. The messages.properties file must contain
     * the values as column.xxx
     *
     * @param msg the @{link MessageSource} that contains the messages
     * @return the grid itself for method chaining
     */
    public DTOGrid<E, D> withMessageSource(MessageSource msg) {
        locSvc = new LocalizationService(msg, UI.getCurrent().getLocale());
        translateColumns();
        return this;
    }

    public DTOGrid<E, D> withLocalizationService(LocalizationService svc) {
        locSvc = svc;
        translateColumns();
        return this;
    }

    private void translateColumns() {
        getColumns()
                .stream()
                .forEach(column -> column.setHeaderCaption(
                        locSvc.translate("column_".concat(column.getPropertyId().toString())))
                );
    }

    /**
     * Creates the grid without the given columns
     *
     * @param columns one or more properties that correspond to the unwanted columns
     * @return the grid itself for method chaining
     */
    public DTOGrid<E, D> withoutColumns(Object... columns) {
        removeColumns(columns);
        return this;
    }

    /**
     * Appends a header row to the EditorGrid, adding default editors for
     * strings and booleans
     *
     * @param dClass the DTO class, used to get the fields
     * @return the grid itself, for use in fluent notation
     */
    public DTOGrid<E, D> withFilterRow(Class<D> dClass) {
        HeaderRow filterRow = appendHeaderRow();

        Method[] methods = dClass.getDeclaredMethods();
        for (Method m : methods) {
            if (m.getName().startsWith("get") || m.getName().startsWith("is")) {
                String property = getPropertyFromMethod(m.getName());
                Component filter = getColumnFilter(property, dClass);
                filterRow.getCell(property).setComponent(filter);
                filterRow.getCell(property).setStyleName("filter-header");
            }
        }

        return this;
    }

    /**
     * Removes the given columns from the grid
     *
     * @param columns list of columns to remove
     */
    public void removeColumns(Object... columns) {
        Arrays.stream(columns).forEach(this::removeColumn);
    }

    /**
     * Adds multiple selection listeners to the grid at once
     *
     * @param listeners the listeners to add
     * @return the grid itself for method chaining
     */
    public DTOGrid<E, D> withSelectionListeners(SelectionListener... listeners) {
        Arrays.stream(listeners).forEach(this::addSelectionListener);
        return this;
    }

    public DTOGrid<E, D> withClickListeners(ItemClickListener... listeners) {
        Arrays.stream(listeners).forEach(this::addItemClickListener);
        return this;
    }

    public DTOGrid<E, D> withColumnOrder(Object... properties) {
        this.setColumnOrder(properties);
        return this;
    }

    public DTOGrid<E, D> withHeight(float height, Unit unit) {
        setHeight(height, unit);
        return this;
    }

    public DTOGrid<E, D> withWidth(float width, Unit unit) {
        setWidth(width, unit);
        return this;
    }

    public DTOGrid<E, D> withStyleNames(String... styles) {
        Arrays.stream(styles).forEach(this::addStyleName);
        return this;
    }

    public DTOGrid<E, D> withCaption(String caption) {
        setCaption(caption);
        return this;
    }

    public <T> DTOGrid<E, D> withColumnRenderer(String column, Renderer<T> renderer, Converter<? extends T, ?> converter) {
        getColumn(column).setRenderer(renderer, converter);
        return this;
    }

    /**
     * Adds a column with the delete button.
     *
     * @param l the action that the delete button will perform
     */
    @SuppressWarnings("unchecked")
    public void addDeleteColumn(DeleteListener<D> l) {
        this.deleteListener = l;

        gpc.addGeneratedProperty(COLUMN_DELETE, new PropertyValueGenerator<Component>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getValue(Item item, Object itemId, Object propertyId) {
                return new MButton(FontAwesome.TRASH_O).withListener(e -> deleteAction((D) item))
                        .withStyleName(BUTTON_BORDERLESS);
            }

            @Override
            public Class<Component> getType() {
                return Component.class;
            }
        });

        setColumnOrder(COLUMN_DELETE);
        getColumn(COLUMN_DELETE).setRenderer(new ComponentRenderer()).setWidth(COLUMN_XS).setHeaderCaption("");
        removeColumn("CSVFields");
        removeColumn("id");
    }

    @SuppressWarnings("unchecked")
    public void addEditColumn(EditListener<D> l) {
        editListener = l;

        gpc.addGeneratedProperty(COLUMN_EDIT, new PropertyValueGenerator<Component>() {
            private static final long serialVersionUID = 1L;

            @Override
            public Component getValue(Item item, Object itemId, Object propertyId) {
                return new MButton(FontAwesome.EDIT).withListener(e -> editAction((D) itemId))
                        .withStyleName(BUTTON_BORDERLESS);
            }

            @Override
            public Class<Component> getType() {
                return Component.class;
            }
        });

        setColumnOrder(COLUMN_EDIT);
        getColumn(COLUMN_EDIT).setRenderer(new ComponentRenderer()).setWidth(COLUMN_XS).setHeaderCaption("");
        removeColumn("CSVFields");
        removeColumn("id");

    }

    public DTOGrid<E, D> withDeleteColumn(DeleteListener<D> listener) {
        addDeleteColumn(listener);
        return this;
    }

    public DTOGrid<E, D> withEditColumn(EditListener<D> listener) {
        addEditColumn(listener);
        return this;
    }

    /**
     * Toggles multiselection mode on and off.
     */
    public void toggleMultiSelection() {
        if (!multiSelection) {
            setSelectionMode(MULTI);
        } else {
            setSelectionMode(SINGLE);
        }
        multiSelection = !multiSelection;
    }

    /**
     * Tries to select the first element of the grid
     */
    public void selectFirstElement() {
        try {
            select(getContainerDataSource().getIdByIndex(0));
        } catch (IndexOutOfBoundsException ignored) {

        }
    }

    public boolean isMultiSelection() {
        return multiSelection;
    }

    public void setMultiSelection(boolean multiSelection) {
        this.multiSelection = multiSelection;
    }

    private PageRequest getPageRequest(Request request, Class<E> eClass) {
        String sortProperty = "id";
        try {
            eClass.getDeclaredField(request.getSort().getProperty());
            sortProperty = request.getSort().getProperty();
        } catch (NoSuchFieldException | NullPointerException ignored) {

        }
        return new PageRequest(request.getFirstRow() / PAGESIZE, PAGESIZE, request.getSort().isSortAscending() ? ASC : DESC, sortProperty);
        // TODO handle nested collection properties
    }

    private void commonSettings() {
        setSizeFull();
        removeColumn("id");
    }

    private void initGPC(Indexed container) {
        gpc = new GeneratedPropertyContainer(container);
        setContainerDataSource(gpc);
    }

    private Component getColumnFilter(String property, Class<D> clazz) {
        try {
            clazz.getMethod("is" + StringUtils.capitalize(property));

            ComboBox cb = new ComboBox();
            cb.setStyleName(TEXTFIELD_TINY);
            cb.setWidth("100%");
            cb.setItemCaptionMode(EXPLICIT_DEFAULTS_ID);
            cb.setInputPrompt(locSvc.translate(TXT_FILTER));
            cb.addItem("true");
            cb.addItem("false");
            cb.setItemCaption("true", locSvc.translate(YES));
            cb.setItemCaption("false", locSvc.translate(NO));

            cb.addValueChangeListener(new ValueChangeListener() {

                private static final long serialVersionUID = 1L;
                SimpleStringFilter filter = null;

                @Override
                public void valueChange(ValueChangeEvent event) {
                    Filterable f = (Filterable) getContainerDataSource();

                    if (filter != null)
                        f.removeContainerFilter(filter);

                    String fS = "";
                    if (event.getProperty().getValue() != null)
                        fS = event.getProperty().getValue().toString();

                    filter = new SimpleStringFilter(property, fS, true, true);
                    f.addContainerFilter(filter);

                    cancelEditor();
                }
            });

            return cb;
        } catch (SecurityException | NoSuchMethodException ignored) {
            TextField tf = new MTextField().withWidth("100%");
            tf.addStyleName(TEXTFIELD_TINY);
            tf.setInputPrompt(locSvc.translate(TXT_FILTER));

            tf.addTextChangeListener(new TextChangeListener() {
                private static final long serialVersionUID = 1L;
                SimpleStringFilter filter = null;

                @Override
                public void textChange(TextChangeEvent event) {
                    Filterable f = (Filterable) getContainerDataSource();

                    if (filter != null)
                        f.removeContainerFilter(filter);

                    if (StringUtils.isNotEmpty(event.getText())) {
                        filter = new SimpleStringFilter(property, event.getText(), true, true);
                        f.addContainerFilter(filter);
                    }
                    cancelEditor();
                }
            });
            return tf;
        }
    }

    @SuppressWarnings("unchecked")
    private void deleteAction(D item) {
        ConfirmDialog.show(getUI(), locSvc.translate(TITLE_CONFIRM_DELETE),
                locSvc.translate(MSG_CONFIRM_DELETE_SINGLE), locSvc.translate(YES),
                locSvc.translate(NO), question -> {
                    if (question.isConfirmed()) {
                        List<D> l = new ArrayList<>();
                        l.add(item);
                        deleteListener.performDelete(l);
                    }
                });
    }

    private void editAction(D item) {
        List<D> l = new ArrayList<>();
        l.add(item);
        editListener.performEdit(l);
    }
}
