package com.example.appdispatcher.ui.home;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public String judul;
    public Drawable foto;

    public HomeViewModel(String judul, Drawable foto) {
        this.judul = judul;
        this.foto = foto;
    }

    public LiveData<String> getText() {
        return mText;
    }
}