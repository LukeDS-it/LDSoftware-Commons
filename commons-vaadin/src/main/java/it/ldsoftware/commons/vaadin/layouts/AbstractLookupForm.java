package it.ldsoftware.commons.vaadin.layouts;

import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.data.util.GeneratedPropertyContainer;
import com.vaadin.data.util.PropertyValueGenerator;
import com.vaadin.data.validator.NullValidator;
import com.vaadin.ui.*;
import com.vaadin.ui.renderers.ImageRenderer;
import de.datenhahn.vaadin.componentrenderer.ComponentRenderer;
import it.ldsoftware.commons.entities.base.Lookup;
import it.ldsoftware.commons.entities.base.LookupTranslation;
import it.ldsoftware.commons.entities.lang.Translation;
import it.ldsoftware.commons.i18n.CommonLabels;
import it.ldsoftware.commons.vaadin.data.util.converters.FlagConverter;
import it.ldsoftware.commons.vaadin.util.SupportedLanguage;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MHorizontalLayout;
import org.vaadin.viritin.layouts.MVerticalLayout;

import javax.validation.Validator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;

import static com.vaadin.server.FontAwesome.TRASH_O;
import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;
import static com.vaadin.ui.AbstractSelect.ItemCaptionMode.PROPERTY;
import static com.vaadin.ui.Alignment.MIDDLE_LEFT;
import static com.vaadin.ui.themes.ValoTheme.BUTTON_BORDERLESS;
import static com.vaadin.ui.themes.ValoTheme.NOTIFICATION_ERROR;
import static com.vaadin.ui.themes.ValoTheme.NOTIFICATION_WARNING;
import static it.ldsoftware.commons.i18n.CommonLabels.*;
import static it.ldsoftware.commons.vaadin.theme.MetricConstants.COLUMN_XS;
import static it.ldsoftware.commons.vaadin.theme.MetricConstants.FIELD_WIDTH;
import static it.ldsoftware.commons.vaadin.util.NotificationBuilder.showNotification;
import static it.ldsoftware.commons.vaadin.util.SupportedLanguage.supportedLanguages;
import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;

/**
 * Created by luca on 20/04/16.
 * This class is the representation of a form for a lookup entity.
 * Can be used as-is for simple lookups or extended for more complex
 * lookups
 */
public abstract class AbstractLookupForm<L extends Lookup<T>, T extends LookupTranslation> extends TabbedForm<L> {
    private TextField code, ownLocale;
    BeanItemContainer<T> bic;

    public AbstractLookupForm() {
        super();
        createTranslationsTab();
    }

    @Override
    public void addGeneralContent(VerticalLayout generalTab) {
        code = new MTextField(getTranslator().translate("txt.code")).withWidth(FIELD_WIDTH);
        code.addTextChangeListener(l -> signalChange());

        ownLocale = new MTextField(getTranslator().translate("txt.desc")).withWidth(FIELD_WIDTH);
        ownLocale.addTextChangeListener(l -> signalChange());
        ownLocale.setRequired(true);
        ownLocale.addValidator(new NullValidator("", false));

        generalTab.addComponent(code);
        generalTab.addComponent(ownLocale);
    }

