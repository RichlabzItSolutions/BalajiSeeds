package com.balajiseeds.admin.models.HolidaysList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHolidaysJson {

    @SerializedName("stateId")
    @Expose
    private String stateId;


    public GetHolidaysJson(String stateId) {
        super();
        this.stateId = stateId;

    }


    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }


}
