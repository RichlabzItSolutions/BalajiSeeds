package com.balajiseeds.admin.models.AddHoilidays;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddHolidaysResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;


    public AddHolidaysResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
