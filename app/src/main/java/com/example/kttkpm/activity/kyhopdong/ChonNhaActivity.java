package com.example.kttkpm.activity.kyhopdong;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.DAO.NhaDAO;
import com.example.kttkpm.OnNhaClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.adapters.NhaAdapter;
import com.example.kttkpm.models.Nha;

import java.util.ArrayList;
import java.util.List;

public class ChonNhaActivity extends AppCompatActivity implements OnNhaClickListener {

    private RecyclerView recyclerView;
    private NhaAdapter adapter;
    private List<Nha> nhaList;
    private Button btnTroVe;
    private NhaDAO nhaDAO;
    private String khachID;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chon_nha);

        // Lấy khachID từ intent
        khachID = getIntent().getStringExtra("khachID");
        if (khachID == null) {
            Toast.makeText(this, "Không tìm thấy thông tin khách hàng", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Khởi tạo các thành phần giao diện
        recyclerView = findViewById(R.id.recyclerNha);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnTroVe = findViewById(R.id.btnTroVe);

        // Khởi tạo danh sách và DAO
        nhaList = new ArrayList<>();
        nhaDAO = new NhaDAO();

        // Thiết lập adapter
        adapter = new NhaAdapter(nhaList, this);
        recyclerView.setAdapter(adapter);

        // Thiết lập sự kiện cho các nút
        btnTroVe.setOnClickListener(v -> finish());

        // Tải danh sách nhà của khách hàng
        loadNhaList();
    }

    private void loadNhaList() {
        nhaDAO.getAllNhaOfKhach(khachID, nhaList, new NhaDAO.NhaListCallback() {
            @Override
            public void onNhaListLoaded(List<Nha> list) {
                adapter.notifyDataSetChanged();
                
                if (list.isEmpty()) {
                    Toast.makeText(ChonNhaActivity.this, "Khách hàng chưa có nhà nào", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(String errorMessage) {
                Toast.makeText(ChonNhaActivity.this, "Lỗi tải danh sách nhà: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNhaClick(Nha nha) {
        Intent resultIntent = new Intent();
        resultIntent.putExtra("selectedNha", nha);
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
