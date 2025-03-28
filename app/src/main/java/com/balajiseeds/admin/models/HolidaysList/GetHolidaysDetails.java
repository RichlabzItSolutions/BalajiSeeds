package com.balajiseeds.admin.models.HolidaysList;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetHolidaysDetails {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("holidayTitle")
    @Expose
    private String holidayTitle;
    @SerializedName("stateId")
    @Expose
    private String stateId;
    @SerializedName("stateName")
    @Expose
    private String stateName;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("status")
    @Expose
    private String status;


    public GetHolidaysDetails(String id, String holidayTitle, String stateId, String stateName, String fromDate, String toDate, String description, String status) {
        super();
        this.id = id;
        this.holidayTitle = holidayTitle;
        this.stateId = stateId;
        this.stateName = stateName;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
        this.status = status;

    }


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getHolidayTitle() {
        return holidayTitle;
    }

    public void setHolidayTitle(String holidayTitle) {
        this.holidayTitle = holidayTitle;
    }

    public String getStateId() {
        return stateId;
    }

    public void setStateId(String stateId) {
        this.stateId = stateId;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}
