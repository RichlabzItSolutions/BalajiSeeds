package com.balajiseeds.admin.models.AddExpensesHeads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeleteExpensesHeadsJson {

    @SerializedName("id")
    @Expose
    private String id;


    public DeleteExpensesHeadsJson(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
