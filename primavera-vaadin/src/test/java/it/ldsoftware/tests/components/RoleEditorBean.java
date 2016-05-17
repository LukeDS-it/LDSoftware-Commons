package it.ldsoftware.tests.components;

import com.vaadin.spring.annotation.UIScope;
import it.ldsoftware.primavera.vaadin.editors.security.RoleEditor;
import it.ldsoftware.primavera.vaadin.grouping.GuiGear;

import static it.ldsoftware.primavera.i18n.CommonLabels.EDITOR_ROLES;
import static it.ldsoftware.tests.ui.TestMainUI.GROUP_SECURITY;

/**
 * Created by luca on 17/05/16.
 * Bean that instantiates the role editor.
 *
 * Must have UI scope because there can be only one instance of it
 * for each user.
 */
@UIScope
@GuiGear(caption = EDITOR_ROLES, group = GROUP_SECURITY)
public class RoleEditorBean extends RoleEditor {
}
