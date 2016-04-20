package it.ldsoftware.commons.vaadin.dialogs;

import com.vaadin.ui.Label;
import com.vaadin.ui.Panel;
import com.vaadin.ui.UI;
import it.ldsoftware.commons.i18n.LocalizationService;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Locale;

import static com.vaadin.shared.ui.label.ContentMode.HTML;

/**
 * Created by luca on 20/04/16.
 * This dialog shows the "terms of service" file that is located in the
 * project's "static/termsOfService.txt" file.
 * <p>
 * The TOS can be internationalized using the usual _lang convention
 * of .properties files
 */
public class TermsOfServiceDialog extends AbstractDialog {

    private final static String TOS_FILE = "static/termsOfService%.txt";

    public TermsOfServiceDialog(LocalizationService service) {
        super(service);

        Panel panel = new Panel();
        panel.setSizeFull();
        Label label = new Label();
        label.setContentMode(HTML);
        panel.setContent(label);

        addComponents(panel, getToolBar());
        setExpandRatio(panel, 1.0f);

        loadTOS(label);
    }

    private void loadTOS(Label label) {
        Locale l = UI.getCurrent().getLocale();
        ClassLoader cl = getClass().getClassLoader();

        String termsOfService = "No TOS file...";

        String variant = TOS_FILE.replace("%", "_" + l.getLanguage().toLowerCase());
        URL url = cl.getResource(variant);
        if (url == null) {
            variant = TOS_FILE.replace("%", "");
            url = cl.getResource(variant);
        }
        try {
            if (url != null) {
                File tosFile = new File(url.getFile().replaceAll("\\%20", " "));
                termsOfService = FileUtils.readFileToString(tosFile);
                termsOfService = termsOfService.replaceAll("\r\n|\n", "<br />");
            }
        } catch (IOException ignored) {

        }

        label.setValue(termsOfService);
    }
}
