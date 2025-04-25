package com.example.kttkpm.models;

public class Nha {
    private String id;
    private String address;
    private String houseType;
    private float area;
    private String khachID;
    private int resident;

    public Nha() {
    }

    public Nha(String ID, String diaChi, String houseType, float area, int resident, String khachID) {
        this.area = area;
        this.address = diaChi;
        this.houseType = houseType;
        this.id = ID;
        this.resident = resident;
        this.khachID = khachID;
    }

    public void setArea(float area) {
        this.area = area;
    }

    public void setDiaChi(String diaChi) {
        this.address = diaChi;
    }

    public void setHouseType(String houseType) {
        this.houseType = houseType;
    }

    public void setID(String ID) {
        this.id = ID;
    }

    public float getArea() {
        return area;
    }

    public String getAddress() {
        return address;
    }

    public String getHouseType() {
        return houseType;
    }

    public String getID() {
        return id;
    }

    public String getKhachID() {
        return khachID;
    }

    public void setKhachID(String khachID) {
        this.khachID = khachID;
    }

    public int getResident() {
        return resident;
    }

    public void setResident(int resident) {
        this.resident = resident;
    }


}