package com.balajiseeds.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.GridView;

import java.util.List;

public class CustomCalendarView extends GridView {

    public CustomCalendarView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void setCalendar(List<CalendarDay> calendarDays) {
        CalendarAdapter adapter = new CalendarAdapter(getContext(), calendarDays);
        setAdapter(adapter);
    }
}