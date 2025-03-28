package com.balajiseeds.admin.models.UpdateAmount;

import com.balajiseeds.employee.models.ModelProducts;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UpdateAmountResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;

    @SerializedName("data")
    @Expose
    private UpdateAmountDetails data;


    public UpdateAmountResponse(String status, String message, UpdateAmountDetails data) {
        this.status = status;
        this.message =message;
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

    public UpdateAmountDetails getData() {
        return data;
    }

    public void setData(UpdateAmountDetails data) {
        this.data = data;
    }
}
