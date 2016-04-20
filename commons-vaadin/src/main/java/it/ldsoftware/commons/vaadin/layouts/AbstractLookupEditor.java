package it.ldsoftware.commons.vaadin.layouts;

import it.ldsoftware.commons.dto.base.LookupDTO;
import it.ldsoftware.commons.entities.base.Lookup;
import it.ldsoftware.commons.vaadin.components.DTOGrid;

import static it.ldsoftware.commons.vaadin.theme.MetricConstants.COLUMN_SMALL;

/**
 * Created by luca on 20/04/16.
 * Abstract lookup editor. Can be extended and used as-is or modified
 * to match more complex lookups
 */
public abstract class AbstractLookupEditor<L extends Lookup<?>, D extends LookupDTO<L>> extends AbstractEditor<L, D> {

    @Override
    public void customizeGrid(DTOGrid<L, D> grid) {
        super.customizeGrid(grid);
        grid.getColumn("code").setWidth(COLUMN_SMALL);
    }

}
