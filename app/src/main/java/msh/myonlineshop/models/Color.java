package msh.myonlineshop.models;

import java.io.Serializable;

public class Color implements Serializable {
    private long id;
    private String title;
    private String value;

    public Color() {
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
