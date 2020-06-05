package com.example.appdispatcher.ui.payment;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class PaymentViewModel implements Serializable {
    public String judul;
    public Drawable foto;
    public String location;
    public boolean expended;
    public String id_job;
    public String status_payment;

    public String getStatus_payment() {
        return status_payment;
    }

    public void setStatus_payment(String status_payment) {
        this.status_payment = status_payment;
    }

    public String getId_job() {
        return id_job;
    }

    public void setId_job(String id_job) {
        this.id_job = id_job;
    }

    public String getJudul() {
        return judul;
    }

    public void setJudul(String judul) {
        this.judul = judul;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Drawable getFoto() {
        return foto;
    }

    public void setFoto(Drawable foto) {
        this.foto = foto;
    }

    public boolean isExpended() {
        return expended;
    }

    public void setExpended(boolean expended) {
        this.expended = expended;
    }
}