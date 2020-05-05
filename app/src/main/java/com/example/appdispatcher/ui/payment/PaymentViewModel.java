package com.example.appdispatcher.ui.payment;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

public class PaymentViewModel extends ViewModel {
    public String judul;
    public Drawable foto;
    public String location;

    public PaymentViewModel(String judul, Drawable foto, String location) {
        this.judul = judul;
        this.foto = foto;
        this.location = location;
    }
}