package com.example.appdispatcher.ui.detail;

import androidx.lifecycle.ViewModel;

public class DetailProgressViewModel extends ViewModel {

    public String date;
    public String day;

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
//    private MutableLiveData<String> mText;
//
//    public DetailProgressViewModel(String date, String day) {
//        this.date = date;
//        this.day = day;
//    }

}
