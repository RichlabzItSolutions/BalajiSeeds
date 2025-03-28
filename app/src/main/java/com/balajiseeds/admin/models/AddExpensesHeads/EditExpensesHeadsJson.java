package com.balajiseeds.admin.models.AddExpensesHeads;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class EditExpensesHeadsJson {


    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("narration")
    @Expose
    private String narration;


    @SerializedName("status")
    @Expose
    private String status;


    public EditExpensesHeadsJson(String id, String title, String narration, String status) {
        this.id = id;
        this.title = title;
        this.narration = narration;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
