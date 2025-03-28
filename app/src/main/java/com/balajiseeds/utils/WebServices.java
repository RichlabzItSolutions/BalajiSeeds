package com.balajiseeds.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;
import android.widget.Toast;

import com.balajiseeds.admin.models.AcceptPermisoonJson;
import com.balajiseeds.admin.models.AddExpensesHeads.AddExpensesHeadsJson;
import com.balajiseeds.admin.models.AddExpensesHeads.DeleteExpensesHeadsJson;
import com.balajiseeds.admin.models.AddExpensesHeads.EditExpensesHeadsJson;
import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysJson;
import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysResponse;
import com.balajiseeds.admin.models.AddProductVariety.AddProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.AddProductVarietyResponse;
import com.balajiseeds.admin.models.AddProductVariety.DeleteProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.GetProductVarietyDetails;
import com.balajiseeds.admin.models.AddProductVariety.GetProductVarietyResponse;
import com.balajiseeds.admin.models.AddProductVariety.ProductsVarietiesWithProductsJson;
import com.balajiseeds.admin.models.AddProductVariety.UpdateProductVarietyJson;
import com.balajiseeds.admin.models.AdminOrders.AdminOrdersDetails;
import com.balajiseeds.admin.models.AdminOrders.AdminOrdersResponse;
import com.balajiseeds.admin.models.DeleteHolidays.DeleteHolidaysJson;
import com.balajiseeds.admin.models.EditHolidays.EditHolidaysJson;
import com.balajiseeds.admin.models.GetOrder.CancelOrderJson;
import com.balajiseeds.admin.models.GetOrder.GetOrderDetails;
import com.balajiseeds.admin.models.GetOrder.GetOrderReasponse;
import com.balajiseeds.admin.models.GetOrder.UpdateOrderJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysResponse;
import com.balajiseeds.admin.models.ModelAttendance;
import com.balajiseeds.admin.models.ModelChangeStatus;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.admin.models.ModelPackagingSize;
import com.balajiseeds.admin.models.OTP.VerifyOTPJson;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryDetails;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryJson;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryResponse;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountDetails;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountJson;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountResponse;
import com.balajiseeds.employee.models.AddOrder.AddOrderJson;
import com.balajiseeds.employee.models.AddOrder.AddOrderResponse;
import com.balajiseeds.employee.models.DeletePemisoonJson;
import com.balajiseeds.employee.models.GetOrdersForEmployeeJson;
import com.balajiseeds.employee.models.GetPermissionDetails;
import com.balajiseeds.employee.models.GetPermissonResponse;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.employee.models.ModelCart;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.employee.models.ModelMonthlyAttendance;
import com.balajiseeds.employee.models.ModelOrder;
import com.balajiseeds.employee.models.ModelPermissionAWD;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.employee.models.ModelTracking;
import com.balajiseeds.employee.models.ModelsProducts;
import com.balajiseeds.employee.models.MyOrders.EmpOrdersDetails;
import com.balajiseeds.employee.models.MyOrders.EmpOrdersResponse;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.GetProductsVarietiesWithProductsReasponse;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.ProductVarietiesData;
import com.balajiseeds.employee.models.UpdatePermissionJson;
import com.balajiseeds.employee.models.addCartnew.AddCartNewResponse;
import com.balajiseeds.employee.models.addCartnew.AddToCartNewJson;
import com.balajiseeds.employee.models.getCartnew.GetCartDetails;
import com.balajiseeds.employee.models.getCartnew.GetCartNewResponse;
import com.balajiseeds.employee.models.placeOrder.PlaceOrderJson;
import com.balajiseeds.employee.models.placeOrder.PlaceOrderReasponse;
import com.balajiseeds.employee.models.removeQunatity.RemoveQunatityJson;
import com.balajiseeds.employee.models.removeQunatity.RemoveQunatityReasponse;
import com.balajiseeds.models.ModelChangePassword;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelConmmonResponse;
import com.balajiseeds.models.ModelLogin;
import com.balajiseeds.models.ModelRequestOTP;
import com.balajiseeds.models.ModelStates;
import com.balajiseeds.employee.models.ModelVehicleReading;
import com.google.gson.Gson;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebServices {
    private static Toast currentToast;
    Context context;
    CustomWaitDialog cwd;
    API api;
    String token;

    public WebServices(Context context) {
        this.context = context;
        api = RetrofitClient.getRetrofitInstance().create(API.class);
        cwd = new CustomWaitDialog(context);
        token = new SharedPref(context).getString(SharedPref.token);

    }

    public void dismissDialog() {
        if (cwd != null && cwd.isShowing()) {
            cwd.dismiss();
        }
    }

    public void Login(ModelLogin.LoginRequest request, onLogin onLogin) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.login(request).enqueue(new Callback<ModelLogin.LoginResponse>() {
                @Override
                public void onResponse(Call<ModelLogin.LoginResponse> call, Response<ModelLogin.LoginResponse> response) {
                    cwd.dismiss();
                    ModelLogin.LoginResponse loginResponse = response.body();
                    if (loginResponse != null) {
                        if (loginResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(loginResponse.getMessage());
                            onLogin.onLoggedIn(loginResponse.getData());
                        } else {
                            showToast(loginResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelLogin.LoginResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }


    }

    public void AddEmp(ModelEmployee.EmpRequest request, onAddEmp onAddEmp) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addEmp(token, request).enqueue(new Callback<ModelEmployee.EmpResponse>() {
                @Override
                public void onResponse(Call<ModelEmployee.EmpResponse> call, Response<ModelEmployee.EmpResponse> response) {
                    cwd.dismiss();
                    ModelEmployee.EmpResponse empResponse = response.body();
                    if (empResponse != null) {
                        if (empResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(empResponse.getMessage());
                            onAddEmp.onEmpAdded();
                        } else {
                            showToast(empResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelEmployee.EmpResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void UpdateEmp(ModelEmployee.EmpRequest request, onAddEmp onAddEmp) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateEmp(token, request).enqueue(new Callback<ModelEmployee.EmpResponse>() {
                @Override
                public void onResponse(Call<ModelEmployee.EmpResponse> call, Response<ModelEmployee.EmpResponse> response) {
                    cwd.dismiss();
                    ModelEmployee.EmpResponse empResponse = response.body();
                    if (empResponse != null) {
                        if (empResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(empResponse.getMessage());
                            onAddEmp.onEmpAdded();
                        } else {
                            showToast(empResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelEmployee.EmpResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void UpdateHoliday(EditHolidaysJson request,  onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateHolidays(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse updateHolidayResponse = response.body();
                    if (updateHolidayResponse != null) {

                        if (updateHolidayResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(updateHolidayResponse.getMessage());
                            onResponse.response();

                        } else {
                            showToast(updateHolidayResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }




    public void DeleteEmp(ModelEmployee.deleteEmpRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteEmployee(token, request).enqueue(new Callback<ModelEmployee.EmpResponse>() {
                @Override
                public void onResponse(Call<ModelEmployee.EmpResponse> call, Response<ModelEmployee.EmpResponse> response) {
                    cwd.dismiss();
                    ModelEmployee.EmpResponse empResponse = response.body();
                    if (empResponse != null) {
                        if (empResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(empResponse.getMessage());
                            onResponse.response();
                        } else {
                            showToast(empResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelEmployee.EmpResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void GetEmpList(ModelEmployee.EmpListRequest request, onGetEmpList onGetEmpList) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getEmpList(token, request).enqueue(new Callback<ModelEmployee.EmpListResponse>() {
                @Override
                public void onResponse(Call<ModelEmployee.EmpListResponse> call, Response<ModelEmployee.EmpListResponse> response) {
                    cwd.dismiss();
                    ModelEmployee.EmpListResponse empListResponse = response.body();
                    if (empListResponse != null) {
                        if (empListResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(empListResponse.getMessage());
                            onGetEmpList.getEmpList(empListResponse.getEmpList());
                        } else {
                            onGetEmpList.getEmpList(empListResponse.getEmpList());
                            showToast(empListResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelEmployee.EmpListResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void ChangePassword(ModelChangePassword.ChangePasswordRequest request, onPasswordChange onPasswordChange) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.changePass(request).enqueue(new Callback<ModelChangePassword.ChangePasswordResponse>() {
                @Override
                public void onResponse(Call<ModelChangePassword.ChangePasswordResponse> call, Response<ModelChangePassword.ChangePasswordResponse> response) {
                    cwd.dismiss();
                    ModelChangePassword.ChangePasswordResponse changePasswordResponse = response.body();
                    if (changePasswordResponse != null) {
                        if (changePasswordResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(changePasswordResponse.getMessage());
                            onPasswordChange.onPasswordChanged();
                        } else {
                            showToast(changePasswordResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelChangePassword.ChangePasswordResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void GetStates(onGetStates onGetStates) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getStates(token).enqueue(new Callback<ModelStates.StateResponse>() {
                @Override
                public void onResponse(Call<ModelStates.StateResponse> call, Response<ModelStates.StateResponse> response) {
                    cwd.dismiss();
                    ModelStates.StateResponse stateResponse = response.body();
                    if (stateResponse != null) {
                        if (stateResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
//                    showToast(stateResponse.getMessage());
                            onGetStates.getStates(stateResponse.getData());
                        } else {
//                    showToast(stateResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelStates.StateResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void GetCities(ModelCities.CitiesRequest request, onGetCities onGetCities) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getCities(token, request).enqueue(new Callback<ModelCities.CitiesResponse>() {
                @Override
                public void onResponse(Call<ModelCities.CitiesResponse> call, Response<ModelCities.CitiesResponse> response) {
                    cwd.dismiss();
                    ModelCities.CitiesResponse CitiesResponse = response.body();
                    if (CitiesResponse != null) {
                        if (CitiesResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetCities.getCities(CitiesResponse.getData());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelCities.CitiesResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void getAllCities(onGetCities onGetCities) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getAllCities(token).enqueue(new Callback<ModelCities.CitiesResponse>() {
                @Override
                public void onResponse(Call<ModelCities.CitiesResponse> call, Response<ModelCities.CitiesResponse> response) {
                    cwd.dismiss();
                    ModelCities.CitiesResponse CitiesResponse = response.body();
                    if (CitiesResponse != null) {
                        if (CitiesResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetCities.getCities(CitiesResponse.getData());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelCities.CitiesResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void RequestOTP(ModelRequestOTP.OTPRequest request, onRequestOTP onRequestOTP) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.requestOTP(request).enqueue(new Callback<ModelRequestOTP.OTPResponse>() {
                @Override
                public void onResponse(Call<ModelRequestOTP.OTPResponse> call, Response<ModelRequestOTP.OTPResponse> response) {
                    cwd.dismiss();
                    ModelRequestOTP.OTPResponse otpResponse = response.body();
                    if (otpResponse != null) {
                        if (otpResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                         // String  messages = otpResponse.getMessage().substring(0,otpResponse.getMessage().length() - 4);

                            String  messages = otpResponse.getMessage();
                            showToast(messages);
                            onRequestOTP.OTPRequested();
                        } else {
                            showToast(otpResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelRequestOTP.OTPResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void VerifyOTP(VerifyOTPJson request, onVerifyOTP onVerifyOTP) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.verifyOTP(request).enqueue(new Callback<ModelRequestOTP.OTPResponse>() {
                @Override
                public void onResponse(Call<ModelRequestOTP.OTPResponse> call, Response<ModelRequestOTP.OTPResponse> response) {
                    cwd.dismiss();
                    ModelRequestOTP.OTPResponse otpResponse = response.body();
                    if (otpResponse != null) {
                        if (otpResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            String  messages = otpResponse.getMessage();
                            showToast(messages);
                            onVerifyOTP.OTPVerifyed();
                        } else {
                            showToast(otpResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelRequestOTP.OTPResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }
    public void getMonthlyAttendanceEmp(ModelMonthlyAttendance.MonthlyAttendanceRequest request, onGetMonthlyAttEmp onGetMonthlyAttEmp) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getMonthlyAttendanceEmp(token, request).enqueue(new Callback<ModelMonthlyAttendance.MonthlyAttendanceResponse>() {
                @Override
                public void onResponse(Call<ModelMonthlyAttendance.MonthlyAttendanceResponse> call, Response<ModelMonthlyAttendance.MonthlyAttendanceResponse> response) {
                    cwd.dismiss();
                    ModelMonthlyAttendance.MonthlyAttendanceResponse monthlyAttendanceResponse = response.body();
                    if (monthlyAttendanceResponse != null) {
                        if (monthlyAttendanceResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(monthlyAttendanceResponse.getMessage());
                            onGetMonthlyAttEmp.Attendance(monthlyAttendanceResponse);
                        } else {
                            showToast(monthlyAttendanceResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelMonthlyAttendance.MonthlyAttendanceResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void AddLeave(ModelLeaves.Leave request, File file, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            MultipartBody.Part leaveType = MultipartBody.Part.createFormData("leaveType", request.getLeaveType());
            MultipartBody.Part subject = MultipartBody.Part.createFormData("subject", request.getSubject());
            MultipartBody.Part message = MultipartBody.Part.createFormData("message", request.getMessage());
            MultipartBody.Part fromDate = MultipartBody.Part.createFormData("fromDate", request.getFromDate());
            MultipartBody.Part toDate = MultipartBody.Part.createFormData("toDate", request.getToDate());
            RequestBody fileRequestBody = null;
            MultipartBody.Part filePart = null;
            try {
                fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
            } catch (Exception e) {
                e.printStackTrace();
            }
            api.addLeaveEmp(token, leaveType, subject, message, fromDate, toDate, filePart).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(leaveResponse.getMessage());
                            onResponse.response();
                        } else {
                            showToast(leaveResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void updateLeave(ModelLeaves.Leave request, File file, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();

            MultipartBody.Part id = MultipartBody.Part.createFormData("id", request.getId());
            MultipartBody.Part leaveType = MultipartBody.Part.createFormData("leaveType", request.getLeaveType());
            MultipartBody.Part subject = MultipartBody.Part.createFormData("subject", request.getSubject());
            MultipartBody.Part message = MultipartBody.Part.createFormData("message", request.getMessage());
            MultipartBody.Part fromDate = MultipartBody.Part.createFormData("fromDate", request.getFromDate());
            MultipartBody.Part toDate = MultipartBody.Part.createFormData("toDate", request.getToDate());
            RequestBody fileRequestBody;
            MultipartBody.Part filePart = null;
            if (file != null) {
                fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);
            }

            api.updateLeaveEmp(token, id, leaveType, subject, message, fromDate, toDate, filePart).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(leaveResponse.getMessage());
                            onResponse.response();
                        } else {
                            showToast(leaveResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void deleteLeave(ModelLeaves.DeleteLeaveRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteLeaveEmp(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(leaveResponse.getMessage());
                            onResponse.response();
                        } else {
                            showToast(leaveResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void getLeavesListEmp(onFetchLeavesEmp onFetchLeavesEmp) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchLeavesListEmp(token).enqueue(new Callback<ModelLeaves.FetchLeaveResponse>() {
                @Override
                public void onResponse(Call<ModelLeaves.FetchLeaveResponse> call, Response<ModelLeaves.FetchLeaveResponse> response) {
                    cwd.dismiss();
                    ModelLeaves.FetchLeaveResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(leaveResponse.getMessage());
                            onFetchLeavesEmp.fetchedLeaves(leaveResponse.getLeaveList());
                        } else {
                            showToast(leaveResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelLeaves.FetchLeaveResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void getLeavesTypeList(onFetchLeaveType onFetchLeaveType) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchLeaveType(token).enqueue(new Callback<ModelLeaves.LeaveTypeResponse>() {
                @Override
                public void onResponse(Call<ModelLeaves.LeaveTypeResponse> call, Response<ModelLeaves.LeaveTypeResponse> response) {
                    cwd.dismiss();
                    ModelLeaves.LeaveTypeResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
//                    showToast(leaveResponse.getMessage());
                            onFetchLeaveType.fetchedLeaveType(leaveResponse.getData());
                        } else {
                            showToast(leaveResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelLeaves.LeaveTypeResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void AddReading(ModelVehicleReading.addReadingRequest request, File file, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            MultipartBody.Part idPart = MultipartBody.Part.createFormData("id", request.getId());
            MultipartBody.Part vehicleTypePart = MultipartBody.Part.createFormData("vehicleType", request.getVehicleType());
            MultipartBody.Part readingPart = MultipartBody.Part.createFormData("reading", request.getReading());
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part filePart = MultipartBody.Part.createFormData("file", file.getName(), fileRequestBody);

            api.addVehicleReading(token, idPart, vehicleTypePart, readingPart, filePart).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(leaveResponse.getMessage());
                            onResponse.response();
                        } else {
                            showToast(leaveResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void DeleteReading(ModelVehicleReading.deleteReadingRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteVehicleReading(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(leaveResponse.getMessage());
                            onResponse.response();
                        } else {
                            showToast(leaveResponse.getMessage());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void fetchVehicleType(onFetchVehicleType onFetchVehicleType) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchVehicleType(token).enqueue(new Callback<ModelVehicleReading.GetVehicleTypeResponse>() {
                @Override
                public void onResponse(Call<ModelVehicleReading.GetVehicleTypeResponse> call, Response<ModelVehicleReading.GetVehicleTypeResponse> response) {
                    cwd.dismiss();
                    ModelVehicleReading.GetVehicleTypeResponse vehicleTypeResponse = response.body();
                    if (vehicleTypeResponse != null) {
                        if (vehicleTypeResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
//                        showToast(vehicleTypeResponse.getMessage());
                            onFetchVehicleType.fetchedVehicleType(vehicleTypeResponse.getData());
                        } else {
                            showToast(vehicleTypeResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelVehicleReading.GetVehicleTypeResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void fetchVehicleReading(ModelVehicleReading.fetchReadingRequest request, onFetchVehicleReading onFetchVehicleReading) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchVehicleReading(token, request).enqueue(new Callback<ModelVehicleReading.fetchReadingResponse>() {
                @Override
                public void onResponse(Call<ModelVehicleReading.fetchReadingResponse> call, Response<ModelVehicleReading.fetchReadingResponse> response) {
                    cwd.dismiss();
                    ModelVehicleReading.fetchReadingResponse vehicleReadingResponse = response.body();
                    if (vehicleReadingResponse != null) {
                        if (vehicleReadingResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(vehicleReadingResponse.getMessage());
                            onFetchVehicleReading.fetchedVehicleReading(vehicleReadingResponse.getData());
                        } else {
                            showToast(vehicleReadingResponse.getMessage());
                            onFetchVehicleReading.fetchedVehicleReading(vehicleReadingResponse.getData());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelVehicleReading.fetchReadingResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void AddEmpTracking(ModelTracking.TrackingRequest request) {
        if (isNetworkAvailable()) {
            api.addEmpTracking(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    if (response.body() != null) {
                        if (response.body().getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            Log.e("locationupdate", "location updated to database");
                        } else {
                            Log.e("locationupdate", "" + response.body().getMessage());
                        }
                    } else {
                        Log.e("locationupdate", "Body Null");
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void fetchEmpTracking(ModelTracking.fetchTrackingRequest request, onFetchEmpTracking onFetchEmpTracking) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchEmpTracking(token, request).enqueue(new Callback<ModelTracking.fetchTrackingResponse>() {
                @Override
                public void onResponse(Call<ModelTracking.fetchTrackingResponse> call, Response<ModelTracking.fetchTrackingResponse> response) {
                    cwd.dismiss();
                    ModelTracking.fetchTrackingResponse empTrackingResponse = response.body();
                    if (empTrackingResponse != null) {
                        if (empTrackingResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(empTrackingResponse.getMessage());
                            onFetchEmpTracking.fetchedEmpTracking(empTrackingResponse.getData());
                        } else {
                            showToast(empTrackingResponse.getMessage());
                            onFetchEmpTracking.fetchedEmpTracking(empTrackingResponse.getData());
                        }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelTracking.fetchTrackingResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void addActivity(ModelActivity.addActivityRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addActivity(token, request.getTitle(), request.getDescription(), request.getLocation(), request.getImgList()).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void editActivity(ModelActivity.EditActivityRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateActivity(token, request.getId(), request.getTitle(), request.getDescription(), request.getLocation(), request.getImgList(), request.getDeleteList()).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void addExpenses(ModelExpenses.Expenses request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addExpenses(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }
    public void addExpensesNew(ModelExpenses.Expenses request,File file, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            Gson gson = new Gson();
            String json = gson.toJson(request);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), json);
            MultipartBody.Part jsonData = MultipartBody.Part.createFormData("expenses", null, requestBody);
            RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part jsonDataImg = MultipartBody.Part.createFormData("receipt", file.getName(), requestBodyImg);
            api.addExpensesNew(token, jsonData,jsonDataImg).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void editExpenses(ModelExpenses.Expenses request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.editExpenses(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }
    public void editExpensesNew(ModelExpenses.Expenses request,File file, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            Gson gson = new Gson();
            String json = gson.toJson(request);
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), json);
            MultipartBody.Part jsonData = MultipartBody.Part.createFormData("expenses", null, requestBody);
            RequestBody requestBodyImg = RequestBody.create(MediaType.parse("multipart/form-data"), file);
            MultipartBody.Part jsonDataImg = MultipartBody.Part.createFormData("receipt", null, requestBodyImg);
            api.editExpensesNew(token, jsonData,jsonDataImg).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void deleteExpenses(ModelExpenses.Expenses request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteExpenses(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void deletePermisoon(DeletePemisoonJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deletePermisoon(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse deleteHolidaysResponse = response.body();
                    if (deleteHolidaysResponse != null) {
                        showToast(deleteHolidaysResponse.getMessage());
                        if (deleteHolidaysResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }



    public void rejectAdditionalWorkingDay(AcceptPermisoonJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.rejectAdditionalWorkingDay(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse deleteHolidaysResponse = response.body();
                    if (deleteHolidaysResponse != null) {
                        showToast(deleteHolidaysResponse.getMessage());
                        if (deleteHolidaysResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }


    public void acceptAdditionalWorkingDay(AcceptPermisoonJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.acceptAdditionalWorkingDay(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse acceptPermissionResponse = response.body();
                    if (acceptPermissionResponse != null) {
                        showToast(acceptPermissionResponse.getMessage());
                        if (acceptPermissionResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }


    public void deleteHolidays(DeleteHolidaysJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteHolidays(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse deleteHolidaysResponse = response.body();
                    if (deleteHolidaysResponse != null) {
                        showToast(deleteHolidaysResponse.getMessage());
                        if (deleteHolidaysResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void getExpenseHead(onGetExpenseHead onGetExpenseHead) {
        if (isNetworkAvailable()) {
//            cwd.show();
            api.getExpensesHeads(token).enqueue(new Callback<ModelExpenses.ExpensesHeadsResponse>() {
                @Override
                public void onResponse(Call<ModelExpenses.ExpensesHeadsResponse> call, Response<ModelExpenses.ExpensesHeadsResponse> response) {
//                cwd.dismiss();
                    ModelExpenses.ExpensesHeadsResponse expensesHeadsResponse = response.body();
                    if (expensesHeadsResponse != null) {
//                    showToast(expensesHeadsResponse.getMessage());
                        if (expensesHeadsResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetExpenseHead.getExpenseHead(expensesHeadsResponse.getData());
                        }

                    }

                }

                @Override
                public void onFailure(Call<ModelExpenses.ExpensesHeadsResponse> call, Throwable t) {
//                cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void getExpenseHeadAdmin(onGetExpenseHeadAdmin onGetExpenseHeadAdmin) {
        if (isNetworkAvailable()) {
//            cwd.show();
            api.getExpensesHeadsAdmin(token).enqueue(new Callback<ModelExpenses.ExpensesHeadsResponse>() {
                @Override
                public void onResponse(Call<ModelExpenses.ExpensesHeadsResponse> call, Response<ModelExpenses.ExpensesHeadsResponse> response) {
//                cwd.dismiss();
                    ModelExpenses.ExpensesHeadsResponse expensesHeadsResponse = response.body();
                    if (expensesHeadsResponse != null) {
//                    showToast(expensesHeadsResponse.getMessage());
                        if (expensesHeadsResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetExpenseHeadAdmin.getExpenseHeadAdmin(expensesHeadsResponse.getData());
                        }

                    }

                }

                @Override
                public void onFailure(Call<ModelExpenses.ExpensesHeadsResponse> call, Throwable t) {
//                cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void addExpensesHeadsAdmin(AddExpensesHeadsJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addExpensesHeadsAdmin(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse addHolidaysResponse = response.body();
                    if (addHolidaysResponse != null) {
                        showToast(addHolidaysResponse.getMessage());
                        if (addHolidaysResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void updateExpensesHeadsAdmin(EditExpensesHeadsJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateExpensesHeadsAdmin(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse addHolidaysResponse = response.body();
                    if (addHolidaysResponse != null) {
                        showToast(addHolidaysResponse.getMessage());
                        if (addHolidaysResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void deleteExpensesHeadsAdmin(DeleteExpensesHeadsJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteExpensesHeadsAdmin(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse addHolidaysResponse = response.body();
                    if (addHolidaysResponse != null) {
                        showToast(addHolidaysResponse.getMessage());
                        if (addHolidaysResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }



    public void getActivityEmp(ModelActivity.getActivityRequest request, onGetActivityList onGetActivityList) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getActivityEmp(token, request).enqueue(new Callback<ModelActivity.FetchActivityResponse>() {
                @Override
                public void onResponse(Call<ModelActivity.FetchActivityResponse> call, Response<ModelActivity.FetchActivityResponse> response) {
                    cwd.dismiss();
                    ModelActivity.FetchActivityResponse activityEmpResponse = response.body();
                    if (activityEmpResponse != null) {
                        showToast(activityEmpResponse.getMessage());
                        if (activityEmpResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetActivityList.getActivityList(activityEmpResponse.getData());
                        } else {
                            onGetActivityList.getActivityList(activityEmpResponse.getData());
                        }
                    }

                }

                @Override
                public void onFailure(Call<ModelActivity.FetchActivityResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void deleteActivityEmp(ModelActivity.DeleteRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteActivityEmp(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void getExpenses(ModelExpenses.getExpensesRequest request, onGetExpenses onGetExpenses) {
        if (isNetworkAvailable()) {
            cwd.show();

            Log.d("token", token);
            api.getExpensesEmp(token, request).enqueue(new Callback<ModelExpenses.getExpensesResponse>() {
                @Override
                public void onResponse(Call<ModelExpenses.getExpensesResponse> call, Response<ModelExpenses.getExpensesResponse> response) {
                    cwd.dismiss();
                    ModelExpenses.getExpensesResponse expensesResponse = response.body();
                    if (expensesResponse != null) {
                        showToast(expensesResponse.getMessage());
//                    if(expensesResponse.status.equalsIgnoreCase(Constants.SUCCESS)){
                        onGetExpenses.getExpenses(expensesResponse.getData());
//                    }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelExpenses.getExpensesResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void addHolidays(AddHolidaysJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addHolidays(token, request).enqueue(new Callback<AddHolidaysResponse>() {
                @Override
                public void onResponse(Call<AddHolidaysResponse> call, Response<AddHolidaysResponse> response) {
                    cwd.dismiss();
                    AddHolidaysResponse addHolidaysResponse = response.body();
                    if (addHolidaysResponse != null) {
                        showToast(addHolidaysResponse.getMessage());
                        if (addHolidaysResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }


    public void addProductVariety(AddProductVarietyJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addProductVariety(token, request).enqueue(new Callback<AddProductVarietyResponse>() {
                @Override
                public void onResponse(Call<AddProductVarietyResponse> call, Response<AddProductVarietyResponse> response) {
                    cwd.dismiss();
                    AddProductVarietyResponse addProductVarietyResponse = response.body();
                    if (addProductVarietyResponse != null) {
                        showToast(addProductVarietyResponse.getMessage());
                        if (addProductVarietyResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddProductVarietyResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }


    public void updateProductVariety(UpdateProductVarietyJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateProductVariety(token, request).enqueue(new Callback<AddProductVarietyResponse>() {
                @Override
                public void onResponse(Call<AddProductVarietyResponse> call, Response<AddProductVarietyResponse> response) {
                    cwd.dismiss();
                    AddProductVarietyResponse addProductVarietyResponse = response.body();
                    if (addProductVarietyResponse != null) {
                        showToast(addProductVarietyResponse.getMessage());
                        if (addProductVarietyResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddProductVarietyResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }


    public void deleteProductVariety(DeleteProductVarietyJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteProductVariety(token, request).enqueue(new Callback<AddProductVarietyResponse>() {
                @Override
                public void onResponse(Call<AddProductVarietyResponse> call, Response<AddProductVarietyResponse> response) {
                    cwd.dismiss();
                    AddProductVarietyResponse addProductVarietyResponse = response.body();
                    if (addProductVarietyResponse != null) {
                        showToast(addProductVarietyResponse.getMessage());
                        if (addProductVarietyResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddProductVarietyResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }



    public void addOrders(AddOrderJson request, onResponseAddOrder onResponseAddOrder) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addOrders(token, request).enqueue(new Callback<AddOrderResponse>() {
                @Override
                public void onResponse(Call<AddOrderResponse> call, Response<AddOrderResponse> response) {
                    cwd.dismiss();
                    AddOrderResponse addOrderResponse = response.body();
                   // if (addOrderResponse != null) {

                        if (addOrderResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(addOrderResponse.getMessage());
                            String partyId = addOrderResponse.getPartyId();
                            onResponseAddOrder.responseAddOrder(partyId);
                        }
                        else {
                            showToast(addOrderResponse.getMessage());

                        }
                   // }
                }

                @Override
                public void onFailure(Call<AddOrderResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }



    public void getProductVariety( onGetProductVariety onGetProductVariety) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getProductVariety(token).enqueue(new Callback<GetProductVarietyResponse>() {
                @Override
                public void onResponse(Call<GetProductVarietyResponse> call, Response<GetProductVarietyResponse> response) {
                    cwd.dismiss();
                    GetProductVarietyResponse getProductVarietyResponse = response.body();
                    if (getProductVarietyResponse != null) {
                        //showToast(getProductVarietyResponse.getMessage());
//                    if(expensesResponse.status.equalsIgnoreCase(Constants.SUCCESS)){
                       // onGetHolidays.getHolidays(getProductVarietyResponse.getData());
//                    }

                        if(getProductVarietyResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                            onGetProductVariety.getProductVariety(getProductVarietyResponse.getData());
                        }
                       // onGetProductVariety.getProductVariety(getProductVarietyResponse.getData());
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetProductVarietyResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void getHolidays(GetHolidaysJson request, onGetHolidays onGetHolidays) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getHolidays(token, request).enqueue(new Callback<GetHolidaysResponse>() {
                @Override
                public void onResponse(Call<GetHolidaysResponse> call, Response<GetHolidaysResponse> response) {
                    cwd.dismiss();
                    GetHolidaysResponse holidaysResponse = response.body();
                    if (holidaysResponse != null) {
                        showToast(holidaysResponse.getMessage());
//                    if(expensesResponse.status.equalsIgnoreCase(Constants.SUCCESS)){
                        onGetHolidays.getHolidays(holidaysResponse.getData());
//                    }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void geEmployeHolidays( onGetEmpHolidays onGetEmpHolidays) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getEmployeesHolidays(token).enqueue(new Callback<GetHolidaysResponse>() {
                @Override
                public void onResponse(Call<GetHolidaysResponse> call, Response<GetHolidaysResponse> response) {
                    cwd.dismiss();
                    GetHolidaysResponse holidaysEmpResponse = response.body();
                    if (holidaysEmpResponse != null) {
                        showToast(holidaysEmpResponse.getMessage());
//                    if(expensesResponse.status.equalsIgnoreCase(Constants.SUCCESS)){
                        onGetEmpHolidays.getEmpHolidays(holidaysEmpResponse.getData());
//                    }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetHolidaysResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void adminFetchLeaveList(ModelLeaves.adminFetchLeaveRequest request, onFetchLeavesEmp onFetchLeavesEmp) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.adminFetchLeaveList(token, request).enqueue(new Callback<ModelLeaves.FetchLeaveResponse>() {
                @Override
                public void onResponse(Call<ModelLeaves.FetchLeaveResponse> call, Response<ModelLeaves.FetchLeaveResponse> response) {
                    cwd.dismiss();
                    ModelLeaves.FetchLeaveResponse leaveResponse = response.body();
                    if (leaveResponse != null) {
                        showToast(leaveResponse.getMessage());
                        if (leaveResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onFetchLeavesEmp.fetchedLeaves(leaveResponse.getLeaveList());
                        } else {
                            onFetchLeavesEmp.fetchedLeaves(leaveResponse.getLeaveList());
                        }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelLeaves.FetchLeaveResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void adminRejectLeave(ModelLeaves.adminRejectLeaveRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.adminRejectLeave(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void adminApproveLeave(ModelLeaves.DeleteLeaveRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.adminApproveLeave(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void changeEmpStatus(ModelChangeStatus request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.changeEmployeeStatus(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void fetchActivityAdmin(ModelActivity.adminGetActivityRequest request, onGetActivityList onGetActivityList) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.adminFetchActivityList(token, request).enqueue(new Callback<ModelActivity.FetchActivityResponse>() {
                @Override
                public void onResponse(Call<ModelActivity.FetchActivityResponse> call, Response<ModelActivity.FetchActivityResponse> response) {
                    cwd.dismiss();
                    ModelActivity.FetchActivityResponse fetchActivityResponse = response.body();
                    if (fetchActivityResponse != null) {
                        if (fetchActivityResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetActivityList.getActivityList(fetchActivityResponse.getData());
                        } else {
                            onGetActivityList.getActivityList(fetchActivityResponse.getData());
                        }
                    } else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelActivity.FetchActivityResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void fetchVehicleReadingAdmin(ModelVehicleReading.fetchReadingRequest request, onFetchVehicleReading onFetchVehicleReading) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.adminfetchVehicleReading(token, request).enqueue(new Callback<ModelVehicleReading.fetchReadingResponse>() {
                @Override
                public void onResponse(Call<ModelVehicleReading.fetchReadingResponse> call, Response<ModelVehicleReading.fetchReadingResponse> response) {
                    cwd.dismiss();
                    ModelVehicleReading.fetchReadingResponse vehicleReadingResponse = response.body();
                    if (vehicleReadingResponse != null) {
                        if (vehicleReadingResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(vehicleReadingResponse.getMessage());
                            onFetchVehicleReading.fetchedVehicleReading(vehicleReadingResponse.getData());
                        } else {
                            showToast(vehicleReadingResponse.getMessage());
                            onFetchVehicleReading.fetchedVehicleReading(vehicleReadingResponse.getData());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelVehicleReading.fetchReadingResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void adminFetchMonthlyAtt(ModelMonthlyAttendance.MonthlyAttendanceRequest request, onGetMonthlyAttendanceAdmin onGetMonthlyAttendanceAdmin) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.adminfetchMonthlyAttendance(token, request).enqueue(new Callback<ModelMonthlyAttendance.AdminGetMonthlyAttendanceResponse>() {
                @Override
                public void onResponse(Call<ModelMonthlyAttendance.AdminGetMonthlyAttendanceResponse> call, Response<ModelMonthlyAttendance.AdminGetMonthlyAttendanceResponse> response) {
                    cwd.dismiss();
                    ModelMonthlyAttendance.AdminGetMonthlyAttendanceResponse monthlyAttendanceResponse = response.body();
                    if (monthlyAttendanceResponse != null) {
                        showToast(monthlyAttendanceResponse.getMessage());
                        if (monthlyAttendanceResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetMonthlyAttendanceAdmin.getAttendance(monthlyAttendanceResponse.getUserAttendance());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelMonthlyAttendance.AdminGetMonthlyAttendanceResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }


    }

    public void cancelLeaveEmp(ModelLeaves.cancelLeaveRequest request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.cancelLeaveEmp(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void getDailyAttendance(ModelAttendance.getDailyAttendanceRequest request, oGetDailyAttendance onGetDailyAttendance) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.adminGetDailyAttendance(token, request).enqueue(new Callback<ModelAttendance.GetDailyAttendanceResponse>() {
                @Override
                public void onResponse(Call<ModelAttendance.GetDailyAttendanceResponse> call, Response<ModelAttendance.GetDailyAttendanceResponse> response) {
                    cwd.dismiss();
                    ModelAttendance.GetDailyAttendanceResponse dailyAttendanceResponse = response.body();
                    if (dailyAttendanceResponse != null) {
                        showToast(dailyAttendanceResponse.getMessage());
                        if (dailyAttendanceResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetDailyAttendance.getDailyAttendance(dailyAttendanceResponse.getData());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelAttendance.GetDailyAttendanceResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void addProduct(List<MultipartBody.Part> imagelist, MultipartBody.Part jsonData, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addProduct(token, jsonData, imagelist).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    public void addProductNew(RequestBody  productData, MultipartBody.Part[] imagelist,  onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addProductNew(token, productData, imagelist).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

    /*public void updateProduct(List<MultipartBody.Part> imagelist, MultipartBody.Part jsonData, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateProducts(token, jsonData, imagelist).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }*/


   /* public void updateProductNew(RequestBody  productData, MultipartBody.Part[] imagelist,  onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateProductsNew(token, productData, imagelist).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }*/

    public void updateProductNew(RequestBody  productData, MultipartBody.Part[] imagelist,  onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateProductsNew(token, productData, imagelist).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }






    public void deleteProduct(String id, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteProduct(token, new ModelProducts.deleteProduct(id)).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void deleteProductNew(String id, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.deleteProductNew(token, new ModelProducts.deleteProduct(id)).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse != null) {
                        showToast(conmmonResponse.getMessage());
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }



 /*   public void getProducts(ModelProducts.GetProductRequest request, onGetProduct onGetProduct) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchProducts(token, request).enqueue(new Callback<ModelProducts.GetProductResponse>() {
                @Override
                public void onResponse(Call<ModelProducts.GetProductResponse> call, Response<ModelProducts.GetProductResponse> response) {
                    cwd.dismiss();
                    ModelProducts.GetProductResponse productResponse = response.body();
                    if (productResponse != null) {

                        if (productResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetProduct.getProduct(productResponse.getData());
                        }else {
                            showToast(productResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelProducts.GetProductResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }*/



    public void getProductsNew(ModelsProducts.GetProductRequest request, onGetProducts onGetProducts) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchProductsNew(token, request).enqueue(new Callback<ModelsProducts.GetProductResponse>() {
                @Override
                public void onResponse(Call<ModelsProducts.GetProductResponse> call, Response<ModelsProducts.GetProductResponse> response) {
                    cwd.dismiss();
                    ModelsProducts.GetProductResponse productResponse = response.body();
                    if (productResponse != null) {

                        if (productResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetProducts.getProducts(productResponse.getData());
                        }else {
                            showToast(productResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelsProducts.GetProductResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void fetchProductsVarietiesWithProducts(ProductsVarietiesWithProductsJson request, onGetProduct onGetProduct) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchProductsVarietiesWithProducts(token, request).enqueue(new Callback<GetProductsVarietiesWithProductsReasponse>() {
                @Override
                public void onResponse(Call<GetProductsVarietiesWithProductsReasponse> call, Response<GetProductsVarietiesWithProductsReasponse> response) {
                    cwd.dismiss();
                    GetProductsVarietiesWithProductsReasponse productResponse = response.body();
                   // if (productResponse != null) {

                  String totalItems =  productResponse.getTotalItems();

                        if (productResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetProduct.getProduct(totalItems,productResponse.getData());
                        }else {
                          //  onGetProduct.getProduct(productResponse.getData().clear());
                            onGetProduct.getProduct(totalItems,productResponse.getData());
                            showToast(productResponse.getMessage());
                        }

                   // }
                }

                @Override
                public void onFailure(Call<GetProductsVarietiesWithProductsReasponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

   /* public void getPackagingSize(onGetPackagingSize onGetPackagingSize) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getPackagingSize(token).enqueue(new Callback<ModelPackagingSize.GetPackagingSizeResponse>() {
                @Override
                public void onResponse(Call<ModelPackagingSize.GetPackagingSizeResponse> call, Response<ModelPackagingSize.GetPackagingSizeResponse> response) {
                    cwd.dismiss();
                    ModelPackagingSize.GetPackagingSizeResponse packagingSizeResponse = response.body();
                    if (packagingSizeResponse != null) {

                        if (packagingSizeResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetPackagingSize.gotPackagingSize(packagingSizeResponse.getPackagingSizes());
                        } else {
                            showToast(packagingSizeResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelPackagingSize.GetPackagingSizeResponse> call, Throwable t) {
                    cwd.dismiss();
                    t.printStackTrace();
                    showToast("Something went wrong");
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }*/


    public void addToCart(ModelCart.addtoCart request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addToCart(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse!= null) {
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                        else {
                            showToast(conmmonResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void addCartNew(AddToCartNewJson request, onResponses onResponses) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.addCartNew(token, request).enqueue(new Callback<AddCartNewResponse>() {
                @Override
                public void onResponse(Call<AddCartNewResponse> call, Response<AddCartNewResponse> response) {
                    cwd.dismiss();
                    AddCartNewResponse conmmonResponse = response.body();
                    if (conmmonResponse!= null) {
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            int totalItems = Integer.parseInt(conmmonResponse.getTotalItems()); // Assuming API response has `totalItems` field
                            String cartId = conmmonResponse.getCartId();     // Assuming API response has `cartId` field

                            // Process totalItems and cartId
                            Log.d("Cart Info", "Total Items: " + totalItems + ", Cart ID: " + cartId);

                            onResponses.responses(totalItems, cartId);
                        } else {
                            showToast(conmmonResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<AddCartNewResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void removeFromCart(ModelCart.deleteFromCart request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.dismiss();
            api.deleteFromCart(token, request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse conmmonResponse = response.body();
                    if (conmmonResponse!= null) {
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                        else {
                            showToast(conmmonResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void removeQunatity(RemoveQunatityJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.dismiss();
            api.removeQunatity(token, request).enqueue(new Callback<RemoveQunatityReasponse>() {
                @Override
                public void onResponse(Call<RemoveQunatityReasponse> call, Response<RemoveQunatityReasponse> response) {
                    cwd.dismiss();
                    RemoveQunatityReasponse conmmonResponse = response.body();
                    if (conmmonResponse!= null) {
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                        else {
                            showToast(conmmonResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RemoveQunatityReasponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }


    public void addQunatity(RemoveQunatityJson request, onResponse onResponse) {
        if (isNetworkAvailable()) {
            cwd.dismiss();
            api.addQunatity(token, request).enqueue(new Callback<RemoveQunatityReasponse>() {
                @Override
                public void onResponse(Call<RemoveQunatityReasponse> call, Response<RemoveQunatityReasponse> response) {
                    cwd.dismiss();
                    RemoveQunatityReasponse conmmonResponse = response.body();
                    if (conmmonResponse!= null) {
                        if (conmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                        }
                        else {
                            showToast(conmmonResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<RemoveQunatityReasponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            showToast("No internet connection available");
        }
    }

   /* public void getCart(onGetCart onGetCart){
        if (isNetworkAvailable()) {
            cwd.show();
            api.getCart(token).enqueue(new Callback<ModelCart.getCartResponse>() {
                @Override
                public void onResponse(Call<ModelCart.getCartResponse> call, Response<ModelCart.getCartResponse> response) {
                    cwd.dismiss();
                    ModelCart.getCartResponse cartResponse = response.body();
                    if (cartResponse!= null) {
                        if (cartResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetCart.getCart(cartResponse.getCartList());
                        }
                        else {
                            onGetCart.getCart(cartResponse.getCartList());
                            showToast(cartResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<ModelCart.getCartResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }*/


    public void getTheCart(PlaceOrderJson request, onGetCartNew onGetCartNew){
        if (isNetworkAvailable()) {
            cwd.show();
            api.getTheCart(token, request).enqueue(new Callback<GetCartNewResponse>() {
                @Override
                public void onResponse(Call<GetCartNewResponse> call, Response<GetCartNewResponse> response) {
                    cwd.dismiss();
                    GetCartNewResponse cartResponse = response.body();
                    if (cartResponse!= null) {
                        if (cartResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetCartNew.getCartNew(cartResponse.getCart());
                        }
                        else {
                            onGetCartNew.getCartNew(cartResponse.getCart());
                            showToast(cartResponse.getMessage());
                        }
                    }
                }

                @Override
                public void onFailure(Call<GetCartNewResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        } else {
            showToast("No internet connection available");
        }

    }

    public void getProductById(String productId,onGetProductById onGetProductById) {
        if (isNetworkAvailable()) {
            cwd.show();
            api.getProductById(token, new ModelProducts.getProductByIdRequest(productId)).enqueue(new Callback<ModelProducts.getProductByIdResponse>() {
                @Override
                public void onResponse(Call<ModelProducts.getProductByIdResponse> call, Response<ModelProducts.getProductByIdResponse> response) {
                    cwd.dismiss();
                    ModelProducts.getProductByIdResponse productByIdResponse = response.body();
                    if(productByIdResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)){
                        onGetProductById.getProductById(productByIdResponse.getData());
                    }
                    else {
                        showToast(productByIdResponse.getMessage());
                    }
                }

                @Override
                public void onFailure(Call<ModelProducts.getProductByIdResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        else {
            showToast("No internet connection available");
        }
    }

    public void addOrder(onResponse onResponse){
        if (isNetworkAvailable()) {
            cwd.show();
            api.addOrder(token).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse modelConmmonResponse= response.body();
                    if(modelConmmonResponse!=null) {
                        if (modelConmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                            showToast(modelConmmonResponse.getMessage());
                        } else {
                            showToast(modelConmmonResponse.getMessage());
                        }
                    }else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }



    public void placeTheOrder(PlaceOrderJson request, onResponsePlace onResponsePlace){
        if (isNetworkAvailable()) {
            cwd.show();
            api.placeTheOrder(token, request).enqueue(new Callback<PlaceOrderReasponse>() {
                @Override
                public void onResponse(Call<PlaceOrderReasponse> call, Response<PlaceOrderReasponse> response) {
                    cwd.dismiss();
                    PlaceOrderReasponse modelConmmonResponse= response.body();
                    if(modelConmmonResponse!=null) {
                        if (modelConmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            String orderId = modelConmmonResponse.getOrderId();
                            String orderNumber = modelConmmonResponse.getOrderNumber();
                            onResponsePlace.responsePlace( orderId,  orderNumber);
                            showToast(modelConmmonResponse.getMessage());
                        } else {
                            showToast(modelConmmonResponse.getMessage());
                        }
                    }else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<PlaceOrderReasponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }


   /* public void getOrder( onGetOrders onGetOrders){
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchOrdersbyUserId(token , new GetOrdersForEmployeeJson("", "")).enqueue(new Callback<ModelOrder.fetchOrderResponse>() {
                @Override
                public void onResponse(Call<ModelOrder.fetchOrderResponse> call, Response<ModelOrder.fetchOrderResponse> response) {
                    cwd.dismiss();
                    ModelOrder.fetchOrderResponse fetchOrderResponse = response.body();
                    if (fetchOrderResponse!= null) {
                        if (fetchOrderResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetOrders.getOrders(fetchOrderResponse.getOrderList());
                        }
                        else {
                            onGetOrders.getOrders(fetchOrderResponse.getOrderList());
                            showToast(fetchOrderResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelOrder.fetchOrderResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }*/


    public void EmpOrders( onGetOrdersEmp onGetOrdersEmp){
        if (isNetworkAvailable()) {
            cwd.show();
            api.EmpOrders(token).enqueue(new Callback<EmpOrdersResponse>() {
                @Override
                public void onResponse(Call<EmpOrdersResponse> call, Response<EmpOrdersResponse> response) {
                    cwd.dismiss();
                    EmpOrdersResponse fetchOrderResponse = response.body();
                    if (fetchOrderResponse!= null) {
                        if (fetchOrderResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetOrdersEmp.getOrdersEmp(fetchOrderResponse.getData());
                        }
                        else {
                            onGetOrdersEmp.getOrdersEmp(fetchOrderResponse.getData());
                         //   showToast(fetchOrderResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<EmpOrdersResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }



    public void AdminOrders( onGetOrdersAdminNew onGetOrdersAdminNew){
        if (isNetworkAvailable()) {
            cwd.show();
            api.AdminOrders(token).enqueue(new Callback<AdminOrdersResponse>() {
                @Override
                public void onResponse(Call<AdminOrdersResponse> call, Response<AdminOrdersResponse> response) {
                    cwd.dismiss();
                    AdminOrdersResponse adminOrdersResponse = response.body();
                    if (adminOrdersResponse!= null) {
                        if (adminOrdersResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetOrdersAdminNew.getOrdersAdminNew(adminOrdersResponse.getData());
                            showToast(adminOrdersResponse.getMessage());
                        }
                        else {
                            onGetOrdersAdminNew.getOrdersAdminNew(adminOrdersResponse.getData());
                            showToast(adminOrdersResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<AdminOrdersResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }

   /* public void getOrdersAdmin( onGetOrdersAdmin onGetOrdersAdmin){
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchOrderAdmin(token , new GetOrdersForEmployeeJson("", "")).enqueue(new Callback<GetOrderReasponse>() {
                @Override
                public void onResponse(Call<GetOrderReasponse> call, Response<GetOrderReasponse> response) {
                    cwd.dismiss();
                    GetOrderReasponse fetchOrderResponse = response.body();
                    if (fetchOrderResponse!= null) {
                        if (fetchOrderResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetOrdersAdmin.getOrdersAdmin(fetchOrderResponse.getOrderList());
                        }
                        else {
                            onGetOrdersAdmin.getOrdersAdmin(fetchOrderResponse.getOrderList());
                            showToast(fetchOrderResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetOrderReasponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }*/


    public void paidHistory(PaidHistoryJson request, onGetPaidHistoryAdmin onGetPaidHistoryAdmin){
        if (isNetworkAvailable()) {
            cwd.show();
            api.paidHistory(token , request).enqueue(new Callback<PaidHistoryResponse>() {
                @Override
                public void onResponse(Call<PaidHistoryResponse> call, Response<PaidHistoryResponse> response) {
                    cwd.dismiss();
                    PaidHistoryResponse paidHistoryResponse = response.body();
                    if (paidHistoryResponse!= null) {
                        if (paidHistoryResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetPaidHistoryAdmin.getPaidHistoryAdmin(paidHistoryResponse.getData());
                        }
                        else {
                          //  onGetPaidHistoryAdmin.getPaidHistoryAdmin(paidHistoryResponse.getData());
                           // showToast(paidHistoryResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<PaidHistoryResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }



    public void updateAmount(UpdateAmountJson request, onGetUpdateAmountAdmin onGetUpdateAmountAdmin){
        if (isNetworkAvailable()) {
            cwd.show();
            api.updateAmount(token ,request).enqueue(new Callback<UpdateAmountResponse>() {
                @Override
                public void onResponse(Call<UpdateAmountResponse> call, Response<UpdateAmountResponse> response) {
                    cwd.dismiss();
                    UpdateAmountResponse updateAmountResponse = response.body();
                   // if (updateAmountResponse!= null) {
                        if (updateAmountResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetUpdateAmountAdmin.getUpdateAmountAdmin(updateAmountResponse.getData());
                            showToast(updateAmountResponse.getMessage());
                        }
                        else {
                            onGetUpdateAmountAdmin.getUpdateAmountAdmin(updateAmountResponse.getData());
                            // showToast(paidHistoryResponse.getMessage());
                        }
                   /* }
                    else {
                        showToast("Something went wrong");
                    }*/
                }

                @Override
                public void onFailure(Call<UpdateAmountResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }

    public void OrderAdminCancel(CancelOrderJson request, onResponse onResponse){
        if (isNetworkAvailable()) {
            cwd.show();
            api.OrderAdminCancel(token ,request).enqueue(new Callback<GetOrderReasponse>() {
                @Override
                public void onResponse(Call<GetOrderReasponse> call, Response<GetOrderReasponse> response) {
                    cwd.dismiss();
                    GetOrderReasponse fetchOrderResponse = response.body();
                    if (fetchOrderResponse!= null) {
                        if (fetchOrderResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(fetchOrderResponse.getMessage());

                        }
                        else {
                            showToast(fetchOrderResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetOrderReasponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }


    public void OrderAdminUpdate(UpdateOrderJson request, onResponse onResponse){
        if (isNetworkAvailable()) {
            cwd.show();
            api.OrderAdminUpdate(token ,request).enqueue(new Callback<GetOrderReasponse>() {
                @Override
                public void onResponse(Call<GetOrderReasponse> call, Response<GetOrderReasponse> response) {
                    cwd.dismiss();
                    GetOrderReasponse fetchOrderResponse = response.body();
                    if (fetchOrderResponse!= null) {
                        if (fetchOrderResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            showToast(fetchOrderResponse.getMessage());

                        }
                        else {
                            showToast(fetchOrderResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetOrderReasponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }





    public void getOrderDetails(String orderId,onGetOrders onGetOrders){
        if (isNetworkAvailable()) {
            cwd.show();
            api.fetchOrderByOrderId(token,new ModelOrder.OrderByIdRequest(orderId)).enqueue(new Callback<ModelOrder.fetchOrderResponse>() {
                @Override
                public void onResponse(Call<ModelOrder.fetchOrderResponse> call, Response<ModelOrder.fetchOrderResponse> response) {
                    cwd.dismiss();
                    ModelOrder.fetchOrderResponse fetchOrderResponse = response.body();
                    if (fetchOrderResponse!= null) {
                        if (fetchOrderResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetOrders.getOrders(fetchOrderResponse.getOrderList());
                        }
                        else {
                            onGetOrders.getOrders(fetchOrderResponse.getOrderList());
                            showToast(fetchOrderResponse.getMessage());
                        }
                    }
                    else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelOrder.fetchOrderResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });

        }
        else {
            showToast("No internet connection available");
        }
    }

    public void requestAdditionalWorkingDay(ModelPermissionAWD.awd request,onResponse onResponse){
        if(isNetworkAvailable()){
            cwd.show();
            api.requestAdditionalWorkingDay(token,request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse modelConmmonResponse= response.body();
                    if(modelConmmonResponse!=null) {
                        if (modelConmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                            showToast(modelConmmonResponse.getMessage());
                        } else {
                            showToast(modelConmmonResponse.getMessage());
                        }
                    }else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        else {
            showToast("No internet connection available");
        }
    }
    public void editAdditionalWorkingDay(UpdatePermissionJson request, onResponse onResponse){
        if(isNetworkAvailable()){
            cwd.show();
            api.updateRequestAdditionalWorkingDay(token,request).enqueue(new Callback<ModelConmmonResponse>() {
                @Override
                public void onResponse(Call<ModelConmmonResponse> call, Response<ModelConmmonResponse> response) {
                    cwd.dismiss();
                    ModelConmmonResponse modelConmmonResponse= response.body();
                    if(modelConmmonResponse!=null) {
                        if (modelConmmonResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onResponse.response();
                            showToast(modelConmmonResponse.getMessage());
                        } else {
                            showToast(modelConmmonResponse.getMessage());
                        }
                    }else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<ModelConmmonResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        else {
            showToast("No internet connection available");
        }
    }

    public void adminAdditionalWorkingDayRequest(onGetAwd onGetAwd){
        if(isNetworkAvailable()){
            cwd.show();
            api.adminAdditionalWorkingDayRequest(token).enqueue(new Callback<GetPermissonResponse>() {
                @Override
                public void onResponse(Call<GetPermissonResponse> call, Response<GetPermissonResponse> response) {
                    cwd.dismiss();
                    GetPermissonResponse awdResponse= response.body();
                    if(awdResponse!=null) {
                        if (awdResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetAwd.getAwd(awdResponse.getData());
                        } else {
                            onGetAwd.getAwd(awdResponse.getData());
                            showToast(awdResponse.getMessage());
                        }
                    }else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetPermissonResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        else {
            showToast("No internet connection available");
        }

    }



    public void fetchAdditionalWorkingRequest(onGetAwd onGetAwd){
        if(isNetworkAvailable()){
            cwd.show();
            api.fetchRequestAdditionalWorkingDay(token).enqueue(new Callback<GetPermissonResponse>() {
                @Override
                public void onResponse(Call<GetPermissonResponse> call, Response<GetPermissonResponse> response) {
                    cwd.dismiss();
                    GetPermissonResponse awdResponse= response.body();
                    if(awdResponse!=null) {
                        if (awdResponse.getStatus().equalsIgnoreCase(Constants.SUCCESS)) {
                            onGetAwd.getAwd(awdResponse.getData());
                        } else {
                            onGetAwd.getAwd(awdResponse.getData());
                            showToast(awdResponse.getMessage());
                        }
                    }else {
                        showToast("Something went wrong");
                    }
                }

                @Override
                public void onFailure(Call<GetPermissonResponse> call, Throwable t) {
                    cwd.dismiss();
                    showToast(t.getMessage());
                    t.printStackTrace();
                }
            });
        }
        else {
            showToast("No internet connection available");
        }

    }


    public void showToast(String msg) {
        Toast.makeText(context, "" + msg, Toast.LENGTH_SHORT).show();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager != null) {
            NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
            return activeNetworkInfo != null && activeNetworkInfo.isConnected();
        }
        return false;
    }

    public interface onGetAwd{
        public void getAwd(List<GetPermissionDetails> awd);
    }


    public interface onGetProductVariety{
        public void getProductVariety(List<GetProductVarietyDetails> getProductVarietyDetailsList);
    }

    public interface onGetOrders{
        public void getOrders(List<ModelOrder.Order> orderList);
    }

    public interface onGetOrdersEmp{
        public void getOrdersEmp(List<EmpOrdersDetails> orderList);
    }

    public interface onGetOrdersAdminNew{
        public void getOrdersAdminNew(List<AdminOrdersDetails> orderList);
    }

        public interface onGetOrdersAdmin{
        public void getOrdersAdmin(List<GetOrderDetails> orderList);
    }


    public interface onGetPaidHistoryAdmin{
        public void getPaidHistoryAdmin(List<PaidHistoryDetails> paidHistoryDetailsList);
    }


    public interface onGetUpdateAmountAdmin{
        public void getUpdateAmountAdmin(UpdateAmountDetails updateAmountDetails);
    }

    public interface onGetProductById{
        public void getProductById(ModelProducts.Product product);
    }
    public interface onGetCart{
        public void getCart(List<ModelCart.Cart> cartList);
    }

    public interface onGetCartNew{
        public void getCartNew(List<GetCartDetails> cartList);
    }

    public interface onGetPackagingSize {
        public void gotPackagingSize(List<ModelPackagingSize.PackagingSize> packagingSizeList);
    }

    public interface onGetProducts {
        public void getProducts(List<ModelsProducts.Product> productList);
    }

    public interface onGetProduct {
        public void getProduct(String totalItems,  List<ProductVarietiesData> productList);
    }

    public interface oGetDailyAttendance {
        public void getDailyAttendance(List<ModelAttendance.DailyAttendance> dailyAttendanceList);
    }

    public interface onGetMonthlyAttendanceAdmin {
        public void getAttendance(List<ModelMonthlyAttendance.UserAttendance> attendanceList);
    }

    public interface onGetExpenses {
        public void getExpenses(List<ModelExpenses.Expenses> expenseList);
    }

    public interface onGetHolidays {
        public void getHolidays(List<GetHolidaysDetails> holidaysList);
    }

    public interface onAddHolidays {
        public void addHolidays(List<GetHolidaysDetails> holidaysList);
    }

    public interface onGetEmpHolidays {
        public void getEmpHolidays(List<GetHolidaysDetails> holidaysEmpList);
    }

    public interface onGetActivityList {
        public void getActivityList(List<ModelActivity.Data> activityList);
    }

    public interface onGetExpenseHead {
        public void getExpenseHead(List<ModelExpenses.ExpensesHead> expenseHeadList);
    }


    public interface onGetExpenseHeadAdmin {
        public void getExpenseHeadAdmin(List<ModelExpenses.ExpensesHead> expenseHeadAdminList);
    }

    public interface onFetchEmpTracking {
        public void fetchedEmpTracking(List<ModelTracking.trackingData> empTrackingList);
    }

    public interface onFetchVehicleReading {
        public void fetchedVehicleReading(List<ModelVehicleReading.Reading> vehicleReadingList);
    }

    public interface onFetchVehicleType {
        public void fetchedVehicleType(List<ModelVehicleReading.VehicleType> vehicleTypeList);
    }

    public interface onFetchLeaveType {
        public void fetchedLeaveType(List<ModelLeaves.LeaveType> leaveTypeList);
    }

    public interface onFetchLeavesEmp {
        public void fetchedLeaves(List<ModelLeaves.Leave> leaveList);
    }

    public interface onGetMonthlyAttEmp {
        public void Attendance(ModelMonthlyAttendance.MonthlyAttendanceResponse response);
    }

    public interface onResponse {
        public void response();
    }

    public interface onResponseAddOrder {
        public void responseAddOrder(String partyId);
    }

    public interface onResponsePlace {
        public void responsePlace(String orderId, String orderNumber);
    }

    public interface onResponses {
        public void responses(int totalItems, String cartId);
    }

    public interface onGetEmpList {
        public void getEmpList(List<ModelEmployee.EmpRequest> empList);
    }

    public interface onRequestOTP {
        public void OTPRequested();
    }

    public interface onVerifyOTP {
        public void OTPVerifyed();
    }

    public interface onGetStates {
        public void getStates(List<ModelStates.State> stateList);
    }

    public interface onGetCities {
        public void getCities(List<ModelCities.Cities> citiesList);
    }

    public interface onPasswordChange {
        public void onPasswordChanged();
    }

    public interface onLogin {
        public void onLoggedIn(ModelLogin.Data data);
    }

    public interface onAddEmp {
        public void onEmpAdded();
    }


    /*public interface onUpdateHoliday {
        public void onHolidaysUpdated();
    }*/
}
