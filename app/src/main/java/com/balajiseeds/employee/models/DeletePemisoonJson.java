package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DeletePemisoonJson {

    @SerializedName("id")
    @Expose
    private String id;


    public DeletePemisoonJson(String id) {
        super();
        this.id = id;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


   /* public DeleteHolidaysJson enqueue(Callback<AddHolidaysResponse> kol) {
    }*/
}