    private void createTranslationsTab() {
        VerticalLayout tTab = new MVerticalLayout().withFullWidth().withMargin(true);

        Grid tGrid = new Grid();
        tGrid.setWidth(100, PERCENTAGE);
        tGrid.setHeight("200px");

        bic = new BeanItemContainer<>(LookupTranslation.class, new ArrayList<>());
        GeneratedPropertyContainer gpc = new GeneratedPropertyContainer(bic);

        gpc.addGeneratedProperty("delete", new PropertyValueGenerator<Component>() {

            private static final long serialVersionUID = 1L;

            @Override
            public Component getValue(Item item, Object itemId, Object propertyId) {
                return new MButton(TRASH_O).withListener(l -> deleteTranslation(itemId))
                        .withStyleName(BUTTON_BORDERLESS);
            }

            @Override
            public Class<Component> getType() {
                return Component.class;
            }
        });

        tGrid.setContainerDataSource(gpc);
        tGrid.setColumnOrder("delete", "lang", "desc");
        tGrid.getColumn("delete").setRenderer(new ComponentRenderer()).setWidth(COLUMN_XS).setHeaderCaption("");
        tGrid.getColumn("lang").setHeaderCaption(getTranslator().translate("label.language")).setWidth(70)
                .setRenderer(new ImageRenderer(), new FlagConverter());

        tGrid.getColumn("desc").setHeaderCaption(getTranslator().translate("label.description"));
        tGrid.removeColumn("id");
        tGrid.removeColumn("version");
        tGrid.setCellStyleGenerator(cell -> "lang".equals(cell.getPropertyId()) ? "" : null);

        tTab.addComponent(tGrid);

        Label label = new Label(getTranslator().translate("txt.add.desc"));
        TextField translation = new MTextField().withWidth("250px");
        ComboBox language = new ComboBox();

        Collection<SupportedLanguage> langs = stream(supportedLanguages)
                .map(l -> l.translate(getTranslator()))
                .sorted((c1, c2) -> c1.getLangName().compareTo(c2.getLangName())).collect(toList());

        BeanItemContainer<SupportedLanguage> cont = new BeanItemContainer<>(SupportedLanguage.class, langs);
        language.setContainerDataSource(cont);

        language.setItemCaptionMode(PROPERTY);
        language.setItemCaptionPropertyId("langName");
        language.setItemIconPropertyId("icon");

        language.getItemIds().forEach(id -> language.setItemIcon(id, ((SupportedLanguage) id).getIcon()));

        CheckBox defaultLang = new CheckBox(getTranslator().translate(CommonLabels.DEFAULT));
        Button insert = new Button(getTranslator().translate(ADD));

        insert.addClickListener(l -> {
            String transl = translation.getValue();

            try {
                String lang = language.getValue().toString();
                getBean().addTranslation(lang, createTranslation(transl, lang));
                if (defaultLang.getValue())
                    getBean().setDefaultLang(lang);
                signalChange();
                translation.focus();
                translation.setValue("");
                language.setValue(language.getNullSelectionItemId());
                getParentLayout().saveAction(null);
            } catch (IllegalArgumentException e) {
                showNotification(getTranslator().translate(TITLE_GENERIC_WARNING),
                        getTranslator().translate(e.getMessage()), NOTIFICATION_WARNING);
            } catch (NullPointerException e) {
                showNotification(getTranslator().translate(TITLE_SAVE_ERROR),
                        getTranslator().translate(MSG_SAVE_ERROR, getTranslator().translate(MSG_INS_ERROR)),
                        NOTIFICATION_ERROR);
            }
        });

        HorizontalLayout insertTranslation = new MHorizontalLayout()
                .with(label, translation, language, defaultLang, insert).withAlign(label, MIDDLE_LEFT)
                .withAlign(defaultLang, MIDDLE_LEFT);

        tTab.addComponent(insertTranslation);

        addTab(tTab, getTranslator().translate("tab.translations"));
    }

    @Override
    public void selectFirstField() {
        code.focus();
    }

    private void deleteTranslation(Object itemId) {
        getBean().removeTranslation(((Translation) itemId).getLang());
        getParentLayout().saveAction(null);
    }

    @Override
    public void setBean(L bean) {
        super.setBean(bean);
        Locale loc = UI.getCurrent().getLocale();

        T desc = bean.getTranslationForced(loc.getLanguage());
        if (desc == null) {
            desc = createTranslation(null, loc.getLanguage());
            bean.addTranslation(loc.getLanguage(), desc);
        }

        ownLocale.setValue(desc.getContent());

        bic.removeAllItems();
        bic.addAll(bean.getTranslations().values());
    }

    @Override
    public L getBean() {
        Locale loc = UI.getCurrent().getLocale();

        L bean = super.getBean();

        T desc = bean.getTranslationForced(loc.getLanguage());
        if (desc == null) {
            desc = createTranslation(null, loc.getLanguage());
            bean.addTranslation(loc.getLanguage(), desc);
        }

        desc.setContent(ownLocale.getValue());
        bean.setDefaultLang(loc.getLanguage());

        return bean;
    }

    @Override
    public List<String> validate(Class<?>... validationGroups) {
        List<String> partial = super.validate(validationGroups);

        Validator v = getBeanValidator();

        getBean().getTranslations().values().stream()
                .flatMap((entry) -> v.validate(entry, validationGroups).stream())
                .map(cve -> getTranslator().getConstraintViolation(cve))
                .forEach(partial::add);

        return partial;
    }

    public abstract T createTranslation(String text, String lang);
}
