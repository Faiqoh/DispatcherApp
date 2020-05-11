package com.example.appdispatcher.ui.detail;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

public class PendingViewModel extends ViewModel {
    public String judul;
    public Drawable foto;
    public String location;

    public PendingViewModel(String judul, Drawable foto, String location) {
        this.judul = judul;
        this.foto = foto;
        this.location = location;
    }
}
