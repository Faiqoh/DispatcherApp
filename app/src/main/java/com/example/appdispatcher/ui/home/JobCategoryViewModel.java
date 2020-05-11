package com.example.appdispatcher.ui.home;

import java.io.Serializable;

public class JobCategoryViewModel implements Serializable {

    public String judul;
    public String foto;

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

}
