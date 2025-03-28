package com.balajiseeds.admin.models.DeleteHolidays;

import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysResponse;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import retrofit2.Callback;

public class DeleteHolidaysJson {

    @SerializedName("id")
    @Expose
    private String id;


    public DeleteHolidaysJson(String id) {
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
