package com.example.appdispatcher.ui.payment;

import android.graphics.drawable.Drawable;

import java.io.Serializable;

public class DetailPaymentViewModel implements Serializable {
    String ft_transfer;
    String status;
    Drawable icon;
    String date;
    String id_payment;

    public String getId_payment() {
        return id_payment;
    }

    public void setId_payment(String id_payment) {
        this.id_payment = id_payment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getFt_transfer() {
        return ft_transfer;
    }

    public void setFt_transfer(String ft_transfer) {
        this.ft_transfer = ft_transfer;
    }

    public Drawable getIcon() {
        return icon;
    }

    public void setIcon(Drawable icon) {
        this.icon = icon;
    }
}