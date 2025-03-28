package com.balajiseeds.employee.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Multipart;

public class ModelActivity {
    public static class addActivityRequest {
        public MultipartBody.Part title;
        public MultipartBody.Part description;
        MultipartBody.Part location;
        List<MultipartBody.Part> imgList;


        public addActivityRequest(String title, String description, String location, List<File> filelist) {
            imgList = new ArrayList<>();
            this.title = MultipartBody.Part.createFormData("title", title);
            this.description = MultipartBody.Part.createFormData("description", description);
            this.location = MultipartBody.Part.createFormData("location", location);
            for (File f : filelist) {
                RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), f);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("images[]", f.getName(), fileRequestBody);
                imgList.add(filePart);
            }
        }

        public MultipartBody.Part getTitle() {
            return title;
        }

        public MultipartBody.Part getDescription() {
            return description;
        }

        public MultipartBody.Part getLocation() {
            return location;
        }


        public List<MultipartBody.Part> getImgList() {
            return imgList;
        }

    }

    public static class EditActivityRequest {
        private MultipartBody.Part id;
        private MultipartBody.Part title;
        private MultipartBody.Part description;
        private MultipartBody.Part location;
        private List<MultipartBody.Part> imgList;
        private List<MultipartBody.Part> deleteList;

        public EditActivityRequest(String id, String title, String description, String location, List<File> fileList, List<String> deleteList) {
            imgList = new ArrayList<>();
            this.deleteList = new ArrayList<>();

            this.title = MultipartBody.Part.createFormData("title", title);
            this.id = MultipartBody.Part.createFormData("id", id);
            this.description = MultipartBody.Part.createFormData("description", description);
            this.location = MultipartBody.Part.createFormData("location", location);

            for (File file : fileList) {
                RequestBody fileRequestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
                MultipartBody.Part filePart = MultipartBody.Part.createFormData("images[]", file.getName(), fileRequestBody);
                imgList.add(filePart);
            }

            for (String deleteItem : deleteList) {
//                RequestBody deleteRequestBody = RequestBody.create(MediaType.parse("text/plain"), deleteItem);
                MultipartBody.Part deletePart = MultipartBody.Part.createFormData("delete[]", deleteItem);
                this.deleteList.add(deletePart);
            }
        }

        public MultipartBody.Part getId() {
            return id;
        }

        public MultipartBody.Part getTitle() {
            return title;
        }

        public MultipartBody.Part getDescription() {
            return description;
        }

        public MultipartBody.Part getLocation() {
            return location;
        }

        public List<MultipartBody.Part> getImgList() {
            return imgList;
        }

        public List<MultipartBody.Part> getDeleteList() {
            return deleteList;
        }
    }

    public static class FetchActivityResponse {

        @SerializedName("status")
        @Expose
        private String status;
        @SerializedName("message")
        @Expose
        private String message;
        @SerializedName("data")
        @Expose
        private List<Data> data;

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }

        public List<Data> getData() {
            return data;
        }

        public void setData(List<Data> data) {
            this.data = data;
        }

    }

    public static class Data {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("empId")
        @Expose
        private String empId;
        @SerializedName("empName")
        @Expose
        private String empName;
        @SerializedName("title")
        @Expose
        private String title;
        @SerializedName("description")
        @Expose
        private String description;
        @SerializedName("location")
        @Expose
        private String location;
        @SerializedName("photos")
        @Expose
        private List<Photo> photos;
        @SerializedName("date")
        @Expose
        private String date;

        public String getEmpName() {
            return empName;
        }

        public void setEmpName(String empName) {
            this.empName = empName;
        }

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getEmpId() {
            return empId;
        }

        public void setEmpId(String empId) {
            this.empId = empId;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public List<Photo> getPhotos() {
            return photos;
        }

        public void setPhotos(List<Photo> photos) {
            this.photos = photos;
        }

    }

    public static class Photo {

        @SerializedName("id")
        @Expose
        private String id;
        @SerializedName("imageUrl")
        @Expose
        private String imageUrl;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public void setImageUrl(String imageUrl) {
            this.imageUrl = imageUrl;
        }

    }

    public static class DeleteRequest {
        @SerializedName("id")
        @Expose
        private String id;

        public DeleteRequest(String id) {
            this.id = id;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }

    public static class getActivityRequest {
        @SerializedName("fromDate")
        @Expose
        public String fromDate;
        @SerializedName("toDate")
        @Expose
        public String toDate;

        public getActivityRequest(String fromDate, String toDate) {
            this.fromDate = fromDate;
            this.toDate = toDate;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }
    }

    public static class adminGetActivityRequest {
        @SerializedName("fromDate")
        @Expose
        public String fromDate;
        @SerializedName("toDate")
        @Expose
        public String toDate;
        @SerializedName("state")
        @Expose
        public String state;
        @SerializedName("city")
        @Expose
        public String city;

        public adminGetActivityRequest(String fromDate, String toDate, String state, String city) {
            this.fromDate = fromDate;
            this.toDate = toDate;
            this.state = state;
            this.city = city;
        }

        public String getFromDate() {
            return fromDate;
        }

        public void setFromDate(String fromDate) {
            this.fromDate = fromDate;
        }

        public String getToDate() {
            return toDate;
        }

        public void setToDate(String toDate) {
            this.toDate = toDate;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }
    }
}
