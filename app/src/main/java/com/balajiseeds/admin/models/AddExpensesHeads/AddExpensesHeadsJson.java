package com.balajiseeds.admin.models.AddExpensesHeads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddExpensesHeadsJson {

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("narration")
    @Expose
    private String narration;




    public AddExpensesHeadsJson(String title, String narration) {
        this.title = title;
        this.narration = narration;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNarration() {
        return narration;
    }

    public void setNarration(String narration) {
        this.narration = narration;
    }
}
