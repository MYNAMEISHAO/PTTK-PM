package com.example.kttkpm.activity.quanly;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kttkpm.DAO.KhachDAO;
import com.example.kttkpm.R;
import com.example.kttkpm.models.Khach;
import com.example.kttkpm.helper.IDGenerate;

import java.util.ArrayList;
import java.util.List;

public class ThemKhachActivity extends AppCompatActivity {

    EditText edtTenKhach, edtEmail, edtSDT;
    Button btnThemKhach, btnTroVe;
    KhachDAO kd;
    List<Khach> khachList;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themkhach);

        edtTenKhach = findViewById(R.id.etTen);
        edtEmail = findViewById(R.id.etEmail);
        edtSDT = findViewById(R.id.etPhone);

        khachList = new ArrayList<>();
        kd = new KhachDAO();

        btnTroVe = findViewById(R.id.btnBack);
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnThemKhach = findViewById(R.id.btnLuuKhach);
        btnThemKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemKhach();
            }
        });

        kd.getAllKhach(khachList,new KhachDAO.KhachListCallback(){
            @Override
            public void onKhachListLoaded(List<Khach> list) {
                khachList = list;
                System.out.println("Size laf "+khachList.size());
            }

            @Override
            public void onCancelled(String errorMessage) {

            }
        });

    }

    public void ThemKhach() {
        String ten;
        String email;
        String sdt;
        String id;
        ten = edtTenKhach.getText().toString().trim();
        email = edtEmail.getText().toString().trim();
        sdt = edtSDT.getText().toString().trim();
        IDGenerate idg = new IDGenerate();
        if (ten.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }
        System.out.println("Size laf "+khachList.size());
        id = idg.generateKhachID(khachList);


        kd.addKhach(new Khach(id, ten, email, sdt), new KhachDAO.AddKhachCallback() {
            @Override
            public void onSuccess() {
                runOnUiThread(() -> {
                    Toast.makeText(ThemKhachActivity.this, "Thêm khách hàng thành công", Toast.LENGTH_SHORT).show();
                    finish(); //kết thúc activity
                });
            }

            @Override
            public void onError(String errorMessage) {
                runOnUiThread(() -> {
                    Toast.makeText(ThemKhachActivity.this, "Lỗi thêm khách hàng: " + errorMessage, Toast.LENGTH_SHORT).show();

                });
            }

        });


    }



}
