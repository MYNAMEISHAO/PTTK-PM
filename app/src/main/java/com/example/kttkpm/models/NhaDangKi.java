package com.example.kttkpm.models;

public class NhaDangKi {
    private String nhaID;
    private String ID;
    private String dichVuID;

    public NhaDangKi() {
    }

    public NhaDangKi(String nhaID, String ID, String dichVu) {
        this.nhaID = nhaID;
        this.ID = ID;
        this.dichVuID = dichVu;
    }

    public NhaDangKi(String nhaID, String dichVuID) {
        this.nhaID = nhaID;
        this.dichVuID = dichVuID;
    }

    public String getNhaID() {
        return nhaID;
    }

    public void setNhaID(String nhaID) {
        this.nhaID = nhaID;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDichVu() {
        return dichVuID;
    }
    public void setDichVu(String dichVu) {
        this.dichVuID = dichVu;
    }
}
