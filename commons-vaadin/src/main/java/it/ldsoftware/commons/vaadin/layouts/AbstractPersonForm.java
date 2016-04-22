package it.ldsoftware.commons.vaadin.layouts;

import com.vaadin.ui.*;
import com.vaadin.ui.renderers.HtmlRenderer;
import it.ldsoftware.commons.dto.people.ContactDTO;
import it.ldsoftware.commons.entities.people.Contact;
import it.ldsoftware.commons.entities.people.Person;
import it.ldsoftware.commons.util.ContactType;
import it.ldsoftware.commons.util.PersonType;
import it.ldsoftware.commons.vaadin.components.DTOGrid;
import it.ldsoftware.commons.vaadin.data.util.converters.ContactTypeConverter;
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
import static it.ldsoftware.commons.i18n.CommonLabels.*;
import static it.ldsoftware.commons.vaadin.data.util.converters.ContactTypeConverter.findIcon;
import static it.ldsoftware.commons.vaadin.theme.MetricConstants.FIELD_WIDTH;
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

    @Override
    void addGeneralContent(VerticalLayout generalTab) {
        createFields();
        wireEvents();

        generalTab.addComponents(personType, name, surname, fullName, uniqueId, vatInfo, birthDate, sex);
        addPersonalFields(generalTab);
    }

    @Override
    void selectFirstField() {
        name.focus();
    }

    @Override
    public void addOtherTabs() {
        contacts = new DTOGrid<>(new ArrayList<>(), ContactDTO.class)
                .withLocalizationService(getTranslator())
                .withWidth(100, PERCENTAGE)
                .withHeight(250, PIXELS)
                .withDeleteColumn(this::removeFromMaster)
                .withColumnRenderer("contactType", new HtmlRenderer(), new ContactTypeConverter());

        Label label = new Label(getTranslator().translate("txt.contact"));
        TextField contact = new MTextField().withWidth(FIELD_WIDTH);
        ComboBox contactType = new ComboBox();
        Collection<ContactType> types = Arrays.stream(ContactType.values()).collect(toList());
        contactType.addItems(types);
        contactType.setItemCaptionMode(EXPLICIT_DEFAULTS_ID);
        types.forEach(type -> {
            contactType.setItemIcon(type, findIcon(type));
            contactType.setItemCaption(type, getTranslator().translate("enum." + type.toString()));
        });

        Button btnAdd = new MButton(PLUS)
                .withDescription(getTranslator().translate(ADD))
                .withListener(l -> {
                    Contact c = new Contact()
                            .withContactType((ContactType) contactType.getValue())
                            .withValue(contact.getValue());
                    getBean().addContact(c);
                    getParentLayout().saveAction(null);
                });

        VerticalLayout contactsTab = new MVerticalLayout(contacts, label, contact, contactType, btnAdd)
                .withFullWidth();
        addTab(contactsTab, getTranslator().translate("tab.contacts"));
    }

    @Override
    void setBean(T bean) {
        super.setBean(bean);
        updateGrids(bean);
    }

    public void switchFields(PersonType personType) {
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
        name = new MTextField(getTranslator().translate(getTextFieldName("name"))).withWidth(FIELD_WIDTH);
        surname = new MTextField(getTranslator().translate(getTextFieldName("surname"))).withWidth(FIELD_WIDTH);
        fullName = new MTextField(getTranslator().translate(getTextFieldName("fullName")));
        uniqueId = new MTextField(getTranslator().translate(getTextFieldName("uniqueId")));
        vatInfo = new MTextField(getTranslator().translate(getTextFieldName("vatInfo")));

        birthDate = new DateField(getTranslator().translate(getCalendarName("birthDate")));

        sex = new ComboBox(getTranslator().translate(getCheckboxName("sex")));
        sex.addItems("M", "F");
        sex.setItemCaption(sex.getItem("M"), getTranslator().translate("sex.m"));
        sex.setItemCaption(sex.getItem("F"), getTranslator().translate("sex.f"));
        personType = new ComboBox(getTranslator().translate(getComboName("personType")));
    }

    private void wireEvents() {
        name.addValueChangeListener(l -> signalChange());
        surname.addValueChangeListener(l -> signalChange());
        fullName.addValueChangeListener(l -> signalChange());
        uniqueId.addValueChangeListener(l -> signalChange());
        vatInfo.addValueChangeListener(l -> signalChange());
        birthDate.addValueChangeListener(l -> signalChange());
        sex.addValueChangeListener(l -> signalChange());
        personType.addValueChangeListener(l -> {
            switchFields((PersonType) l.getProperty().getValue());
        });
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
