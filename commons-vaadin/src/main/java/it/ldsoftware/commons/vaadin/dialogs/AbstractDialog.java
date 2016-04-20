package it.ldsoftware.commons.vaadin.dialogs;

import com.vaadin.ui.*;
import com.vaadin.ui.Button.ClickEvent;
import it.ldsoftware.commons.i18n.CommonLabels;
import it.ldsoftware.commons.i18n.LocalizationService;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;

import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_PRIMARY;
import static it.ldsoftware.commons.i18n.CommonLabels.CANCEL;

/**
 * Created by luca on 20/04/16.
 * Basic functions that may serve in creating a dialog
 */
public abstract class AbstractDialog extends VerticalLayout {

    private MButton btnOk, btnCancel;
    private Window popup;

    private LocalizationService translator;

    public AbstractDialog(LocalizationService service) {
        translator = service;

        initContent();
    }

    public Component getToolBar() {
        return new MHorizontalLayout(btnOk, btnCancel).withMargin(false);
    }

    private void initContent() {
        btnOk = new MButton("Ok").withStyleName(BUTTON_PRIMARY).withListener(this::pressOk);
        btnCancel = new MButton(translator.translate(CANCEL)).withListener(this::pressCancel);

        setMargin(true);
        setSpacing(true);
        setWidth(500, PIXELS);
    }

    private void pressOk(ClickEvent event) {
        closeWindow();
    }

    void pressCancel(ClickEvent event) {
        closeWindow();
    }

    private void closeWindow() {
        if (popup != null)
            UI.getCurrent().removeWindow(popup);
    }

    public LocalizationService getTranslator() {
        return translator;
    }

    public MButton getBtnOk() {
        return btnOk;
    }

    public void setBtnOk(MButton btnOk) {
        this.btnOk = btnOk;
    }

    public MButton getBtnCancel() {
        return btnCancel;
    }

    public void setBtnCancel(MButton btnCancel) {
        this.btnCancel = btnCancel;
    }

    public Window popup(String title) {
        popup = new Window(translator.translate(title), this);
        popup.setModal(true);
        UI.getCurrent().addWindow(popup);
        popup.center();
        return popup;
    }
}
