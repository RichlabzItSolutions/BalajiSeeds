package com.balajiseeds.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.icu.util.Calendar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.core.content.res.ResourcesCompat;

import com.balajiseeds.R;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class CalendarAdapter extends BaseAdapter {

    private Context context;
    private List<CalendarDay> calendarDays;

    public CalendarAdapter(Context context, List<CalendarDay> calendarDays) {
        this.context = context;
        this.calendarDays = calendarDays;
    }

    @Override
    public int getCount() {
        return calendarDays.size();
    }

    @Override
    public Object getItem(int position) {
        return calendarDays.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            view = LayoutInflater.from(context).inflate(R.layout.grid_item_calendar, parent, false);
        }

        TextView dayTextView = view.findViewById(R.id.calendar_day);
        CalendarDay calendarDay = calendarDays.get(position);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.MONTH, calendarDay.getMonth());
        // Format the month using SimpleDateFormat
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM", Locale.getDefault());
        String monthShortName = dateFormat.format(calendar.getTime());

        dayTextView.setText("" + monthShortName + "\n" + calendarDay.getDay());

        // Set background color based on status
        if (calendarDay.getStatus() == CalendarDay.Status.PRESENT) {
            view.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.border_grey_round_corners, null));
            view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#EDFAEE")));
            dayTextView.setTextColor(Color.parseColor("#278B2B"));
        } else if (calendarDay.getStatus() == CalendarDay.Status.ABSENT) {
            view.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.border_grey_round_corners, null));
            view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FFE8F2")));
            dayTextView.setTextColor(Color.parseColor("#CB7097"));
        } else if (calendarDay.getStatus() == CalendarDay.Status.HOLIDAY) {
            view.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.border_grey_round_corners, null));
            view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#DEF4FF")));
            dayTextView.setTextColor(Color.parseColor("#5EA5C8"));
        }else if (calendarDay.getStatus() == CalendarDay.Status.LEAVES) {
                view.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.border_grey_round_corners, null));
            view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#FBEED1")));
            dayTextView.setTextColor(Color.parseColor("#EB7F23"));
        } else {
            // Default background color for other cases
            view.setBackground(ResourcesCompat.getDrawable(context.getResources(), R.drawable.border_grey_round_corners, null));
            view.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#F3F3F3")));
            dayTextView.setTextColor(Color.parseColor("#8A8B8B"));
        }

        return view;
    }
}