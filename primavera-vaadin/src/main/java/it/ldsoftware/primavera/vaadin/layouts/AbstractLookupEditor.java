package it.ldsoftware.primavera.vaadin.layouts;

import it.ldsoftware.primavera.dto.base.LookupDTO;
import it.ldsoftware.primavera.entities.base.Lookup;
import it.ldsoftware.primavera.vaadin.components.DTOGrid;

import static it.ldsoftware.primavera.vaadin.theme.MetricConstants.COLUMN_SMALL;

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
