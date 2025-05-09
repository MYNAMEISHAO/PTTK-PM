package com.example.kttkpm.activity.kyhopdong;

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

public class KiHopDongActivity extends AppCompatActivity implements OnKhachClickListener {

    private RecyclerView recyclerView;
    private KhachAdapter khachAdapter;
    private List<Khach> khachList;
    private Button btnTroVe;
    private KhachDAO kd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kyhopdong);

        // Khởi tạo các thành phần giao diện
        recyclerView = findViewById(R.id.recyclerKhach);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        btnTroVe = findViewById(R.id.btnTroVe);

        // Khởi tạo danh sách và DAO
        khachList = new ArrayList<>();
        kd = new KhachDAO();

        // Thiết lập sự kiện cho nút trở về
        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        // Tải danh sách khách hàng
        loadKhach();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Tải lại danh sách khi quay lại màn hình
        loadKhach();
    }

    /**
     * Tải danh sách khách hàng từ cơ sở dữ liệu
     */
    public void loadKhach() {
        kd.getAllKhach(khachList, new KhachDAO.KhachListCallback() {
            @Override
            public void onKhachListLoaded(List<Khach> list) {
                khachList = list;
                khachAdapter = new KhachAdapter(khachList, KiHopDongActivity.this);
                recyclerView.setAdapter(khachAdapter);
            }

            @Override
            public void onCancelled(String errorMessage) {
                Toast.makeText(KiHopDongActivity.this, "Lỗi tải danh sách khách hàng: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    /**
     * Xử lý sự kiện khi người dùng nhấp vào một khách hàng
     */
    @Override
    public void onKhachClick(Khach khach) {
        Toast.makeText(this, "Đã chọn khách hàng: " + khach.getName(), Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, DanhSachNhaDangKiActivity.class);
        intent.putExtra("khachID", khach.getId());
        startActivity(intent);
    }
}
