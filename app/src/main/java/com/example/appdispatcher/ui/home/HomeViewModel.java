package com.example.appdispatcher.ui.home;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {

//    private MutableLiveData<String> mText;

    public String judul;
    public Drawable foto;

    public boolean expended;

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public Drawable getFoto() {
        return foto;
    }

    public void setFoto(Drawable foto) {
        this.foto = foto;
    }

//
//    public LiveData<String> getText() {
//        return mText;
//    }
}