package com.example.kttkpm.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kttkpm.R;
import com.example.kttkpm.activity.kyhopdong.KiHopDongActivity;
import com.example.kttkpm.activity.quanly.QuanLyKhachActivity;
import com.google.android.material.card.MaterialCardView;

public class HomeActivity extends AppCompatActivity {

    private MaterialCardView cardQuanLyKhachHang, cardKyHopDong;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home);

        cardQuanLyKhachHang = findViewById(R.id.cardQuanLyKhachHang);
        cardKyHopDong = findViewById(R.id.cardKyHopDong);

        cardQuanLyKhachHang.setOnClickListener(v -> goToQuanLyKhach());
        cardKyHopDong.setOnClickListener(v -> goToKiHopDong());
    }

    public void goToQuanLyKhach() {
        Intent intent = new Intent(this, QuanLyKhachActivity.class);
        startActivity(intent);
    }

    public void goToKiHopDong(){
        Intent intent = new Intent(this, KiHopDongActivity.class);
        startActivity(intent);
    }
}
