package com.balajiseeds;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import android.widget.Toast;

import com.balajiseeds.databinding.ActivityForgotPasswordBinding;

public class ForgotPasswordActivity extends AppCompatActivity {
    ActivityForgotPasswordBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityForgotPasswordBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.tvSendOtp.setOnClickListener(v -> {
            String mob = binding.etMob.getText().toString();
            if (!mob.isEmpty() && mob.length() == 10 && Integer.valueOf(mob.substring(0, 1)) > 5) {
                Intent i = new Intent(ForgotPasswordActivity.this, OtpVerificationActivity.class);
                i.putExtra("mob", mob);
                startActivity(i);
                finish();
            } else {
                Toast.makeText(ForgotPasswordActivity.this, "Enter valid mobile number", Toast.LENGTH_SHORT).show();
                binding.etMob.requestFocus();
            }

        });

    }
}