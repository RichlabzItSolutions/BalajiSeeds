package com.balajiseeds.employee.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.balajiseeds.R;


public class SampleFragment extends Fragment {

    private static final String ARG_TITLE = "title";

    public static SampleFragment newInstance(String title) {
        SampleFragment fragment = new SampleFragment();
        Bundle args = new Bundle();
        args.putString(ARG_TITLE, title);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sample, container, false);
      /*  TextView textView = view.findViewById(R.id.textView);
        if (getArguments() != null) {
            textView.setText(getArguments().getString(ARG_TITLE));
        }*/
        return view;
    }
}