package com.example.kttkpm.models;

import java.util.List;

public class HopDong {
    private String id;
    private String khachID;
    private String status;
    private long ngayKy;        // Thay đổi từ Date sang long
    private long ngayHetHan;    // Thay đổi từ Date sang long
    private List<NhaDangKi> nhaDangKiList;

    public HopDong() {
        // Required empty constructor for Firebase
    }

    public HopDong(String id, String khachID, String status, long ngayKy, long ngayHetHan, List<NhaDangKi> nhaDangKiList) {
        this.id = id;
        this.khachID = khachID;
        this.status = status;
        this.ngayKy = ngayKy;
        this.ngayHetHan = ngayHetHan;
        this.nhaDangKiList = nhaDangKiList;
    }

    // Getters và setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKhachID() {
        return khachID;
    }

    public void setKhachID(String khachID) {
        this.khachID = khachID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public long getNgayKy() {
        return ngayKy;
    }

    public void setNgayKy(long ngayKy) {
        this.ngayKy = ngayKy;
    }

    public long getNgayHetHan() {
        return ngayHetHan;
    }

    public void setNgayHetHan(long ngayHetHan) {
        this.ngayHetHan = ngayHetHan;
    }

    public List<NhaDangKi> getNhaDangKiList() {
        return nhaDangKiList;
    }

    public void setNhaDangKiList(List<NhaDangKi> nhaDangKiList) {
        this.nhaDangKiList = nhaDangKiList;
    }
}