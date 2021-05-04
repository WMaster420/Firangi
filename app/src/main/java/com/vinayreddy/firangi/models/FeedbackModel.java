package com.vinayreddy.firangi.models;

public class FeedbackModel {

    private String message;
    private String userId;
    private String userName;
    private String dateTime;

    public FeedbackModel() {
    }

    public FeedbackModel(String message, String userId, String userName, String dateTime) {
        this.message = message;
        this.userId = userId;
        this.userName = userName;
        this.dateTime = dateTime;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
