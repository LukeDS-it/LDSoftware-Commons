package it.ldsoftware.commons.vaadin.components;

import com.vaadin.event.LayoutEvents.LayoutClickListener;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Image;
import com.vaadin.ui.Label;
import org.vaadin.viritin.layouts.MCssLayout;

/**
 * Created by luca on 02/05/16.
 * <p>
 * This component mimics a live tile of the windows metro UI.
 */
public class LiveTile extends CssLayout {

    private static final long serialVersionUID = 1L;

    private CssLayout inner;
    private Image image;
    private Label label;

    public LiveTile() {
        image = new Image();
        label = new Label();
        inner = new MCssLayout(image, label).withStyleName("tile");

        image.setStyleName("tile-img");
        label.setStyleName("tile-caption");

        setStyleName("tile-outer");

        addComponent(inner);
    }

    public LiveTile withImage(String resource) {
        image.setSource(new ThemeResource(resource));
        image.setCaption(null);
        return this;
    }

    public LiveTile withCaption(String text) {
        label.setValue(text);
        return this;
    }

    public LiveTile withColor(TileColor color) {
        inner.setStyleName(color.style());
        return this;
    }

    public LiveTile withClickListener(LayoutClickListener listener) {
        addLayoutClickListener(listener);
        return this;
    }

    public LiveTile withTooltip(String tooltip) {
        setDescription(tooltip);
        return this;
    }

    public LiveTile withDoubleWitdth() {
        addStyleName("w-double");
        return this;
    }

    public LiveTile withBackground(String resource) {
        return this;
    }

    public enum TileColor {
        GREEN("green"), LIGHT_PURPLE("light-purple"), PURPLE("purple"), DARK_PURPLE("dark-purple"), DARK_RED(
                "dark-red"), BLUE("blue"), DARK_BLUE("dark-blue"), YELLOW("yellow"), ORANGE("orange"), DARK_ORANGE(
                "dark-orange"), RED("red"), DEEP_BLUE("deep-blue");

        private String style;

        TileColor(String s) {
            style = s;
        }

        public String style() {
            return style;
        }
    }

}
