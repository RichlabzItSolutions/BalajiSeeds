package com.balajiseeds.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ModelChangeStatus {
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("empId")
    @Expose
    String empId;

    public ModelChangeStatus(String status, String empId) {
        this.status = status;
        this.empId = empId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmpId() {
        return empId;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }
}
