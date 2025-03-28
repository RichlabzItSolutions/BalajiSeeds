package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetPermissionDetails {

    @SerializedName("id")
    @Expose
    public String id;
    @SerializedName("empId")
    @Expose
    public String empId;
    @SerializedName("empName")
    @Expose
    public String empName;
    @SerializedName("fromDate")
    @Expose
    public String fromDate;
    @SerializedName("toDate")
    @Expose
    public String toDate;
    @SerializedName("reason")
    @Expose
    public String reason;
    @SerializedName("status")
    @Expose
    public String status;


    public GetPermissionDetails(String id, String empId, String empName, String fromDate, String toDate, String reason, String status) {
        this.id = id;
        this.empId = empId;
        this.empName = empName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.reason = reason;
        this.status = status;
    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public String getEmpName() {
        return empName;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
