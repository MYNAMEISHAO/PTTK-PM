package com.example.kttkpm.models;

import java.io.Serializable;

public class DichVu implements Serializable {
    private String id;
    private String tenDichVu;
    private String moTa;
    private double gia;

    public DichVu() {
        // Required empty constructor for Firebase
    }

    public DichVu(String id, String tenDichVu, String moTa, double gia) {
        this.id = id;
        this.tenDichVu = tenDichVu;
        this.moTa = moTa;
        this.gia = gia;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTenDichVu() {
        return tenDichVu;
    }

    public void setTenDichVu(String tenDichVu) {
        this.tenDichVu = tenDichVu;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public double getGia() {
        return gia;
    }

    public void setGia(double gia) {
        this.gia = gia;
    }
}
