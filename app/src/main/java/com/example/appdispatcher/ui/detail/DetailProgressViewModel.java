package com.example.appdispatcher.ui.detail;

import java.io.Serializable;

public class DetailProgressViewModel implements Serializable {

    public String date;
    public String day;
    public String id_job;
    public String detail_activity;
    public boolean expended;

    public String getDetail_activity() {
        return detail_activity;
    }

    public void setDetail_activity(String detail_activity) {
        this.detail_activity = detail_activity;
    }

    public String getId_job() {
        return id_job;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public void setId_job(String id_job) {
        this.id_job = id_job;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }

}
