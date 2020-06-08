package com.example.appdispatcher.ui.payment;

import android.graphics.drawable.Drawable;

import androidx.lifecycle.ViewModel;

public class DetailPaymentViewModel extends ViewModel {
    String ft_transfer;
    String Status;
    Drawable icon;

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
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