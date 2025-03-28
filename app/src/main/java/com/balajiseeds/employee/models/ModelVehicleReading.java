package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelVehicleReading {
    public static class Reading {
        @SerializedName("id")
        @Expose
        String id;
        @SerializedName("startMeter")
        @Expose
        String startMeter;
        @SerializedName("endMeter")
        @Expose
        String endMeter;
        @SerializedName("vehicleType")
        @Expose
        String vehicleType;
        @SerializedName("kilometerDriven")
        @Expose
        String kmsDriven;
        @SerializedName("date")
        @Expose
        String date;

        @SerializedName("empId")
        @Expose
        String empId;
        @SerializedName("name")
        @Expose
        String name;
        @SerializedName("startMeterImageUrl")
        @Expose
        String startMeterImageUrl;
        @SerializedName("endMeterImageUrl")
        @Expose
        String endMeterImageUrl;

        public String getStartMeterImageUrl() {
            return startMeterImageUrl;
        }

        public void setStartMeterImageUrl(String startMeterImageUrl) {
            this.startMeterImageUrl = startMeterImageUrl;
        }

        public String getEndMeterImageUrl() {
            return endMeterImageUrl;
        }

        public void setEndMeterImageUrl(String endMeterImageUrl) {
            this.endMeterImageUrl = endMeterImageUrl;
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

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStartMeter() {
            return startMeter;
        }

        public void setStartMeter(String startMeter) {
            this.startMeter = startMeter;
        }

        public String getEndMeter() {
            return endMeter;
        }

        public void setEndMeter(String endMeter) {
            this.endMeter = endMeter;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getKmsDriven() {
            return kmsDriven;
        }

        public void setKmsDriven(String kmsDriven) {
            this.kmsDriven = kmsDriven;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }
    }

    public static class addReadingRequest {
        @SerializedName("id")
        @Expose
        String id;
        @SerializedName("vehicleType")
        @Expose
        private String vehicleType;
        @SerializedName("reading")
        @Expose
        private String reading;

        public addReadingRequest(String vehicleType, String reading) {
            this.vehicleType = vehicleType;
            this.reading = reading;
        }

        public addReadingRequest(String id, String vehicleType, String reading) {
            this.vehicleType = vehicleType;
            this.reading = reading;
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

        public String getReading() {
            return reading;
        }

        public void setReading(String reading) {
            this.reading = reading;
        }
    }

    public static class deleteReadingRequest {
        @SerializedName("id")
        @Expose
        String id;

        public deleteReadingRequest(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class VehicleType {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("vehicleType")
        @Expose
        private String vehicleType;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getVehicleType() {
            return vehicleType;
        }

        public void setVehicleType(String vehicleType) {
            this.vehicleType = vehicleType;
        }

    }

    public static class GetVehicleTypeResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<VehicleType> data;

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

        public List<VehicleType> getData() {
            return data;
        }

        public void setData(List<VehicleType> data) {
            this.data = data;
        }
    }

    public static class fetchReadingResponse {
        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Reading> data;

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

        public List<Reading> getData() {
            return data;
        }

        public void setData(List<Reading> data) {
            this.data = data;
        }
    }

    public static class fetchReadingRequest {
        @SerializedName("fromDate")
        @Expose
        private String fromDate;
        @SerializedName("toDate")
        @Expose
        private String toDate;
        @SerializedName("state")
        @Expose
        private String state;
        @SerializedName("city")
        @Expose
        private String city;
        @SerializedName("searchStr")
        @Expose
        private String searchStr;

        public fetchReadingRequest(String fromDate, String toDate) {
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        public fetchReadingRequest(String fromDate, String toDate, String state, String city, String searchStr) {
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.state = state;
            this.city = city;
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

        public String getSearchStr() {
            return searchStr;
        }

        public void setSearchStr(String searchStr) {
            this.searchStr = searchStr;
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

}
