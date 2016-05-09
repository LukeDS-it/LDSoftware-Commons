package it.ldsoftware.primavera.vaadin.dialogs;

import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ListSelect;
import com.vaadin.ui.UI;
import com.vaadin.ui.Window;
import it.ldsoftware.primavera.i18n.LocalizationService;
import it.ldsoftware.primavera.vaadin.components.LiveTile;
import it.ldsoftware.primavera.vaadin.components.LiveTile.TileColor;
import it.ldsoftware.primavera.vaadin.listeners.CancelHandler;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.io.IOException;
import java.util.Arrays;
import java.util.Locale;

import static com.vaadin.ui.AbstractSelect.ItemCaptionMode.EXPLICIT;
import static com.vaadin.ui.Alignment.MIDDLE_CENTER;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_PRIMARY;
import static it.ldsoftware.primavera.i18n.CommonLabels.CANCEL;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 20/04/16.
 * This dialog is used to create the configuration for a @{link LiveTile}
 */
public class TileConfigurator extends MVerticalLayout {

    private static final long serialVersionUID = 1L;

    private LiveTile mockup;
    private ListSelect tileColor, tileImage;
    private Window popup;

    private SelectionHandler handler;
    private CancelHandler cancel;

    private LocalizationService svc;

    public TileConfigurator(LocalizationService svc, TileColor previousColor, String previousImage) {
        this.svc = svc;
        Locale l = UI.getCurrent().getLocale();

        mockup = new LiveTile().withCaption(svc.translate("label.dummy.tile")).withColor(previousColor)
                .withImage("../" + previousImage);

        tileColor = new ListSelect(svc.translate("lst.choose.color"));
        tileColor.addItems(Arrays.stream(TileColor.values()).collect(toList()));
        tileColor.select(previousColor);
        tileColor.addValueChangeListener(this::changeTileColor);

        tileImage = new ListSelect(svc.translate("lst.choose.image"));
        PathMatchingResourcePatternResolver rpr = new PathMatchingResourcePatternResolver();
        try {
            tileImage.addItems(Arrays.stream(rpr.getResources("classpath*:VAADIN/themes/*/images/icons/*.png"))
                    .collect(toList()));

        } catch (IOException e) {
            e.printStackTrace();
        }
        tileImage.setItemCaptionMode(EXPLICIT);
        tileImage.getItemIds().forEach(id -> {
            FileSystemResource fsr = (FileSystemResource) id;
            tileImage.setItemCaption(id, fsr.getFilename());
            if (fsr.getPath().contains(previousImage))
                tileImage.select(id);
        });
        tileImage.select(previousImage);
        tileImage.addValueChangeListener(this::changeTileImage);

        addComponents(
                new MHorizontalLayout(mockup, tileColor, tileImage).withMargin(false).withSpacing(true)
                        .withAlign(mockup, MIDDLE_CENTER),
                new MHorizontalLayout(new MButton("OK").withStyleName(BUTTON_PRIMARY).withListener(this::btnOk),
                        new MButton(svc.translate(CANCEL)).withListener(this::btnCancel)));
    }

    public TileConfigurator withSaveHandler(SelectionHandler handler) {
        this.handler = handler;
        return this;
    }

    public TileConfigurator withCancelHandler(CancelHandler handler) {
        cancel = handler;
        return this;
    }

    public Window popup() {
        popup = new Window(svc.translate("msg.create.tile"), this);
        popup.setModal(true);
        UI.getCurrent().addWindow(popup);
        return popup;
    }

    private void changeTileColor(ValueChangeEvent event) {
        mockup.withColor((TileColor) event.getProperty().getValue());
    }

    private void changeTileImage(ValueChangeEvent event) {
        String path = ((FileSystemResource) event.getProperty().getValue()).getPath();
        mockup.withImage(".." + path.substring(path.indexOf("/themes") + "/themes".length()));
    }

    private void btnOk(ClickEvent event) {
        String path = ((FileSystemResource) tileImage.getValue()).getPath();
        path = path.substring(path.indexOf("/themes/") + "/themes/".length());
        handler.saveTileConfiguration((TileColor) tileColor.getValue(), path);
        closePopup();
    }

    private void btnCancel(ClickEvent event) {
        if (cancel != null)
            cancel.cancelEvent();
        if (popup != null)
            closePopup();
    }

    private void closePopup() {
        if (popup != null)
            UI.getCurrent().removeWindow(popup);
    }

    public interface SelectionHandler {
        void saveTileConfiguration(TileColor color, String image);
    }
}
