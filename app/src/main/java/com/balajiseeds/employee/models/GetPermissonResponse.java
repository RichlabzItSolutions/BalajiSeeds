package com.balajiseeds.employee.models;

import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class GetPermissonResponse {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private List<GetPermissionDetails> data;

    public GetPermissonResponse(String status, String message, List<GetPermissionDetails> data) {
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


    public List<GetPermissionDetails> getData() {
        return data;
    }

    public void setData(List<GetPermissionDetails> data) {
        this.data = data;
    }
}
