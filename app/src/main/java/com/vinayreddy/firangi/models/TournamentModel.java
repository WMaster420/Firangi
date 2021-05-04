package com.vinayreddy.firangi.models;

public class TournamentModel {

    private String status;
    private String endDate;
    private String endTime;

    public TournamentModel() {
    }

    public TournamentModel(String status, String endDate, String endTime) {
        this.status = status;
        this.endDate = endDate;
        this.endTime = endTime;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
