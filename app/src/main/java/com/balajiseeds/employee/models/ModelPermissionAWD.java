package com.balajiseeds.employee.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelPermissionAWD {
    //AWD=Additional working day

    public static class awd {
//id,empId,empName,fromDate,toDate,reason,status

       /* @SerializedName("id")
        public int id;
        @SerializedName("empId")
        public String empId;
        @SerializedName("empName")
        public String empName;*/
        @SerializedName("fromDate")
        public String fromDate;
        @SerializedName("toDate")
        public String toDate;
        @SerializedName("reason")
        public String reason;
        @SerializedName("status")
        public String status;

        public awd(String fromDate, String toDate, String reason) {
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.reason = reason;
        }

        /*public awd(int id, String fromDate, String toDate, String reason) {
            this.id = id;
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.reason = reason;
        }*/


      /*  public int getId() {
            return id;
        }

        public void setId(int id) {
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
        }*/

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

    public static class awdResponse{
        @SerializedName("status")
        public String status;
        @SerializedName("message")
        public String message;
        @SerializedName("data")
        public List<awd> awdData;

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

        public List<awd> getAwdData() {
            return awdData;
        }

        public void setAwdData(List<awd> awdData) {
            this.awdData = awdData;
        }
    }
}
