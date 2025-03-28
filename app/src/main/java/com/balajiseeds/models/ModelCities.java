package com.balajiseeds.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ModelCities {
    public static class Cities {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("stateName")
        @Expose
        private String stateName;
        @SerializedName("cityName")
        @Expose
        private String cityName;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getStateName() {
            return stateName;
        }

        public void setStateName(String stateName) {
            this.stateName = stateName;
        }

        public String getCityName() {
            return cityName;
        }

        public void setCityName(String cityName) {
            this.cityName = cityName;
        }


    }

    public static class CitiesRequest {
        @SerializedName("stateId")
        @Expose
        private String stateId;

        public CitiesRequest(String stateId) {
            this.stateId = stateId;
        }

        public String getStateId() {
            return stateId;
        }

        public void setStateId(String stateId) {
            this.stateId = stateId;
        }
    }

    public static class CitiesResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Cities> data;

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

        public List<Cities> getData() {
            return data;
        }

        public void setData(List<Cities> data) {
            this.data = data;
        }

    }
}
