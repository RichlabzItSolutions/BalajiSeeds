package com.balajiseeds.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;


import com.balajiseeds.R;

import java.util.List;

public class CustomSpinnerAdapter extends ArrayAdapter<ModelSpinner> {

    private LayoutInflater inflater;

    public CustomSpinnerAdapter(Context context, List<ModelSpinner> models) {
        super(context, R.layout.simple_spinner_item, models);
        inflater = LayoutInflater.from(context);
        setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ModelSpinner model = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_spinner_item, parent, false);
        }

        // Lookup view for data population
        TextView textView = convertView.findViewById(android.R.id.text1);

        // Populate the data into the template view using the data object
        textView.setText(model.getText());

        return convertView;
    }

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ModelSpinner model = getItem(position);

        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.simple_spinner_dropdown_item, parent, false);
        }

        // Lookup view for data population
        TextView textView = convertView.findViewById(android.R.id.text1);

        // Populate the data into the template view using the data object
        textView.setText(model.getText());

        return convertView;
    }
}