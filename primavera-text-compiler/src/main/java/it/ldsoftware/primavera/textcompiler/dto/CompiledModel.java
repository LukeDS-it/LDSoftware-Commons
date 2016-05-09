package it.ldsoftware.primavera.textcompiler.dto;

/**
 * Created by Luca on 28/04/2016.
 * This entity represents a model that has been compiled.
 * It contains the fields for the title and the content of the model.
 */
public class CompiledModel {

    private String title, body;

    public CompiledModel(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
