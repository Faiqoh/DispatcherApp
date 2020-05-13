package com.example.appdispatcher.ui.home;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class RecomendJobViewModel extends ViewModel {

    public String judul;

    public String location;

    public RecomendJobViewModel(String judul, String location, Drawable foto) {
        this.judul = judul;
        this.foto = foto;
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public Drawable foto;
    private MutableLiveData<String> mText;

    public void setLocation(String location) {
        this.location = location;
    }


    public LiveData<String> getText() {
        return mText;
    }
}