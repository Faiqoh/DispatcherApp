package com.example.appdispatcher.ui.home;

import java.io.Serializable;

public class JobCategoryViewModel implements Serializable {

    public String judul;
    public String foto;

    public Integer id_category;

    public Integer getId_category() {
        return id_category;
    }

    public void setId_category(Integer id_category) {
        this.id_category = id_category;
    }

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
