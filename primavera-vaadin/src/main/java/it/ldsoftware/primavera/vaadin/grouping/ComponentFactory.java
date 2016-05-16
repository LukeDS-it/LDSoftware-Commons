package it.ldsoftware.primavera.vaadin.grouping;

import com.vaadin.ui.UI;
import it.ldsoftware.primavera.i18n.LocalizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Collection;
import java.util.Locale;

import static it.ldsoftware.primavera.util.UserUtil.isCurrentUserEnabled;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 16/05/16.
 *
 */
@Service
public class ComponentFactory {

    @Autowired
    ApplicationContext context;

    @Autowired
    LocalizationService msg;

    public Collection<Gear> getGears(String group) {
        Locale l = UI.getCurrent().getLocale();
        msg.setLocale(l);

        return Arrays.stream(context.getBeanNamesForAnnotation(GuiGear.class))
                .filter(elName -> isInGroup(elName, group))
                .map(this::makeGroupElement)
                .sorted()
                .filter(this::isTileVisible)
                .collect(toList());
    }

    private boolean isInGroup(String element, String group) {
        GuiGear g = context.findAnnotationOnBean(element, GuiGear.class);
        return g.group().equals(group);
    }

    private Gear makeGroupElement(String element) {
        GuiGear g = context.findAnnotationOnBean(element, GuiGear.class);
        return new Gear().withCaption(msg.translate(g.caption())).withBeanName(element)
                .withCss(g.css()).withGroup(g.group()).withRole(g.role());
    }

    private boolean isTileVisible(Gear element) {
        return isCurrentUserEnabled(element.getRole());
    }

}
