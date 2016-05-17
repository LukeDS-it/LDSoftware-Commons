package it.ldsoftware.tests.components;

import com.vaadin.spring.annotation.UIScope;
import it.ldsoftware.primavera.vaadin.editors.security.GroupEditor;
import it.ldsoftware.primavera.vaadin.grouping.GuiGear;

import static it.ldsoftware.primavera.i18n.CommonLabels.EDITOR_GROUPS;
import static it.ldsoftware.tests.ui.TestMainUI.GROUP_SECURITY;

/**
 * Created by luca on 17/05/16.
 *
 */
@UIScope
@GuiGear(caption = EDITOR_GROUPS, group = GROUP_SECURITY)
public class GroupEditorBean extends GroupEditor {
}
