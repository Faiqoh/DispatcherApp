package com.example.appdispatcher.ui.support;

import java.io.Serializable;

public class SupportViewModel implements Serializable {
    public String judul;
    public String foto;
    public String status_support;
    public String id_support;
    public String id_job;
    public String date;
    public String id_engineer;

    public String getId_engineer() {
        return id_engineer;
    }

    public void setId_engineer(String id_engineer) {
        this.id_engineer = id_engineer;
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

    public String getStatus_support() {
        return status_support;
    }

    public void setStatus_support(String status_support) {
        this.status_support = status_support;
    }

    public String getId_support() {
        return id_support;
    }

    public void setId_support(String id_support) {
        this.id_support = id_support;
    }

    public String getId_job() {
        return id_job;
    }

    public void setId_job(String id_job) {
        this.id_job = id_job;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
