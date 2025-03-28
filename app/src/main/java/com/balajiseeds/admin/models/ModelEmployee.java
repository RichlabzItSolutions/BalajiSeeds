package com.balajiseeds.admin.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelEmployee {
    public static class deleteEmpRequest {
        @SerializedName("id")
        @Expose
        private String id;

        public deleteEmpRequest(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class EmpRequest {
        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("name")
        @Expose
        private String name;
        @SerializedName("empCode")
        @Expose
        private String empCode;
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("mobile")
        @Expose
        private String mobile;
        @SerializedName("email")
        @Expose
        private String email;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("address")
        @Expose
        private String address;
        @SerializedName("salary")
        @Expose
        private Integer salary;

        public EmpRequest(String id, String name, String empCode, String mobile, String email, String state, String city, String address, Integer salary) {
            this.id = id;
            this.name = name;
            this.empCode = empCode;
            this.mobile = mobile;
            this.email = email;
            this.state = state;
            this.city = city;
            this.address = address;
            this.salary = salary;
        }

        public EmpRequest(String name, String empCode, String mobile, String email, String state, String city, String address, Integer salary) {
            this.name = name;
            this.empCode = empCode;
            this.mobile = mobile;
            this.email = email;
            this.state = state;
            this.city = city;
            this.address = address;
            this.salary = salary;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getEmpCode() {
            return empCode;
        }

        public void setEmpCode(String empCode) {
            this.empCode = empCode;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public Integer getSalary() {
            return salary;
        }

        public void setSalary(Integer salary) {
            this.salary = salary;
        }

    }

    public static class EmpResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;

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

    }

    public static class EmpListResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("employees")
        @Expose
        private List<ModelEmployee.EmpRequest> empList;

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

        public List<EmpRequest> getEmpList() {
            return empList;
        }

        public void setEmpList(List<EmpRequest> empList) {
            this.empList = empList;
        }
    }
    public static class EmpListRequest{
        @SerializedName("searchStr")
        @Expose
        private String searchStr;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;

        public EmpListRequest(String searchStr, String state, String city) {
            this.searchStr = searchStr;
            this.state = state;
            this.city = city;
        }

        public String getSearchStr() {
            return searchStr;
        }

        public void setSearchStr(String searchStr) {
            this.searchStr = searchStr;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
