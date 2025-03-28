package com.balajiseeds.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AcceptPermisoonJson {

    @SerializedName("id")
    @Expose
    private String id;

   /* @SerializedName("acceptOrReject")
    @Expose
    private int acceptOrReject;*/


    public AcceptPermisoonJson(String id) {
        this.id = id;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

}
