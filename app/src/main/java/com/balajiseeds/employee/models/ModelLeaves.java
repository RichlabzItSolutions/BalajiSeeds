package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelLeaves {
    public static class Leave {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("empId")
        @Expose
        private String empId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("leaveType")
        @Expose
        private String leaveType;
        @SerializedName("subject")
        @Expose
        private String subject;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("fromDate")
        @Expose
        private String fromDate;
        @SerializedName("toDate")
        @Expose
        private String toDate;
        @SerializedName("leaveStatus")
        @Expose
        private String leaveStatus;
        @SerializedName("leaveRejectReason")
        @Expose
        private String leaveRejectReason;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;

        public Leave() {
        }

        public Leave(String leaveType, String subject, String message, String fromDate, String toDate) {
            this.leaveType = leaveType;
            this.subject = subject;
            this.message = message;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        public Leave(String id, String leaveType, String subject, String message, String fromDate, String toDate) {
            this.id = id;
            this.leaveType = leaveType;
            this.subject = subject;
            this.message = message;
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeaveStatus() {
            return leaveStatus;
        }

        public void setLeaveStatus(String leaveStatus) {
            this.leaveStatus = leaveStatus;
        }

        public String getLeaveRejectReason() {
            return leaveRejectReason;
        }

        public void setLeaveRejectReason(String leaveRejectReason) {
            this.leaveRejectReason = leaveRejectReason;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public void setLeaveType(String leaveType) {
            this.leaveType = leaveType;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
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

    public static class DeleteLeaveRequest {
        @SerializedName("id")
        @Expose
        private String id;

        public DeleteLeaveRequest(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class FetchLeaveResponse {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Leave> leaveList;

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

        public List<Leave> getLeaveList() {
            return leaveList;
        }

        public void setLeaveList(List<Leave> leaveList) {
            this.leaveList = leaveList;
        }
    }

    public static class LeaveTypeResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<LeaveType> data;

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

        public List<LeaveType> getData() {
            return data;
        }

        public void setData(List<LeaveType> data) {
            this.data = data;
        }

    }

    public static class adminFetchLeaveRequest {
        @SerializedName("month")
        @Expose
        private String month;
        @SerializedName("year")
        @Expose
        private String year;
        @SerializedName("searchStr")
        @Expose
        private String searchStr;

        public adminFetchLeaveRequest(String month, String year, String searchStr) {
            this.month = month;
            this.year = year;
            this.searchStr = searchStr;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }

        public String getYear() {
            return year;
        }

        public void setYear(String year) {
            this.year = year;
        }

        public String getSearchStr() {
            return searchStr;
        }

        public void setSearchStr(String searchStr) {
            this.searchStr = searchStr;
        }
    }

    public static class adminRejectLeaveRequest {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("rejectReason")
        @Expose
        private String rejectReason;

        public adminRejectLeaveRequest(String id, String rejectReason) {
            this.id = id;
            this.rejectReason = rejectReason;
        }

        public String getRejectReason() {
            return rejectReason;
        }

        public void setRejectReason(String rejectReason) {
            this.rejectReason = rejectReason;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class cancelLeaveRequest {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("cancelReason")
        @Expose
        private String cancelReason;

        public cancelLeaveRequest(String id, String cancelReason) {
            this.id = id;
            this.cancelReason = cancelReason;
        }

        public String getCancelReason() {
            return cancelReason;
        }

        public void setCancelReason(String cancelReason) {
            this.cancelReason = cancelReason;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public class LeaveType {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("leaveType")
        @Expose
        private String leaveType;
        @SerializedName("code")
        @Expose
        private String code;
        @SerializedName("days")
        @Expose
        private String days;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLeaveType() {
            return leaveType;
        }

        public void setLeaveType(String leaveType) {
            this.leaveType = leaveType;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getDays() {
            return days;
        }

        public void setDays(String days) {
            this.days = days;
        }

    }


}
