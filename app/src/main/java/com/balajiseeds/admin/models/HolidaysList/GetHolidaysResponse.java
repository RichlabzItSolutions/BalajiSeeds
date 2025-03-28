package com.balajiseeds.admin.models.HolidaysList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetHolidaysResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<GetHolidaysDetails> data;

    public GetHolidaysResponse(String status, String message, List<GetHolidaysDetails> data) {
        super();
        this.status = status;
        this.message = message;
        this.data = data;
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


    public List<GetHolidaysDetails> getData() {
        return data;
    }

    public void setData(List<GetHolidaysDetails> data) {
        this.data = data;
    }
}
