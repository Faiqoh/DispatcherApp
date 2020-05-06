package com.example.appdispatcher.ui.detail;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class DetailProgressViewModel extends ViewModel {

    public String date, day;
    private MutableLiveData<String> mText;

    public DetailProgressViewModel(String date, String day) {
        this.date = date;
        this.day = day;
    }

}
