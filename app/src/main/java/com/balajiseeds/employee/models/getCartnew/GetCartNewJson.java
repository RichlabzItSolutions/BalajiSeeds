package com.balajiseeds.employee.models.getCartnew;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCartNewJson {

    @SerializedName("partyId")
    @Expose
    private String partyId;


    public GetCartNewJson( String partyId) {
        this.partyId = partyId;
    }


    public String getPartyId() {
        return partyId;
    }

    public void setPartyId(String partyId) {
        this.partyId = partyId;
    }
}
