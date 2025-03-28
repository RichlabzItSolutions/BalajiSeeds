package com.balajiseeds.utils;

public class ModelSpinner {
    String Text, Id;

    public ModelSpinner(String text, String id) {
        Text = text;
        Id = id;
    }

    public String getText() {
        return Text;
    }

    public void setText(String text) {
        Text = text;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }
}
