package com.balajiseeds.employee.models;

import com.google.gson.annotations.SerializedName;

public class UpdatePermissionJson {

    @SerializedName("id")
    public String id;
    @SerializedName("fromDate")
    public String fromDate;
    @SerializedName("toDate")
    public String toDate;
    @SerializedName("reason")
    public String reason;




    public UpdatePermissionJson(String id, String fromDate, String toDate, String reason) {
        this.id = id;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }


}
