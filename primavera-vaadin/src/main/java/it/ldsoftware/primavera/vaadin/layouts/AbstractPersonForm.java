package it.ldsoftware.primavera.vaadin.layouts;

import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import it.ldsoftware.primavera.presentation.people.ContactDTO;
import it.ldsoftware.primavera.model.people.Contact;
import it.ldsoftware.primavera.model.people.Person;
import it.ldsoftware.primavera.util.ContactType;
import it.ldsoftware.primavera.util.PersonType;
import it.ldsoftware.primavera.vaadin.components.DTOGrid;
import it.ldsoftware.primavera.vaadin.data.util.converters.ContactTypeConverter;
import org.vaadin.viritin.button.MButton;
import org.vaadin.viritin.fields.MTextField;
import org.vaadin.viritin.layouts.MVerticalLayout;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static com.vaadin.server.FontAwesome.PLUS;
import static com.vaadin.server.Sizeable.Unit.PERCENTAGE;
import static com.vaadin.server.Sizeable.Unit.PIXELS;
import static com.vaadin.ui.AbstractSelect.ItemCaptionMode.EXPLICIT_DEFAULTS_ID;
import static com.vaadin.ui.themes.ValoTheme.*;
import static it.ldsoftware.primavera.presentation.people.ContactDTO.FIELD_CONTACT_TYPE;
import static it.ldsoftware.primavera.presentation.people.PersonDTO.*;
import static it.ldsoftware.primavera.i18n.CommonLabels.ADD;
import static it.ldsoftware.primavera.i18n.LanguageUtils.*;
import static it.ldsoftware.primavera.vaadin.i18n.CommonLabels.TAB_CONTACTS;
import static it.ldsoftware.primavera.vaadin.i18n.CommonLabels.TXT_CONTACT;
import static it.ldsoftware.primavera.vaadin.theme.MetricConstants.FIELD_WIDTH;
import static java.util.stream.Collectors.toList;


/**
 * Created by luca on 22/04/16.
 * This form contains all common information regarding people.
 */
public abstract class AbstractPersonForm<T extends Person> extends TabbedForm<T> {

    private TextField name, surname, fullName, uniqueId, vatInfo;
    private DateField birthDate;
    private ComboBox sex, personType;

    private DTOGrid<Contact, ContactDTO> contacts;

    public AbstractPersonForm(AbstractEditor parent) {
        super(parent);
    }

    @Override
    public void addGeneralContent(VerticalLayout generalTab) {
        createFields();
        wireEvents();

        generalTab.addComponents(personType, name, surname, fullName, uniqueId, vatInfo, birthDate, sex);
        addPersonalFields(generalTab);
    }

    @Override
    public void selectFirstField() {
        name.focus();
    }

    @Override
    public void addOtherTabs() {
        contacts = new DTOGrid<>(new ArrayList<>(), ContactDTO.class)
                .withLocalizationService(getTranslator())
                .withWidth(100, PERCENTAGE)
                .withHeight(250, PIXELS)
                .withDeleteColumn(this::removeFromMaster)
                .withColumnRenderer(FIELD_CONTACT_TYPE, new HtmlRenderer(), new ContactTypeConverter());

        Label label = new Label(getTranslator().translate(TXT_CONTACT));
        TextField contact = new MTextField().withWidth(FIELD_WIDTH).withStyleName(TEXTFIELD_TINY);
        ComboBox contactType = new ComboBox();
        contactType.setWidth(FIELD_WIDTH);
        contactType.setStyleName(COMBOBOX_TINY);
        Collection<ContactType> types = Arrays.stream(ContactType.values()).collect(toList());
        contactType.addItems(types);
        contactType.setItemCaptionMode(EXPLICIT_DEFAULTS_ID);
        types.forEach(type -> {
            contactType.setItemIcon(type, ContactTypeConverter.findIcon(type));
            contactType.setItemCaption(type, getTranslator().translate("enum." + type.toString()));
        });

        Button btnAdd = new MButton(PLUS)
                .withDescription(getTranslator().translate(ADD))
                .withListener(l -> {
                    Contact c = new Contact()
                            .withContactType((ContactType) contactType.getValue())
                            .withValue(contact.getValue());
                    getBean().addContact(c);
                    getEditor().saveAction(null);
                });

        VerticalLayout contactsTab = new MVerticalLayout(contacts, label, contact, contactType, btnAdd)
                .withFullWidth();
        addTab(contactsTab, getTranslator().translate(TAB_CONTACTS));
    }

