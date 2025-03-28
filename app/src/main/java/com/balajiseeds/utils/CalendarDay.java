package com.balajiseeds.utils;

public class CalendarDay {
    private int day, month;
    private Status status;

    public CalendarDay(int day, int month, Status status) {
        this.day = day;
        this.status = status;
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getMonth() {
        return month;
    }

    public enum Status {
        PRESENT, ABSENT, HOLIDAY,LEAVES
    }
}