package it.ldsoftware.primavera.textcompiler.dto;

import it.ldsoftware.primavera.presentation.base.BaseDTO;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Luca on 28/04/2016.
 * This entity represents a model that has been compiled.
 * It contains the fields for the title and the content of the model.
 */
@Getter @Setter
public class CompiledModel extends BaseDTO {

    private String title, body;

    public CompiledModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

}
