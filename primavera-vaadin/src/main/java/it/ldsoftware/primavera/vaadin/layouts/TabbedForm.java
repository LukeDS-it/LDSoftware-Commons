package it.ldsoftware.primavera.vaadin.layouts;

import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.VerticalLayout;
import it.ldsoftware.primavera.entities.base.BaseEntity;
import org.vaadin.viritin.layouts.MVerticalLayout;

import static com.vaadin.ui.themes.ValoTheme.TABSHEET_FRAMED;

/**
 * Created by luca on 20/04/16.
 * This form has tabs :)
 * <p>
 * It has a pre-made tab called "General", which can be customized,
 * other tabs can be added at will
 */
public abstract class TabbedForm<E extends BaseEntity> extends AbstractEditorForm<E> {
    private TabSheet tabs;
    private VerticalLayout generalTab;

    public TabbedForm() {
        tabs = new TabSheet();
        tabs.setStyleName(TABSHEET_FRAMED);
        tabs.setSizeFull();

        createGeneralTab();
        addComponent(tabs);
        addOtherTabs();
    }

    private void createGeneralTab() {
        generalTab = new MVerticalLayout().withMargin(true).withFullWidth();
        addGeneralContent(generalTab);
        addTab(generalTab, getTranslator().translate("tab.general"));
    }

    public void addTab(Component component, String caption) {
        tabs.addTab(component, caption);
    }

    public void addToGeneral(Component component) {
        generalTab.addComponent(component);
    }

    /**
     * Implement this method if you wish to add more tabs to the
     * form. Call method addTab(Component, String) to effectively
     * add the tabs.
     */
    public void addOtherTabs() {
    }

    public abstract void addGeneralContent(VerticalLayout generalTab);
}
