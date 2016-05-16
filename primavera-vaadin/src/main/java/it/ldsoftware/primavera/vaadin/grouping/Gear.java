package it.ldsoftware.primavera.vaadin.grouping;

/**
 * Created by luca on 16/05/16.
 *
 * A gear created from the GuiGear annotation using the
 * {@link ComponentFactory}.
 */
public class Gear implements Comparable<Gear> {

    private String caption, role, css, group, beanName;

    public Gear withCaption(String caption) {
        this.caption = caption;
        return this;
    }

    public Gear withRole(String role) {
        this.role = role;
        return this;
    }

    public Gear withCss(String css) {
        this.css = css;
        return this;
    }

    public Gear withGroup(String group) {
        this.group = group;
        return this;
    }

    public Gear withBeanName(String beanName) {
        this.beanName = beanName;
        return this;
    }

    public String getCaption() {
        return caption;
    }

    public String getRole() {
        return role;
    }

    public String getCss() {
        return css;
    }

    public String getGroup() {
        return group;
    }

    public String getBeanName() {
        return beanName;
    }

    @Override
    public int compareTo(Gear o) {
        return caption.compareTo(o.caption);
    }
}
