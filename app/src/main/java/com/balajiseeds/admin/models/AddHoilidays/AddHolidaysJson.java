package com.balajiseeds.admin.models.AddHoilidays;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AddHolidaysJson {

    @SerializedName("holidayTitle")
    @Expose
    private String holidayTitle;
    @SerializedName("fromDate")
    @Expose
    private String fromDate;
    @SerializedName("toDate")
    @Expose
    private String toDate;

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("states")
    @Expose
    private List states;
    @SerializedName("status")
    @Expose
    private String status;


    public AddHolidaysJson(String holidayTitle, String fromDate, String toDate, String description, List states, String status) {
        super();
        this.holidayTitle = holidayTitle;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.description = description;
        this.states = states;
        this.status = status;
    }


    public String getHolidayTitle() {
        return holidayTitle;
    }

    public void setHolidayTitle(String holidayTitle) {
        this.holidayTitle = holidayTitle;
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

    public List getStates() {
        return states;
    }

    public void setStates(List states) {
        this.states = states;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
