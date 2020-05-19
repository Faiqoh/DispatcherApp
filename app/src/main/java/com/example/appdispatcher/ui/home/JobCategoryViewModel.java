package com.example.appdispatcher.ui.home;

import java.io.Serializable;
import java.util.List;

public class JobCategoryViewModel implements Serializable {

    public String judul;
    public String foto;

    public List<JobCategoryViewModel> details;
    public String main_category;

    public List<JobCategoryViewModel> getDetails() {
        return details;
    }

    public void setDetails(List<JobCategoryViewModel> details) {
        this.details = details;
    }

    public String getMain_category() {
        return main_category;
    }

    public void setMain_category(String main_category) {
        this.main_category = main_category;
    }

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
