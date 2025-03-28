package com.balajiseeds.employee.models.AddOrder;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddOrderJson {

    @SerializedName("partyName")
    @Expose
    private String partyName;

    @SerializedName("partyCode")
    @Expose
    private String partyCode;


    public AddOrderJson(String partyName, String partyCode) {
        this.partyName = partyName;
        this.partyCode = partyCode;
    }


    public String getPartyName() {
        return partyName;
    }

    public void setPartyName(String partyName) {
        this.partyName = partyName;
    }

    public String getPartyCode() {
        return partyCode;
    }

    public void setPartyCode(String partyCode) {
        this.partyCode = partyCode;
    }
}
