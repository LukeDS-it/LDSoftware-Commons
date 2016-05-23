package it.ldsoftware.primavera.vaadin.dialogs;

import com.vaadin.data.Container.Filter;
import com.vaadin.ui.Button.ClickEvent;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.vaadin.data.FilterConsumer;

import java.util.Collection;

/**
 * Created by luca on 23/05/16.
 *
 * Abstraction of the filtering dialog that will be used by the
 * editors to filter the results in a more extended way
 */
public abstract class AbstractFilterDialog extends AbstractDialog {

    private FilterConsumer filterConsumer;

    public AbstractFilterDialog(LocalizationService service) {
        super(service);

    }

    public AbstractFilterDialog withFilterConsumer(FilterConsumer consumer) {
        filterConsumer = consumer;
        return this;
    }

    @Override
    public void pressOk(ClickEvent event) {
        super.pressOk(event);
        filterConsumer.useFilters(buildFilters());
    }

    abstract Collection<Filter> buildFilters();
}
