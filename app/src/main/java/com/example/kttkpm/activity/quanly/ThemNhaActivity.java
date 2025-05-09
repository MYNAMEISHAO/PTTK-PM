package com.example.kttkpm.activity.quanly;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kttkpm.DAO.KhachDAO;
import com.example.kttkpm.DAO.NhaDAO;
import com.example.kttkpm.R;
import com.example.kttkpm.models.Nha;
import com.example.kttkpm.helper.IDGenerate;

import java.util.ArrayList;
import java.util.List;

public class ThemNhaActivity extends AppCompatActivity {

    EditText etDiaChi, etDienTich, etHouseType, etResident;
    Button btnLuuNha;
    NhaDAO nhaDAO;
    List<Nha> nhaList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.themnha);

        etDiaChi = findViewById(R.id.etDiaChi);
        etDienTich = findViewById(R.id.etDienTich);
        etHouseType = findViewById(R.id.etHouseType);
        etResident = findViewById(R.id.etResident);
        nhaList = new ArrayList<>();
        nhaDAO = new NhaDAO();
        btnLuuNha = findViewById(R.id.btnLuuNha);

        btnLuuNha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ThemNha();
            }
        });

       nhaDAO.getAllNha(nhaList, new NhaDAO.AllNhaListCallback() {
           @Override
           public void onNhaListLoaded(List<Nha> list) {
                nhaList = list;
                System.out.println("Size laf "+ nhaList.size());
           }

           @Override
           public void onCancelled(String errorMessage) {

           }
       });
    }

    void ThemNha() {
        String diaChi = etDiaChi.getText().toString().trim();
        float dienTich = Float.parseFloat(etDienTich.getText().toString().trim());
        String houseType = etHouseType.getText().toString();
        int resident = Integer.parseInt(etResident.getText().toString().trim());
        String khachID = getIntent().getStringExtra("khachID");
        IDGenerate idg = new IDGenerate();
        String idNha = idg.generateNhaID(nhaList);

        nhaDAO.addNha(new Nha(idNha, diaChi, houseType, dienTich, resident, khachID), new KhachDAO.AddKhachCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(ThemNhaActivity.this, "Thêm nhà thành công", Toast.LENGTH_SHORT).show();
                finish(); //kết thúc activity
            }

            @Override
            public void onError(String errorMessage) {

            }
        });
    }
}
