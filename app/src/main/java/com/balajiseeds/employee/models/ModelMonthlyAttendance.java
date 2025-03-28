package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelMonthlyAttendance {

    public static class Attendance {

        @SerializedName("empId")
        @Expose
        private String empId;
        @SerializedName("date")
        @Expose
        private String date;
        @SerializedName("loginTime")
        @Expose
        private String loginTime;
        @SerializedName("status")
        @Expose
        private String status;

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getLoginTime() {
            return loginTime;
        }

        public void setLoginTime(String loginTime) {
            this.loginTime = loginTime;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

    }

    public static class MonthlyAttendanceResponse {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("totalWorkingDays")
        @Expose
        private Integer totalWorkingDays;
        @SerializedName("totalHolidays")
        @Expose
        private Integer totalHolidays;
        @SerializedName("totalLeaves")
        @Expose
        private Integer totalLeaves;
        @SerializedName("totalAbsent")
        @Expose
        private Integer totalAbsent;
        @SerializedName("totalPresent")
        @Expose
        private Integer totalPresent;
        @SerializedName("data")
        @Expose
        private List<Attendance> data;

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

        public Integer getTotalWorkingDays() {
            return totalWorkingDays;
        }

        public void setTotalWorkingDays(Integer totalWorkingDays) {
            this.totalWorkingDays = totalWorkingDays;
        }

        public Integer getTotalHolidays() {
            return totalHolidays;
        }

        public void setTotalHolidays(Integer totalHolidays) {
            this.totalHolidays = totalHolidays;
        }

        public Integer getTotalLeaves() {
            return totalLeaves;
        }

        public void setTotalLeaves(Integer totalLeaves) {
            this.totalLeaves = totalLeaves;
        }

        public Integer getTotalAbsent() {
            return totalAbsent;
        }

        public void setTotalAbsent(Integer totalAbsent) {
            this.totalAbsent = totalAbsent;
        }

        public Integer getTotalPresent() {
            return totalPresent;
        }

        public void setTotalPresent(Integer totalPresent) {
            this.totalPresent = totalPresent;
        }

        public List<Attendance> getData() {
            return data;
        }

        public void setData(List<Attendance> data) {
            this.data = data;
        }

    }

    public static class MonthlyAttendanceRequest {
        @SerializedName("month")
        @Expose
        private String month;
        @SerializedName("year")
        @Expose
        private String year;
        @SerializedName("searchStr")
        @Expose
        private String searchStr;
        @SerializedName("empId")
        @Expose
        private String empId;

        public MonthlyAttendanceRequest(String month, String year) {
            this.month = month;
            this.year = year;
        }

        public MonthlyAttendanceRequest(String month, String year, String searchStr) {
            this.month = month;
            this.year = year;
            this.searchStr = searchStr;
        }

        public MonthlyAttendanceRequest(String month, String year, String empId, String searchStr) {
            this.month = month;
            this.year = year;
            this.searchStr = searchStr;
            this.empId = empId;
        }

        public String getSearchStr() {
            return searchStr;
        }

        public void setSearchStr(String searchStr) {
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
    }

    public static class AdminGetMonthlyAttendanceResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("userAttendance")
        @Expose
        private List<UserAttendance> userAttendance;

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

        public List<UserAttendance> getUserAttendance() {
            return userAttendance;
        }

        public void setUserAttendance(List<UserAttendance> userAttendance) {
            this.userAttendance = userAttendance;
        }

    }

    public static class UserAttendance {

        @SerializedName("totalWorkingDays")
        @Expose
        private Integer totalWorkingDays;
        @SerializedName("totalHolidays")
        @Expose
        private Integer totalHolidays;
        @SerializedName("totalLeaves")
        @Expose
        private Integer totalLeaves;
        @SerializedName("totalAbsent")
        @Expose
        private Integer totalAbsent;
        @SerializedName("totalPresent")
        @Expose
        private Integer totalPresent;
        @SerializedName("empId")
        @Expose
        private String empId;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("salary")
        @Expose
        private String salary;
        @SerializedName("id")
        @Expose
        private String id;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getTotalWorkingDays() {
            return totalWorkingDays;
        }

        public void setTotalWorkingDays(Integer totalWorkingDays) {
            this.totalWorkingDays = totalWorkingDays;
        }

        public Integer getTotalHolidays() {
            return totalHolidays;
        }

        public void setTotalHolidays(Integer totalHolidays) {
            this.totalHolidays = totalHolidays;
        }

        public Integer getTotalLeaves() {
            return totalLeaves;
        }

        public void setTotalLeaves(Integer totalLeaves) {
            this.totalLeaves = totalLeaves;
        }

        public Integer getTotalAbsent() {
            return totalAbsent;
        }

        public void setTotalAbsent(Integer totalAbsent) {
            this.totalAbsent = totalAbsent;
        }

        public Integer getTotalPresent() {
            return totalPresent;
        }

        public void setTotalPresent(Integer totalPresent) {
            this.totalPresent = totalPresent;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSalary() {
            return salary;
        }

        public void setSalary(String salary) {
            this.salary = salary;
        }

    }
}
