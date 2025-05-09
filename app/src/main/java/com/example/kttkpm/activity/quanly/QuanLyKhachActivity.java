package com.example.kttkpm.activity.quanly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.DAO.KhachDAO;
import com.example.kttkpm.OnKhachClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.adapters.KhachAdapter;
import com.example.kttkpm.models.Khach;


import java.util.ArrayList;
import java.util.List;

public class QuanLyKhachActivity extends AppCompatActivity implements OnKhachClickListener {

    private RecyclerView recyclerView;
    private KhachAdapter khachAdapter;
    private List<Khach> khachList;
    private Button btnThemKhach, btnTroVeHome;
    private KhachDAO kd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quanlykhach);

        recyclerView = findViewById(R.id.recyclerKhach);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        khachList = new ArrayList<>();

        kd = new KhachDAO();
        loadKhach();

        btnThemKhach = findViewById(R.id.btnThemKhachHang);
        btnThemKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToThemKhach();
            }
        });

        btnTroVeHome = findViewById(R.id.btnTroVeHome);
        btnTroVeHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    public void loadKhach() {
        kd.getAllKhach(khachList,new KhachDAO.KhachListCallback() {
            @Override
            public void onKhachListLoaded(List<Khach> list) {
                khachList = list;
                khachAdapter = new KhachAdapter(khachList, QuanLyKhachActivity.this);
                recyclerView.setAdapter(khachAdapter);
            }

            @Override
            public void onCancelled(String errorMessage) {

            }
        });

    }


    public void onKhachClick(Khach khach) {
        Toast.makeText(this, "Clicked on: " + khach.getName() , Toast.LENGTH_SHORT).show();
        goToChiTietKhach(khach);
        // Xử lý sự kiện khi khách hàng được chọn
    }

    public void goToChiTietKhach(Khach khach){
        Intent intent = new Intent(this, ChiTietKhachActivity.class);
        intent.putExtra("khachName", khach.getName());
        intent.putExtra("khachPhone", khach.getPhone());
        intent.putExtra("khachEmail", khach.getEmail());
        intent.putExtra("khachId", khach.getId());
        startActivity(intent);
    }

    public void goToThemKhach(){
        Intent intent = new Intent(this, ThemKhachActivity.class);
        startActivity(intent);
    }
}