    @Override
    public void setBean(T bean) {
        super.setBean(bean);
        updateGrids(bean);
    }

    protected void switchFields(PersonType personType) {
        switch (personType) {
            case REAL:
                name.setVisible(true);
                surname.setVisible(true);
                fullName.setVisible(false);
                sex.setVisible(true);
                break;
            case ABSTRACT:
                name.setVisible(false);
                surname.setVisible(false);
                fullName.setVisible(true);
                sex.setVisible(false);
                break;
        }
    }

    private void createFields() {
        name = new MTextField(getTranslator().translate(getTextFieldName(FIELD_NAME))).withWidth(FIELD_WIDTH);
        surname = new MTextField(getTranslator().translate(getTextFieldName(FIELD_SURNAME))).withWidth(FIELD_WIDTH);
        fullName = new MTextField(getTranslator().translate(getTextFieldName(FIELD_FULL_NAME)));
        uniqueId = new MTextField(getTranslator().translate(getTextFieldName(FIELD_UNIQUE_ID)));
        vatInfo = new MTextField(getTranslator().translate(getTextFieldName(FIELD_VAT_INFO)));

        birthDate = new DateField(getTranslator().translate(getCalendarName(FIELD_BIRTH_DATE)));

        sex = new ComboBox(getTranslator().translate(getCheckboxName(FIELD_SEX)));
        sex.addItems("M", "F");
        sex.setItemCaption(sex.getItem("M"), getTranslator().translate("sex.m"));
        sex.setItemCaption(sex.getItem("F"), getTranslator().translate("sex.f"));
        personType = new ComboBox(getTranslator().translate(getComboName(FIELD_RECORD_TYPE)));

        setStyles();
    }

    private void setStyles() {
        name.setStyleName(TEXTFIELD_TINY);
        surname.setStyleName(TEXTFIELD_TINY);
        fullName.setStyleName(TEXTFIELD_TINY);
        uniqueId.setStyleName(TEXTFIELD_TINY);
        vatInfo.setStyleName(TEXTFIELD_TINY);
        birthDate.setStyleName(DATEFIELD_TINY);
        sex.setStyleName(COMBOBOX_TINY);
        personType.setStyleName(COMBOBOX_TINY);
        sex.setWidth(FIELD_WIDTH);
        personType.setWidth(FIELD_WIDTH);
        birthDate.setWidth(FIELD_WIDTH);
        vatInfo.setWidth(FIELD_WIDTH);
        uniqueId.setWidth(FIELD_WIDTH);
    }

    private void wireEvents() {
        name.addValueChangeListener(l -> signalChange());
        surname.addValueChangeListener(l -> signalChange());
        fullName.addValueChangeListener(l -> signalChange());
        uniqueId.addValueChangeListener(l -> signalChange());
        vatInfo.addValueChangeListener(l -> signalChange());
        birthDate.addValueChangeListener(l -> signalChange());
        sex.addValueChangeListener(l -> signalChange());
        personType.addValueChangeListener(l -> switchFields((PersonType) l.getProperty().getValue()));
    }

    private void removeFromMaster(List<ContactDTO> dtoList) {
        ContactDTO dto = dtoList.get(0);
        Contact c = getDatabaseService().findOne(Contact.class, dto.getId());
        c.setPerson(null);
        getDatabaseService().save(Contact.class, c);
        getDatabaseService().delete(Contact.class, c.getId());
        setBean(findPerson(getBean().getId()));
    }

    private void updateGrids(T bean) {
        contacts.refresh(bean.getContacts().stream().map(ContactDTO::new).collect(toList()));
    }

    public abstract T findPerson(long id);

    public abstract void addPersonalFields(VerticalLayout generalTab);
}
