package com.example.kttkpm.activity.quanly;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kttkpm.DAO.KhachDAO;
import com.example.kttkpm.R;
import com.example.kttkpm.models.Khach;

public class SuaKhachActivity extends AppCompatActivity {
    TextView txtMaKhach;
    EditText edtTenKhach, edtEmail, edtSDT;
    Button btnLuu, btnTroVe;

    KhachDAO kd;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.suakhach);
        txtMaKhach = findViewById(R.id.txtIDKhachSua);
        edtTenKhach = findViewById(R.id.edtName);
        edtEmail = findViewById(R.id.edtEmail);
        edtSDT = findViewById(R.id.edtPhone);

        txtMaKhach.setText(getIntent().getStringExtra("khachId"));
        edtTenKhach.setText(getIntent().getStringExtra("khachName"));
        edtEmail.setText(getIntent().getStringExtra("khachEmail"));
        edtSDT.setText(getIntent().getStringExtra("khachPhone"));

        kd = new KhachDAO();

        btnLuu = findViewById(R.id.btnLuuSuaKhach);
        btnTroVe = findViewById(R.id.btnTroVe);
        btnLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SuaKhach();
            }
        });

        btnTroVe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    void SuaKhach(){
        String maKhach = txtMaKhach.getText().toString();
        String tenKhach = edtTenKhach.getText().toString();
        String email = edtEmail.getText().toString();
        String sdt = edtSDT.getText().toString();

        if (tenKhach.isEmpty() || email.isEmpty() || sdt.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

        kd.updateKhach(new Khach(maKhach, tenKhach, email, sdt), new KhachDAO.UpdateKhachCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(SuaKhachActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(SuaKhachActivity.this, ChiTietKhachActivity.class);
                intent.putExtra("khachId", maKhach);
                intent.putExtra("khachName", tenKhach);
                intent.putExtra("khachEmail", email);
                intent.putExtra("khachPhone", sdt);
                startActivity(intent);
                finish();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
