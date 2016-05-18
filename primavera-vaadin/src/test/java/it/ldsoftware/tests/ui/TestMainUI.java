package it.ldsoftware.tests.ui;

import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Widgetset;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.Component;
import com.vaadin.ui.TabSheet;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import it.ldsoftware.primavera.vaadin.grouping.ComponentFactory;
import it.ldsoftware.primavera.vaadin.grouping.Gear;
import org.springframework.beans.factory.annotation.Autowired;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MCssLayout;

/**
 * Created by luca on 16/05/16.
 * Test UI for all the components in the primavera vaadin framework
 */
@SpringUI
@Theme("ldsmetro")
@Widgetset("it.ldsoftware.primavera.vaadin.widgetset.PrimaveraWidgetset")
public class TestMainUI extends UI {

    public static final String GROUP_SECURITY = "security", GROUP_PEOPLE = "people";

    @Autowired
    ComponentFactory componentFactory;

    private TabSheet tabSheet;

    @Override
    protected void init(VaadinRequest request) {
        tabSheet = new TabSheet();
        tabSheet.setSizeFull();

        VerticalLayout secGroup = new VerticalLayout();
        componentFactory.getGears(GROUP_PEOPLE).stream().map(this::createButton).forEach(secGroup::addComponent);

        VerticalLayout peoGroup = new VerticalLayout();
        componentFactory.getGears(GROUP_SECURITY).stream().map(this::createButton).forEach(peoGroup::addComponent);

        tabSheet.addTab(new MCssLayout(secGroup, peoGroup).withFullHeight().withFullWidth(), "Main");

        setContent(tabSheet);
    }

    private Component createButton(Gear gear) {
        return new MButton(gear.getCaption())
                .withStyleName(gear.getCss())
                .withListener(event -> {
                    Object o = gear.getBean();
                    if (o instanceof Component) {
                        tabSheet.addTab((Component) o, gear.getCaption());
                    }
                });
    }

}
