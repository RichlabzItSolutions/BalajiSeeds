package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelTracking {
    public static class TrackingRequest {
        @SerializedName("lat")
        @Expose
        public String lat;

        @SerializedName("lon")
        @Expose
        public String lon;

        public TrackingRequest(String lat, String lon) {
            this.lat = lat;
            this.lon = lon;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }
    }

    public static class trackingData {
        //id,lat,lon,time
        @SerializedName("id")
        @Expose
        public String id;
        @SerializedName("lat")
        @Expose
        public String lat;
        @SerializedName("lon")
        @Expose
        public String lon;
        @SerializedName("time")
        @Expose
        public String time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }

    public static class fetchTrackingResponse {
        @SerializedName("status")
        @Expose
        public String status;
        @SerializedName("message")
        @Expose
        public String message;
        @SerializedName("data")
        @Expose
        public List<trackingData> data;

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

        public List<trackingData> getData() {
            return data;
        }

        public void setData(List<trackingData> data) {
            this.data = data;
        }
    }

    //trakingrequest
    public static class fetchTrackingRequest {
        //String empId,date
        @SerializedName("empId")
        @Expose
        public String empId;
        @SerializedName("date")
        @Expose
        public String date;

        public fetchTrackingRequest(String empId, String date) {
            this.empId = empId;
            this.date = date;
        }

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
    }
}
