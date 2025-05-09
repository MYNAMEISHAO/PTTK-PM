package com.example.kttkpm.activity.kyhopdong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;


import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.DAO.DichVuDAO;
import com.example.kttkpm.OnDichVuClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.adapters.DichVuAdapter;
import com.example.kttkpm.models.DichVu;

import java.util.ArrayList;
import java.util.List;

public class ChonDichVuActivity extends AppCompatActivity implements OnDichVuClickListener {

    private RecyclerView recyclerView;
    private DichVuAdapter adapter;
    private List<DichVu> dichVuList;
    private Button btnTroVe;
    private DichVuDAO dichVuDAO;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_dichvu);

        // Khởi tạo các thành phần giao diện
        recyclerView = findViewById(R.id.recyclerDichVu);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnTroVe = findViewById(R.id.btnReturn);

        // Khởi tạo danh sách và DAO
        dichVuList = new ArrayList<>();
        dichVuDAO = new DichVuDAO();

        // Thiết lập adapter
        adapter = new DichVuAdapter(dichVuList, this);
        recyclerView.setAdapter(adapter);

        // Thiết lập sự kiện cho các nút
        btnTroVe.setOnClickListener(v -> finish());

        // Tải danh sách dịch vụ hoặc tạo dữ liệu mẫu nếu chưa có
        loadDichVuList();
    }

    private void loadDichVuList() {
        dichVuDAO.getAllDichVu(dichVuList, new DichVuDAO.DichVuListCallback() {
            @Override
            public void onDichVuListLoaded(List<DichVu> list) {
                list = dichVuList;
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(String errorMessage) {
                Toast.makeText(ChonDichVuActivity.this, "Lỗi tải danh sách dịch vụ: " + errorMessage, Toast.LENGTH_SHORT).show();
                // Tạo dữ liệu mẫu nếu có lỗi
            }
        });
    }


    @Override
    public void onDichVuClick(DichVu dichVu) {
        // Xử lý khi người dùng chọn một dịch vụ
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedDichVu", dichVu);
        setResult(RESULT_OK, resultIntent);
        finish();
    }


}
