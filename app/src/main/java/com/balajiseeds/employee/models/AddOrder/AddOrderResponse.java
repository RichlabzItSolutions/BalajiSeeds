package com.balajiseeds.employee.models.AddOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOrderResponse {

    @SerializedName("status")
    @Expose
    private String status;

    @SerializedName("message")
    @Expose
    private String message;


    @SerializedName("partyId")
    @Expose
    private String partyId;


    public AddOrderResponse(String status, String message, String partyId) {
        this.status = status;
        this.message = message;
        this.partyId = partyId;
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

    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
