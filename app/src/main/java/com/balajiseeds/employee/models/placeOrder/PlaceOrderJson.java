package com.balajiseeds.employee.models.placeOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PlaceOrderJson {

    @SerializedName("partyId")
    @Expose
    private String partyId;


    public PlaceOrderJson(String partyId) {
        this.partyId = partyId;
    }


    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
