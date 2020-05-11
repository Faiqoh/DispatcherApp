package com.example.appdispatcher.ui.home;

import java.io.Serializable;

public class HomeViewModel implements Serializable {

//    private MutableLiveData<String> mText;

    public String judul;
    public String foto;
    public String customer;
    public String location;
    public String id_job;

    public String getId_job() {
        return id_job;
    }

    public void setId_job(String id_job) {
        this.id_job = id_job;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

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

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

//
//    public LiveData<String> getText() {
//        return mText;
//    }
}