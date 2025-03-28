package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetOrdersForEmployeeJson {

    @SerializedName("fromDate")
    @Expose
    private String fromDate;

    @SerializedName("toDate")
    @Expose
    private String toDate;


    public GetOrdersForEmployeeJson(String fromDate, String toDate) {
        this.fromDate = fromDate;
        this.toDate = toDate;
    }


    public String getFromDate() {
        return fromDate;
    }

    public void setFromDate(String fromDate) {
        this.fromDate = fromDate;
    }

    public String getToDate() {
        return toDate;
    }

    public void setToDate(String toDate) {
        this.toDate = toDate;
    }
}
