package com.balajiseeds.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.provider.OpenableColumns;

import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentManager;

import com.balajiseeds.ImageViewerDialogFragment;
import com.balajiseeds.admin.models.HolidaysList.GetHolidaysDetails;
import com.balajiseeds.admin.models.ModelEmployee;
import com.balajiseeds.employee.models.ModelActivity;
import com.balajiseeds.employee.models.ModelExpenses;
import com.balajiseeds.employee.models.ModelLeaves;
import com.balajiseeds.employee.models.ModelProducts;
import com.balajiseeds.employee.models.ModelVehicleReading;
import com.balajiseeds.employee.models.ModelsProducts;
import com.balajiseeds.employee.models.ProductsVarietiesWithProducts.ProductVarietiesData;
import com.balajiseeds.models.ModelCities;
import com.balajiseeds.models.ModelStates;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Constants {
    public static final String SUCCESS = "success";
    public static final String login = "api/login";
    public static final String addEmployee = "api/addEmployee";
    public static final String getEmployeesList = "api/getEmployeeList";
    public static final String updateEmployee = "api/updateEmployee";
    public static final String deleteEmployee = "api/deleteEmployee";
    public static final String changePassword = "api/changePassword";
    public static final String getStates = "api/getStatesList";
    public static final String getCities = "api/getCitiesList";
    public static final String requestOTP = "api/requestOtp";
    public static final String verifyOTP = "api/verifyOTP";
    public static final String getAllCities = "api/getAllCitiesList";
    public static final String getMonthlyAttendanceEmp = "api/getMonthlyAttendance";
    public static final String addLeaveEmp = "api/addLeave";
    public static final String updateLeaveEmp = "api/updateLeave";
    public static final String deleteLeaveEmp = "api/deleteLeave";
    public static final String cancelLeaveEmp = "api/cancelLeave";
    public static final String fetchLeaveListEmp = "api/fetchLeaveList";
    public static final String fetchLeaveType = "api/fetchAllLeaveType";
    public static final String addVehicleReading = "api/addVehicleReading";
    public static final String fetchVehicleReading = "api/fetchVehicleReading";
    public static final String deleteVehicleReading = "api/deleteVehicleReading";
    public static final String fetchVehicleType = "api/getAllVehicleType";
    public static final String addEmpTracking = "api/addEmpTracking";
    public static final String fetchEmpTracking = "api/fetchEmpTracking";
    public static final String addActivityEmp = "api/addActivity";
    public static final String updateActivityEmp = "api/updateActivity";
    public static final String deleteActivityEmp = "api/deleteActivity";
    public static final String fetchActivityEmp = "api/fetchActivityList";
    public static final String addExpensesEmp = "api/addExpense";
    public static final String editExpensesEmp = "api/updateExpense";
    public static final String deleteExpensesEmp = "api/deleteExpense";
    public static final String getExpensesHeads = "api/getExpensesHeads";
    public static final String getExpensesHeadsAdmin = "api/getExpensesHeads";

    public static final String addExpensesHeadsAdmin = "api/addExpenseHeadNew";

    public static final String updateExpensesHeadsAdmin = "api/updateExpenseHeadNew";

    public static final String deleteExpensesHeadsAdmin = "api/deleteExpenseHeadNew";


    public static final String getExpensesEmp = "api/getExpensesNew";
    public static final String adminFetchLeaveList = "api/adminFetchLeaveList";
    public static final String adminLeaveReject = "api/adminLeaveReject";
    public static final String adminLeaveApprove = "api/adminLeaveApprove";
    public static final String changeEmployeeStatus = "api/changeEmployeeStatus";
    public static final String adminFetchActivityList = "api/adminFetchActivityList";
    public static final String adminfetchVehicleReading = "api/adminfetchVehicleReading";
    public static final String adminGetMonthlyAttendance = "api/adminGetMonthlyAttendance";
    public static final String adminGetDailyAttendance = "api/dailyAttendance";
    public static final String addProduct = "api/addProduct";
    public static final String fetchProducts = "api/fetchProducts";
    public static final String updateProducts = "api/updateProduct";
    public static final String deleteProduct = "api/deleteProduct";

    public static final String addProductNew = "api/addProductNew";
    public static final String fetchProductsNew = "api/fetchProductsNew";

    public static final String fetchProductsVarietiesWithProducts = "api/getProductVarietiesWithProducts";
    public static final String updateProductsNew = "api/updateProductNew";
    public static final String deleteProductNew = "api/deleteProductNew";

    public static final String getProductById = "api/getProduct";
    public static final String getPackagingSize = "api/getPackagingSize";
    public static final String addToCart = "api/addToCartNew";
    public static final String addCartNew = "api/addCartnew";

    public static final String deleteFromCart = "api/deleteFromCartnew";
   // public static final String deleteFromCart = "api/deleteProductFromCart";
   public static final String addQunatity = "api/addQunatity";

    public static final String removeQunatity = "api/removeQunatity";
    public static final String getCart = "api/getCartNew";

    public static final String getTheCart = "api/getTheCart";
    public static final String addOrder = "api/placeOrder";

    public static final String placeTheOrder = "api/placeTheOrder";
    public static final String fetchOrderByOrderId = "api/getOrderDetails";
    public static final String fetchOrdersbyUserId = "api/getOrdersForEmployee";

    public static final String EmpOrders = "api/myOrders";

    public static final String AdminOrders = "api/myOrdersAdmin";

    public static final String fetchOrdersAdmin = "api/getOrders";

    public static final String updateAmount = "api/updateAmount";
    public static final String paidHistory = "api/paidHistory";

    public static final String OrdersAdminCancel = "api/cancelOrder";
    public static final String OrdersAdminUpdate = "api/updateOrder";

    public static final String addExpenseNew = "api/addExpenseNew";
    public static final String updateExpenseNew = "api/updateExpenseNew";
    public static final String requestAdditionalWorkingDay = "api/requestAdditionalWorkingDay";
    public static final String updateRequestAdditionalWorkingDay = "api/updateRequestAdditionalWorkingDay";
    public static final String fetchRequestAdditionalWorkingDay = "api/getRequestedAdditionalWorkingDays";
    public static final String adminAdditionalWorkingDayRequest = "api/getAdditionalWorkingDayRequests";



    public static final String cancelAdditionalWorkingDay = "api/cancelRequestedAdditionalWorkingDay";

    public static final String acceptAdditionalWorkingDay = "api/acceptAdditionalWorkingDay";


    public static final String rejectAdditionalWorkingDay = "api/rejectAdditionalWorkingDay";


    public static final String addHolidays = "api/addHoliday";
    public static final String addProductVariety = "api/addProductvariety";
    public static final String updateProductVariety = "api/updateProductvariety";
    public static final String deleteProductVariety = "api/deleteProductvariety";
    public static final String getProductVariety = "api/getProductvariety";

    public static final String getHolidays = "api/getHolidays";

    public static final String updateHolidays = "api/updateHoliday";
    public static final String deleteHolidays = "api/deleteHoliday";


    public static final String getEmployeesHolidays = "api/getEmployeesHolidaysList";

    public static final String addOrders = "api/saveParty";




    public static String baseUrl = "https://richlabzit.com/projects/balajiseeds/";
    //LOCATION CHANGE
    public static double lat;
    public static double lon;
//    public static double lat = 21.408686;
//    public static double lon = 79.9279701;
    public static ModelActivity.Data SelectedActivity;
    public static ModelsProducts.Product SelectedProduct;
    public static  ProductVarietiesData SelectedProducts;


    public static ModelLeaves.Leave SelectedLeave;

    public static GetHolidaysDetails SelectedHOLIDAY;
    public static ModelEmployee.EmpRequest SelectedEMP;
    public static List<ModelStates.State> stateList;
    public static List<ModelCities.Cities> citiesList;
    public static List<ModelVehicleReading.VehicleType> VehicleTypeList;
    public static List<ModelExpenses.ExpensesHead> ExpenseHead;

    public static List<ModelLeaves.LeaveType> leaveTypeList;

    public static String getStateIdByName(String stateName) {
        for (ModelStates.State state : stateList) {
            if (state.getStateName().equals(stateName)) {
                return state.getId();
            }
        }
        return null;
    }

    public static String getStateNameById(String stateId) {
        for (ModelStates.State state : stateList) {
            if (state.getId().equals(stateId)) {
                return state.getStateName();
            }
        }
        return null; // Return null if state ID is not found
    }

    public static String getCityIdByName(String cityName) {
        for (ModelCities.Cities cities : citiesList) {
            if (cities.getCityName().equals(cityName)) {
                return cities.getId();
            }
        }
        return null;
    }

    public static String getCityNameById(String cityId) {
        for (ModelCities.Cities cities : citiesList) {
            if (cities.getId().equals(cityId)) {
                return cities.getCityName();
            }
        }
        return null; // Return null if state ID is not found
    }

    public static File uriToFile(Uri uri, Context context) {
        try {
            String fileName = getFileNameFromUri(uri, context);
            File file = new File(context.getCacheDir(), fileName);
            try (InputStream inputStream = context.getContentResolver().openInputStream(uri);
                 FileOutputStream outputStream = new FileOutputStream(file)) {

                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
            }

            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String getFileNameFromUri(Uri uri, Context context) {
        String result = null;
        if (uri.getScheme().equals("content")) {
            try (Cursor cursor = context.getContentResolver().query(uri, null, null, null, null)) {
                if (cursor != null && cursor.moveToFirst()) {
                    int index = cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME);
                    if (index != -1) {
                        result = cursor.getString(index);
                    }
                }
            }
        }
        if (result == null) {
            result = uri.getLastPathSegment();
        }
        return result;
    }

    public static void checkPermission(Context context, String[] permissions) {
        int result;
        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            result = ContextCompat.checkSelfPermission(context, p);
            if (result != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            int MULTIPLE_PERMISSIONS = 10;
            ActivityCompat.requestPermissions(((Activity) context), listPermissionsNeeded.toArray(new String[0]), MULTIPLE_PERMISSIONS);
        }
    }

    public static void ShowDeleteDialog(Context context, OnYesClicked onYesClicked) {
        AlertDialog ad = new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to delete")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onYesClicked.YesClicked();
                    }
                }).setNegativeButton("No", null)
                .create();
        ad.show();
    }



    public static void ShowCancelDialog(Context context, OnYesClicked onYesClicked) {
        AlertDialog ad = new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to cancel order")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onYesClicked.YesClicked();
                    }
                }).setNegativeButton("No", null)
                .create();
        ad.show();
    }



    public static void ShowAcceptDialog(Context context, OnYesClicked onYesClicked) {
        AlertDialog ad = new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to Accept")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onYesClicked.YesClicked();
                    }
                }).setNegativeButton("No", null)
                .create();
        ad.show();
    }


    public static void ShowRejectDialog(Context context, OnYesClicked onYesClicked) {
        AlertDialog ad = new AlertDialog.Builder(context)
                .setMessage("Are you sure you want to Reject")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onYesClicked.YesClicked();
                    }
                }).setNegativeButton("No", null)
                .create();
        ad.show();
    }



    public static void showImageViewerDialog(FragmentManager fm, ArrayList<String> imageList, int pos) {
        ImageViewerDialogFragment dialogFragment = ImageViewerDialogFragment.newInstance(imageList, pos);
        dialogFragment.show(fm, "ImageViewerDialogFragment");
    }

    public static String getOrderStatus(String status) {
        //1=Pending,2=Approved,3=Out for delivery, 4=delivered, 5=rejected, 6=cancelled
        if(status!=null) {
            switch (status) {
                case "1":
                    return "Pending";
                case "2":
                    return "Approved";
                case "3":
                    return "Out for delivery";
                case "4":
                    return "Delivered";
                case "5":
                    return "Rejected";
                case "6":
                    return "Cancelled";
                default:
                    return "Unknown";
            }
        }
        return "";
    }

    public interface OnYesClicked {
        public void YesClicked();
    }


}
