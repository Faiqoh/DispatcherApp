package com.example.appdispatcher.ui.home;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

public class RecomendJobViewModel extends ViewModel {

    public String judul;
    public Drawable foto;
    private MutableLiveData<String> mText;

    public RecomendJobViewModel(String judul, Drawable foto) {
        this.judul = judul;
        this.foto = foto;
    }

    public RecomendJobViewModel(ArrayList<RecomendJobViewModel> rList) {
    }

    public LiveData<String> getText() {
        return mText;
    }
}