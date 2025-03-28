package com.balajiseeds;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.balajiseeds.admin.AdminMainActivity;
import com.balajiseeds.admin.models.OTP.VerifyOTPJson;
import com.balajiseeds.databinding.ActivityOtpVerificationBinding;
import com.balajiseeds.databinding.DialogNewPasswordBinding;
import com.balajiseeds.models.ModelChangePassword;
import com.balajiseeds.models.ModelRequestOTP;
import com.balajiseeds.utils.Constants;
import com.balajiseeds.utils.SharedPref;
import com.balajiseeds.utils.WebServices;

import java.util.Locale;
import java.util.Objects;

public class OtpVerificationActivity extends AppCompatActivity {
    WebServices webServices;
    ActivityOtpVerificationBinding binding;
    String defaultMobText = "we sent a verification code to +91-";
    String mob;
    String otp;
    private CountDownTimer otpCountDownTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityOtpVerificationBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        webServices = new WebServices(OtpVerificationActivity.this);
        mob = getIntent().getStringExtra("mob");
        binding.tvMob.setText(defaultMobText + mob);
        sendOtp();

        binding.tvVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                otp = binding.etOtp.getText().toString();

                if (TextUtils.isEmpty(otp)) {

                    Toast.makeText(OtpVerificationActivity.this, "Please Enter OTP", Toast.LENGTH_SHORT).show();

                }
                else {

                   // verifyOtp();

                    webServices.VerifyOTP(new VerifyOTPJson(mob, otp), new WebServices.onVerifyOTP() {
                        @Override
                        public void OTPVerifyed() {
                            // startOtpTimer(60000);

                            Dialog dialog_update_pass = new Dialog(OtpVerificationActivity.this);
                            DialogNewPasswordBinding dialogNewPasswordBinding = DialogNewPasswordBinding.inflate(getLayoutInflater());
                            dialog_update_pass.setContentView(dialogNewPasswordBinding.getRoot());
                            Objects.requireNonNull(dialog_update_pass.getWindow()).setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                            dialog_update_pass.show();
                            dialogNewPasswordBinding.tvUpdatePass.setOnClickListener(v1 -> {
                                String pass1, pass2;
                                pass1 = dialogNewPasswordBinding.etPass.getText().toString();
                                pass2 = dialogNewPasswordBinding.etPass2.getText().toString();
                                if (!pass1.isEmpty() && !pass2.isEmpty() && pass1.equals(pass2)) {
                                    ModelChangePassword.ChangePasswordRequest request = new ModelChangePassword.ChangePasswordRequest(mob, otp, pass1);
                                    webServices.ChangePassword(request, new WebServices.onPasswordChange() {
                                        @Override
                                        public void onPasswordChanged() {
                                            new SharedPref(OtpVerificationActivity.this).clearAll();
                                            startActivity(new Intent(OtpVerificationActivity.this, LoginActivity.class));
                                            finishAffinity();
                                        }
                                    });

                                } else {
                                    Toast.makeText(OtpVerificationActivity.this, "Password doesn't match", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });


                }

            }
        });
        binding.tvResend.setOnClickListener(View -> {
            sendOtp();
        });

    }

    private void sendOtp() {
        webServices.RequestOTP(new ModelRequestOTP.OTPRequest(mob), new WebServices.onRequestOTP() {
            @Override
            public void OTPRequested() {
                startOtpTimer(60000);
            }
        });
    }


    private void verifyOtp() {
        webServices.VerifyOTP(new VerifyOTPJson(mob, otp), new WebServices.onVerifyOTP() {
            @Override
            public void OTPVerifyed() {
               // startOtpTimer(60000);
            }
        });
    }

    private void startOtpTimer(long durationMillis) {
        binding.tvResend.setEnabled(false);
        otpCountDownTimer = new CountDownTimer(durationMillis, 1000) {
            public void onTick(long millisUntilFinished) {
                String timeLeftFormatted = formatTime(millisUntilFinished);
                binding.tvTimer.setText(timeLeftFormatted);
            }

            public void onFinish() {
                binding.tvTimer.setText("00:00");
                binding.tvResend.setEnabled(true);
            }
        }.start();
    }

    private String formatTime(long millis) {
        // Format the time in minutes:seconds
        long seconds = millis / 1000;
        long minutes = seconds / 60;
        seconds = seconds % 60;

        return String.format(Locale.getDefault(), "%02d:%02d", minutes, seconds);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (otpCountDownTimer != null) {
            otpCountDownTimer.cancel();
        }
        webServices.dismissDialog();
    }

}