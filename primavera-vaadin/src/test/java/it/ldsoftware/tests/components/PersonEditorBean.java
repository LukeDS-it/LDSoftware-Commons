package it.ldsoftware.tests.components;

import com.vaadin.spring.annotation.UIScope;
import it.ldsoftware.primavera.vaadin.editors.people.PersonEditor;
import it.ldsoftware.primavera.vaadin.grouping.GuiGear;

import static it.ldsoftware.primavera.i18n.CommonLabels.EDITOR_PEOPLE;
import static it.ldsoftware.tests.ui.TestMainUI.GROUP_PEOPLE;

/**
 * Created by luca on 17/05/16.
 *
 */
@UIScope
@GuiGear(caption = EDITOR_PEOPLE, group = GROUP_PEOPLE)
public class PersonEditorBean extends PersonEditor {
}
