package com.example.appdispatcher.ui.payment;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class PaymentViewModel implements Serializable {
    public String judul;
    public Drawable foto;
    public String status_payment;
    public String id_payment;

    public String getId_payment() {
        return id_payment;
    }

    public void setId_payment(String id_payment) {
        this.id_payment = id_payment;
    }

    public String getStatus_payment() {
        return status_payment;
    }

    public void setStatus_payment(String status_payment) {
        this.status_payment = status_payment;
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
}