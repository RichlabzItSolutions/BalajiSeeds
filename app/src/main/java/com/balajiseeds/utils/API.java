package com.balajiseeds.utils;

import com.balajiseeds.admin.models.AcceptPermisoonJson;
import com.balajiseeds.admin.models.AddExpensesHeads.AddExpensesHeadsJson;
import com.balajiseeds.admin.models.AddExpensesHeads.DeleteExpensesHeadsJson;
import com.balajiseeds.admin.models.AddExpensesHeads.EditExpensesHeadsJson;
import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysJson;
import com.balajiseeds.admin.models.AddHoilidays.AddHolidaysResponse;
import com.balajiseeds.admin.models.AddProductVariety.AddProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.AddProductVarietyResponse;
import com.balajiseeds.admin.models.AddProductVariety.DeleteProductVarietyJson;
import com.balajiseeds.admin.models.AddProductVariety.GetProductVarietyResponse;
import com.balajiseeds.admin.models.AddProductVariety.ProductsVarietiesWithProductsJson;
import com.balajiseeds.admin.models.AddProductVariety.UpdateProductVarietyJson;
import com.balajiseeds.admin.models.AdminOrders.AdminOrdersResponse;
import com.balajiseeds.admin.models.DeleteHolidays.DeleteHolidaysJson;
import com.balajiseeds.admin.models.EditHolidays.EditHolidaysJson;
import com.balajiseeds.admin.models.GetOrder.CancelOrderJson;
import com.balajiseeds.admin.models.GetOrder.GetOrderReasponse;
import com.balajiseeds.admin.models.GetOrder.UpdateOrderJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysJson;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysResponse;
import com.balajiseeds.admin.models.ModelAttendance;
import com.balajiseeds.admin.models.ModelChangeStatus;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.admin.models.ModelPackagingSize;
import com.balajiseeds.admin.models.OTP.VerifyOTPJson;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryJson;
import com.balajiseeds.admin.models.PaidHistory.PaidHistoryResponse;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountJson;
import com.balajiseeds.admin.models.UpdateAmount.UpdateAmountResponse;
import com.balajiseeds.employee.models.AddOrder.AddOrderJson;
import com.balajiseeds.employee.models.AddOrder.AddOrderResponse;
import com.balajiseeds.employee.models.DeletePemisoonJson;
import com.balajiseeds.employee.models.GetOrdersForEmployeeJson;
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
import com.balajiseeds.employee.models.MyOrders.EmpOrdersResponse;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.GetProductsVarietiesWithProductsReasponse;
import com.balajiseeds.employee.models.UpdatePermissionJson;
import com.balajiseeds.employee.models.addCartnew.AddCartNewResponse;
import com.balajiseeds.employee.models.addCartnew.AddToCartNewJson;
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
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface API {
    @POST(Constants.login)
    Call<ModelLogin.LoginResponse> login(@Body ModelLogin.LoginRequest loginRequest);

    @POST(Constants.addEmployee)
    Call<ModelEmployee.EmpResponse> addEmp(@Header("Authorization") String Auth, @Body ModelEmployee.EmpRequest empRequest);

    @POST(Constants.updateEmployee)
    Call<ModelEmployee.EmpResponse> updateEmp(@Header("Authorization") String Auth, @Body ModelEmployee.EmpRequest empRequest);

    @POST(Constants.getEmployeesList)
    Call<ModelEmployee.EmpListResponse> getEmpList(@Header("Authorization") String Auth,@Body ModelEmployee.EmpListRequest request);

    @POST(Constants.changePassword)
    Call<ModelChangePassword.ChangePasswordResponse> changePass(@Body ModelChangePassword.ChangePasswordRequest changePasswordRequest);

    @GET(Constants.getStates)
    Call<ModelStates.StateResponse> getStates(@Header("Authorization") String Auth);

    @POST(Constants.getCities)
    Call<ModelCities.CitiesResponse> getCities(@Header("Authorization") String Auth, @Body ModelCities.CitiesRequest request);

    @POST(Constants.requestOTP)
    Call<ModelRequestOTP.OTPResponse> requestOTP(@Body ModelRequestOTP.OTPRequest request);

    @POST(Constants.verifyOTP)
    Call<ModelRequestOTP.OTPResponse> verifyOTP(@Body VerifyOTPJson request);

    @POST(Constants.deleteEmployee)
    Call<ModelEmployee.EmpResponse> deleteEmployee(@Header("Authorization") String Auth, @Body ModelEmployee.deleteEmpRequest request);

    @GET(Constants.getAllCities)
    Call<ModelCities.CitiesResponse> getAllCities(@Header("Authorization") String Auth);

    @POST(Constants.getMonthlyAttendanceEmp)
    Call<ModelMonthlyAttendance.MonthlyAttendanceResponse> getMonthlyAttendanceEmp(@Header("Authorization") String Auth, @Body ModelMonthlyAttendance.MonthlyAttendanceRequest request);

    @Multipart
    @POST(Constants.addLeaveEmp)
    Call<ModelConmmonResponse> addLeaveEmp(@Header("Authorization") String Auth,
                                           @Part MultipartBody.Part leaveType,
                                           @Part MultipartBody.Part subject,
                                           @Part MultipartBody.Part message,
                                           @Part MultipartBody.Part fromDate,
                                           @Part MultipartBody.Part toDate,
                                           @Part MultipartBody.Part filePart);

    @Multipart
    @POST(Constants.updateLeaveEmp)
    Call<ModelConmmonResponse> updateLeaveEmp(@Header("Authorization") String Auth,
                                              @Part MultipartBody.Part id,
                                              @Part MultipartBody.Part leaveType,
                                              @Part MultipartBody.Part subject,
                                              @Part MultipartBody.Part message,
                                              @Part MultipartBody.Part fromDate,
                                              @Part MultipartBody.Part toDate,
                                              @Part MultipartBody.Part filePart);

    @POST(Constants.deleteLeaveEmp)
    Call<ModelConmmonResponse> deleteLeaveEmp(@Header("Authorization") String Auth, @Body ModelLeaves.DeleteLeaveRequest request);

    @GET(Constants.fetchLeaveListEmp)
    Call<ModelLeaves.FetchLeaveResponse> fetchLeavesListEmp(@Header("Authorization") String Auth);

    @GET(Constants.fetchLeaveType)
    Call<ModelLeaves.LeaveTypeResponse> fetchLeaveType(@Header("Authorization") String Auth);

    @Multipart
    @POST(Constants.addVehicleReading)
    Call<ModelConmmonResponse> addVehicleReading(@Header("Authorization") String Auth,
                                                 @Part MultipartBody.Part id,
                                                 @Part MultipartBody.Part vehicleType,
                                                 @Part MultipartBody.Part reading,
                                                 @Part MultipartBody.Part filePart);

    @POST(Constants.deleteVehicleReading)
    Call<ModelConmmonResponse> deleteVehicleReading(@Header("Authorization") String Auth, @Body ModelVehicleReading.deleteReadingRequest request);

    @GET(Constants.fetchVehicleType)
    Call<ModelVehicleReading.GetVehicleTypeResponse> fetchVehicleType(@Header("Authorization") String Auth);

    @POST(Constants.fetchVehicleReading)
    Call<ModelVehicleReading.fetchReadingResponse> fetchVehicleReading(@Header("Authorization") String Auth, @Body ModelVehicleReading.fetchReadingRequest request);

    @POST(Constants.addEmpTracking)
    Call<ModelConmmonResponse> addEmpTracking(@Header("Authorization") String Auth, @Body ModelTracking.TrackingRequest request);

    @POST(Constants.fetchEmpTracking)
    Call<ModelTracking.fetchTrackingResponse> fetchEmpTracking(@Header("Authorization") String Auth, @Body ModelTracking.fetchTrackingRequest request);

    @Multipart
    @POST(Constants.addActivityEmp)
    Call<ModelConmmonResponse> addActivity(@Header("Authorization") String Auth,
                                           @Part MultipartBody.Part title,
                                           @Part MultipartBody.Part description,
                                           @Part MultipartBody.Part location,
                                           @Part List<MultipartBody.Part> files);

    @Multipart
    @POST(Constants.updateActivityEmp)
    Call<ModelConmmonResponse> updateActivity(@Header("Authorization") String Auth,
                                              @Part MultipartBody.Part id,
                                              @Part MultipartBody.Part title,
                                              @Part MultipartBody.Part description,
                                              @Part MultipartBody.Part location,
                                              @Part List<MultipartBody.Part> files,
                                              @Part List<MultipartBody.Part> delete);

    @POST(Constants.addExpensesEmp)
    Call<ModelConmmonResponse> addExpenses(@Header("Authorization") String Auth, @Body ModelExpenses.Expenses request);

    @POST(Constants.editExpensesEmp)
    Call<ModelConmmonResponse> editExpenses(@Header("Authorization") String Auth, @Body ModelExpenses.Expenses request);

    @POST(Constants.deleteExpensesEmp)
    Call<ModelConmmonResponse> deleteExpenses(@Header("Authorization") String Auth, @Body ModelExpenses.Expenses request);

    @POST(Constants.getExpensesEmp)
    Call<ModelExpenses.getExpensesResponse> getExpensesEmp(@Header("Authorization") String Auth, @Body ModelExpenses.getExpensesRequest request);

    @Multipart
    @POST(Constants.addExpenseNew)
    Call<ModelConmmonResponse> addExpensesNew(@Header("Authorization") String Auth,
                                          @Part MultipartBody.Part jsondata,
                                          @Part MultipartBody.Part file);
    @Multipart
    @POST(Constants.updateExpenseNew)
    Call<ModelConmmonResponse> editExpensesNew(@Header("Authorization") String Auth,
                                              @Part MultipartBody.Part jsondata,
                                              @Part MultipartBody.Part file);
    @GET(Constants.getExpensesHeads)
    Call<ModelExpenses.ExpensesHeadsResponse> getExpensesHeads(@Header("Authorization") String Auth);

    @GET(Constants.getExpensesHeadsAdmin)
    Call<ModelExpenses.ExpensesHeadsResponse> getExpensesHeadsAdmin(@Header("Authorization") String Auth);


    @POST(Constants.addExpensesHeadsAdmin)
    Call<AddHolidaysResponse> addExpensesHeadsAdmin(@Header("Authorization") String Auth,  @Body AddExpensesHeadsJson request);

    @POST(Constants.updateExpensesHeadsAdmin)
    Call<AddHolidaysResponse> updateExpensesHeadsAdmin(@Header("Authorization") String Auth,  @Body EditExpensesHeadsJson request);

    @POST(Constants.deleteExpensesHeadsAdmin)
    Call<AddHolidaysResponse> deleteExpensesHeadsAdmin(@Header("Authorization") String Auth,  @Body DeleteExpensesHeadsJson request);

    @POST(Constants.fetchActivityEmp)
    Call<ModelActivity.FetchActivityResponse> getActivityEmp(@Header("Authorization") String Auth, @Body ModelActivity.getActivityRequest request);

    @POST(Constants.deleteActivityEmp)
    Call<ModelConmmonResponse> deleteActivityEmp(@Header("Authorization") String Auth, @Body ModelActivity.DeleteRequest request);

    @POST(Constants.adminFetchLeaveList)
    Call<ModelLeaves.FetchLeaveResponse> adminFetchLeaveList(@Header("Authorization") String Auth, @Body ModelLeaves.adminFetchLeaveRequest request);

    @POST(Constants.adminLeaveReject)
    Call<ModelConmmonResponse> adminRejectLeave(@Header("Authorization") String Auth, @Body ModelLeaves.adminRejectLeaveRequest request);

    @POST(Constants.adminLeaveApprove)
    Call<ModelConmmonResponse> adminApproveLeave(@Header("Authorization") String Auth, @Body ModelLeaves.DeleteLeaveRequest request);

    @POST(Constants.changeEmployeeStatus)
    Call<ModelConmmonResponse> changeEmployeeStatus(@Header("Authorization") String Auth, @Body ModelChangeStatus request);

    @POST(Constants.adminFetchActivityList)
    Call<ModelActivity.FetchActivityResponse> adminFetchActivityList(@Header("Authorization") String Auth, @Body ModelActivity.adminGetActivityRequest request);

    @POST(Constants.adminfetchVehicleReading)
    Call<ModelVehicleReading.fetchReadingResponse> adminfetchVehicleReading(@Header("Authorization") String Auth, @Body ModelVehicleReading.fetchReadingRequest request);

    @POST(Constants.adminGetMonthlyAttendance)
    Call<ModelMonthlyAttendance.AdminGetMonthlyAttendanceResponse> adminfetchMonthlyAttendance(@Header("Authorization") String Auth, @Body ModelMonthlyAttendance.MonthlyAttendanceRequest request);

    @POST(Constants.cancelLeaveEmp)
    Call<ModelConmmonResponse> cancelLeaveEmp(@Header("Authorization") String Auth, @Body ModelLeaves.cancelLeaveRequest request);

    @POST(Constants.adminGetDailyAttendance)
    Call<ModelAttendance.GetDailyAttendanceResponse> adminGetDailyAttendance(@Header("Authorization") String Auth, @Body ModelAttendance.getDailyAttendanceRequest request);

    @Multipart
    @POST(Constants.addProduct)
    Call<ModelConmmonResponse> addProduct(@Header("Authorization") String Auth,
                                          @Part MultipartBody.Part jsondata,
                                          @Part List<MultipartBody.Part> files);



  /*  @Multipart
    @POST(Constants.addProductNew)
    Call<ModelConmmonResponse> addProductNew(@Header("Authorization") String Auth,
                                             @Part MultipartBody.Part productData,
                                             @Part MultipartBody.Part[] images  );*/

    @Multipart
    @POST(Constants.addProductNew)
    Call<ModelConmmonResponse> addProductNew(@Header("Authorization") String Auth,
                                             @Part("productData") RequestBody productData,  // JSON product data
                                             @Part MultipartBody.Part[] images );


    @POST(Constants.fetchProducts)
    Call<ModelProducts.GetProductResponse> fetchProducts(@Header("Authorization") String Auth,@Body ModelProducts.GetProductRequest request);


    @POST(Constants.fetchProductsNew)
    Call<ModelsProducts.GetProductResponse> fetchProductsNew(@Header("Authorization") String Auth, @Body ModelsProducts.GetProductRequest request);


    @POST(Constants.fetchProductsVarietiesWithProducts)
    Call<GetProductsVarietiesWithProductsReasponse> fetchProductsVarietiesWithProducts(@Header("Authorization") String Auth, @Body ProductsVarietiesWithProductsJson request);


    @GET(Constants.getPackagingSize)
    Call<ModelPackagingSize.GetPackagingSizeResponse> getPackagingSize(@Header("Authorization") String Auth);

    @Multipart
    @POST(Constants.updateProducts)
    Call<ModelConmmonResponse> updateProducts(@Header("Authorization") String Auth,
                                              @Part MultipartBody.Part jsondata,
                                              @Part List<MultipartBody.Part> files);

    @Multipart
    @POST(Constants.updateProductsNew)
    Call<ModelConmmonResponse> updateProductsNew(@Header("Authorization") String Auth,
                                             @Part("productData") RequestBody productData,  // JSON product data
                                             @Part MultipartBody.Part[] images );

   /* @Multipart
    @POST(Constants.updateProductsNew)
    Call<ModelConmmonResponse> updateProductsNew(@Header("Authorization") String Auth,
                                                 @Part MultipartBody.Part jsondata,
                                                 @Part List<MultipartBody.Part> files);*/

    @POST(Constants.deleteProduct)
    Call<ModelConmmonResponse> deleteProduct(@Header("Authorization") String Auth, @Body ModelProducts.deleteProduct request);

    @POST(Constants.deleteProductNew)
    Call<ModelConmmonResponse> deleteProductNew(@Header("Authorization") String Auth, @Body ModelProducts.deleteProduct request);

    @POST(Constants.getProductById)
    Call<ModelProducts.getProductByIdResponse> getProductById(@Header("Authorization") String Auth, @Body ModelProducts.getProductByIdRequest request);

    @POST(Constants.addToCart)
    Call<ModelConmmonResponse> addToCart(@Header("Authorization") String Auth,@Body ModelCart.addtoCart request);

    @POST(Constants.addCartNew)
    Call<AddCartNewResponse> addCartNew(@Header("Authorization") String Auth, @Body AddToCartNewJson request);

    @POST(Constants.deleteFromCart)
    Call<ModelConmmonResponse> deleteFromCart(@Header("Authorization") String Auth,@Body ModelCart.deleteFromCart request);


    @POST(Constants.removeQunatity)
    Call<RemoveQunatityReasponse> removeQunatity(@Header("Authorization") String Auth, @Body RemoveQunatityJson request);


    @POST(Constants.addQunatity)
    Call<RemoveQunatityReasponse> addQunatity(@Header("Authorization") String Auth, @Body RemoveQunatityJson request);

    /*
    @POST(Constants.deleteFromCart)
    Call<ModelConmmonResponse> deleteFromCart(@Header("Authorization") String Auth,@Body ModelCart.deleteFromCart request);
*/
    @POST(Constants.getCart)
    Call<ModelCart.getCartResponse> getCart(@Header("Authorization") String Auth);

    @POST(Constants.getTheCart)
    Call<GetCartNewResponse> getTheCart(@Header("Authorization") String Auth,@Body PlaceOrderJson request);


    @POST(Constants.addOrder)
    Call<ModelConmmonResponse> addOrder(@Header("Authorization") String Auth);

    @POST(Constants.placeTheOrder)
    Call<PlaceOrderReasponse> placeTheOrder(@Header("Authorization") String Auth, @Body PlaceOrderJson request);

    @POST(Constants.fetchOrdersbyUserId)
    Call<ModelOrder.fetchOrderResponse> fetchOrdersbyUserId(@Header("Authorization") String Auth, @Body GetOrdersForEmployeeJson request);


    @GET(Constants.EmpOrders)
    Call<EmpOrdersResponse> EmpOrders(@Header("Authorization") String Auth);

    @GET(Constants.AdminOrders)
    Call<AdminOrdersResponse> AdminOrders(@Header("Authorization") String Auth);

    @POST(Constants.fetchOrdersAdmin)
    Call<GetOrderReasponse> fetchOrderAdmin(@Header("Authorization") String Auth, @Body GetOrdersForEmployeeJson request);


    @POST(Constants.updateAmount)
    Call<UpdateAmountResponse> updateAmount(@Header("Authorization") String Auth, @Body UpdateAmountJson request);

    @POST(Constants.paidHistory)
    Call<PaidHistoryResponse> paidHistory(@Header("Authorization") String Auth, @Body PaidHistoryJson request);


    @POST(Constants.OrdersAdminCancel)
    Call<GetOrderReasponse> OrderAdminCancel(@Header("Authorization") String Auth, @Body CancelOrderJson request);

    @POST(Constants.OrdersAdminUpdate)
    Call<GetOrderReasponse> OrderAdminUpdate(@Header("Authorization") String Auth, @Body UpdateOrderJson request);
    @POST(Constants.fetchOrderByOrderId)
    Call<ModelOrder.fetchOrderResponse> fetchOrderByOrderId(@Header("Authorization") String Auth,@Body ModelOrder.OrderByIdRequest request);
    @POST(Constants.requestAdditionalWorkingDay)
    Call<ModelConmmonResponse> requestAdditionalWorkingDay(@Header("Authorization") String Auth,@Body ModelPermissionAWD.awd request);
    @POST(Constants.updateRequestAdditionalWorkingDay)
    Call<ModelConmmonResponse> updateRequestAdditionalWorkingDay(@Header("Authorization") String Auth,@Body UpdatePermissionJson request);
    @POST(Constants.fetchRequestAdditionalWorkingDay)
    Call<GetPermissonResponse> fetchRequestAdditionalWorkingDay(@Header("Authorization") String Auth);

    @POST(Constants.adminAdditionalWorkingDayRequest)
    Call<GetPermissonResponse> adminAdditionalWorkingDayRequest(@Header("Authorization") String Auth);

    @POST(Constants.addHolidays)
    Call<AddHolidaysResponse> addHolidays(@Header("Authorization") String Auth, @Body AddHolidaysJson request);

    @POST(Constants.addProductVariety)
    Call<AddProductVarietyResponse> addProductVariety(@Header("Authorization") String Auth, @Body AddProductVarietyJson request);

    @POST(Constants.updateProductVariety)
    Call<AddProductVarietyResponse> updateProductVariety(@Header("Authorization") String Auth, @Body UpdateProductVarietyJson request);

    @POST(Constants.deleteProductVariety)
    Call<AddProductVarietyResponse> deleteProductVariety(@Header("Authorization") String Auth, @Body DeleteProductVarietyJson request);

    @POST(Constants.getProductVariety)
    Call<GetProductVarietyResponse> getProductVariety(@Header("Authorization") String Auth);


    @POST(Constants.addOrders)
    Call<AddOrderResponse> addOrders(@Header("Authorization") String Auth, @Body AddOrderJson request);


   /* @POST(Constants.getHolidays)
    Call<GetHolidaysResponse> getHolidays(@Header("Authorization") String Auth, @Body GetHolidaysJson request);*/

    @POST(Constants.updateHolidays)
    Call<AddHolidaysResponse> updateHolidays(@Header("Authorization") String Auth, @Body EditHolidaysJson request);

    @POST(Constants.deleteHolidays)
    Call<AddHolidaysResponse> deleteHolidays(@Header("Authorization") String Auth, @Body DeleteHolidaysJson request);

    @POST(Constants.cancelAdditionalWorkingDay)
    Call<AddHolidaysResponse> deletePermisoon(@Header("Authorization") String Auth, @Body DeletePemisoonJson request);

    @POST(Constants.acceptAdditionalWorkingDay)
    Call<AddHolidaysResponse> acceptAdditionalWorkingDay(@Header("Authorization") String Auth, @Body AcceptPermisoonJson request);

    @POST(Constants.rejectAdditionalWorkingDay)
    Call<AddHolidaysResponse> rejectAdditionalWorkingDay(@Header("Authorization") String Auth, @Body AcceptPermisoonJson request);

    @POST(Constants.getEmployeesHolidays)
    Call<GetHolidaysResponse> getEmployeesHolidays(@Header("Authorization") String Auth);

    @POST(Constants.getHolidays)
    Call<GetHolidaysResponse> getHolidays(@Header("Authorization") String Auth, @Body GetHolidaysJson request);


}
