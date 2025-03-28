package com.balajiseeds.employee;

import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;

import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.balajiseeds.R;
import com.balajiseeds.databinding.EmpActivityAddActivityBinding;
import com.balajiseeds.employee.adapters.AdapterAddImages;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.WebServices;
import com.balajiseeds.utils.onfilePick;
import com.canhub.cropper.CropImageContract;
import com.canhub.cropper.CropImageContractOptions;
import com.canhub.cropper.CropImageView;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class AddActivityActivity extends BaseActivity {

    public static List<File> filesToUpload;
    EmpActivityAddActivityBinding binding;
    ActivityResultLauncher<CropImageContractOptions> crop;
    AdapterAddImages adapter;
    WebServices webServices;
    List<Object> objectList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = EmpActivityAddActivityBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(AddActivityActivity.this);
        objectList = new ArrayList<>();
        registerResult(new onfilePick() {
            @Override
            public void onFilePicked(File file) {

            }
        });
        binding.textView.setOnClickListener(v -> {
            getOnBackPressedDispatcher().onBackPressed();
        });
        if (Constants.SelectedActivity != null) {
            binding.textView.setText("Edit Activity");
            objectList.addAll(Constants.SelectedActivity.getPhotos());
            setActivityDetails();
        }

        binding.rvImages.setLayoutManager(new GridLayoutManager(AddActivityActivity.this, 3));
        adapter = new AdapterAddImages(objectList, AddActivityActivity.this, crop);
        binding.rvImages.setAdapter(adapter);

        binding.tvSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filesToUpload = new ArrayList<>();
                filesToUpload = adapter.getuploadedFiles();
                String title, location, desc;
                title = binding.etTitle.getText().toString();
                location = binding.etLocation.getText().toString();
                desc = binding.etDescription.getText().toString();
                if (title.isEmpty()) {
                    Toast.makeText(AddActivityActivity.this, "Title cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (location.isEmpty()) {
                    Toast.makeText(AddActivityActivity.this, "Location cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (desc.isEmpty()) {
                    Toast.makeText(AddActivityActivity.this, "Description cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    if (Constants.SelectedActivity == null) {
                        //Add Activity
                        ModelActivity.addActivityRequest request = new ModelActivity.addActivityRequest(title, desc, location, filesToUpload);
                        webServices.addActivity(request, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                finish();
                            }
                        });
                    } else {
                        //Edit Activity
                        List<String> filesToDelete = new ArrayList<>();
                        adapter.getDeleteList().size();
                        filesToUpload = adapter.getuploadedFiles();
                        filesToDelete = adapter.getDeleteList();
                        ModelActivity.EditActivityRequest request = new ModelActivity.EditActivityRequest(Constants.SelectedActivity.getId(), title, desc, location, filesToUpload, filesToDelete);
                        webServices.editActivity(request, new WebServices.onResponse() {
                            @Override
                            public void response() {
                                finish();
                            }
                        });

                    }
                }
            }
        });


    }

    private void setActivityDetails() {
        binding.etTitle.setText(Constants.SelectedActivity.getTitle());
        binding.etLocation.setText(Constants.SelectedActivity.getLocation());
        binding.etDescription.setText(Constants.SelectedActivity.getDescription());

    }

    private void registerResult(onfilePick onfilePick) {
        crop = registerForActivityResult(new CropImageContract(), new ActivityResultCallback<CropImageView.CropResult>() {
            @Override
            public void onActivityResult(CropImageView.CropResult result) {
                Uri selectedFileUri = result.getUriContent();
                if (selectedFileUri != null) {
                    File f = Constants.uriToFile(selectedFileUri, AddActivityActivity.this);
                    if (f != null) {
                        adapter.addTolist(selectedFileUri);
//                        onfilePick.onFilePicked(f);
                    }
                }

            }
        });


    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Constants.SelectedActivity = null;
        webServices.dismissDialog();
    }
}