package com.balajiseeds.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;

import com.balajiseeds.R;
import com.balajiseeds.databinding.AdminActivityTrackEmployeeBinding;
import com.balajiseeds.employee.VehicleReadingActivity;
import com.balajiseeds.employee.models.ModelTracking;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Calendar;
import java.util.List;

public class TrackEmployeeActivity extends AppCompatActivity implements OnMapReadyCallback {

    AdminActivityTrackEmployeeBinding binding;
    WebServices webServices;
    String empid;
    LatLng lastPos;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = AdminActivityTrackEmployeeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(TrackEmployeeActivity.this);
        empid = getIntent().getStringExtra("empid");
        SupportMapFragment mapFragment = (SupportMapFragment)
                getSupportFragmentManager()
                        .findFragmentById(R.id.map);

        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
        binding.txtTrackMap.setText(getIntent().getStringExtra("empName") + " Tracking");
        binding.txtTrackMap.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        int year = Calendar.getInstance().get(Calendar.YEAR);
        int month = Calendar.getInstance().get(Calendar.MONTH);
        int day = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
        binding.tvDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);

        binding.tvDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dpd = new DatePickerDialog(TrackEmployeeActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                        binding.tvDate.setText(String.format("%02d", day) + "-" + String.format("%02d", (month + 1)) + "-" + year);
                        getLocations();
                    }
                }, Calendar.getInstance().get(Calendar.YEAR), Calendar.getInstance().get(Calendar.MONTH), Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
                dpd.show();
            }
        });
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {

        mMap = googleMap;
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setCompassEnabled(true);

        getLocations();
    }

    private void getLocations() {
        mMap.clear();
        webServices.fetchEmpTracking(new ModelTracking.fetchTrackingRequest(empid, binding.tvDate.getText().toString()), new WebServices.onFetchEmpTracking() {
            @Override
            public void fetchedEmpTracking(List<ModelTracking.trackingData> empTrackingList) {
                if (empTrackingList != null) {
                    for (ModelTracking.trackingData data : empTrackingList) {
                        LatLng pos = new LatLng(Double.parseDouble(data.getLat()), Double.parseDouble(data.getLon()));
                        MarkerOptions markerOptions = new MarkerOptions();
                        markerOptions.position(pos);
                        markerOptions.title(data.getTime());
                        mMap.addMarker(markerOptions).showInfoWindow();
                        lastPos = pos;
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastPos, 15)); // 15 is the zoom level, you can adjust as needed
                }
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        webServices.dismissDialog();
    }
}