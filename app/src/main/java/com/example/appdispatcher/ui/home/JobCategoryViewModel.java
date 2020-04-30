package com.example.appdispatcher.ui.home;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

public class JobCategoryViewModel extends ViewModel {

    public String judul;
    public Drawable foto;

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

}
