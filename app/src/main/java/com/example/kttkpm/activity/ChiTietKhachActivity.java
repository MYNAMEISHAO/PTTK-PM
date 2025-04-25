package com.example.kttkpm.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.DAO.KhachDAO;
import com.example.kttkpm.DAO.NhaDAO;
import com.example.kttkpm.OnNhaClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.adapters.NhaAdapter;
import com.example.kttkpm.models.Khach;
import com.example.kttkpm.models.Nha;

import java.util.ArrayList;
import java.util.List;

public class ChiTietKhachActivity extends AppCompatActivity implements OnNhaClickListener {

    private RecyclerView recyclerView;
    private NhaAdapter nhaAdapter;
    private List<Nha> nhaList;
    private Button btnTroVe,btnThemNha,btnSuaKhach,btnXoaKhach;
    private TextView txtName, txtPhone, txtEmail, txtIDKhach;
    private NhaDAO nd;
    private KhachDAO kd;

    private ActivityResultLauncher<Intent> launcher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietkhach);

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtEmail = findViewById(R.id.txtEmail);
        txtIDKhach = findViewById(R.id.txtIDKhach);

        btnTroVe = findViewById(R.id.btnTroVe);
        btnThemNha = findViewById(R.id.btnThemNha);

        recyclerView = findViewById(R.id.recyclerNha);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        nd = new NhaDAO();
        kd = new KhachDAO();
        nhaList = new ArrayList<>();

        txtName.setText(getIntent().getStringExtra("khachName"));
        txtPhone.setText(getIntent().getStringExtra("khachPhone"));
        txtEmail.setText(getIntent().getStringExtra("khachEmail"));
        txtIDKhach.setText(getIntent().getStringExtra("khachId"));

        loadNha(txtIDKhach.getText().toString());

        btnTroVe.setOnClickListener(v -> finish());
        btnSuaKhach = findViewById(R.id.btnSuaKhach);
        btnSuaKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToSuaKhach();
            }
        });

        btnXoaKhach = findViewById(R.id.btnXoaKhach);
        btnXoaKhach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaKhach();
            }
        });


        btnThemNha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToThemNha();
            }
        });

        setupLauncher();
    }

    @Override
    public void onNhaClick(Nha nha) {
        goToChiTietNha(nha);
    }

    private void setupLauncher() {
        launcher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        Nha updatedNha = (Nha) result.getData().getSerializableExtra("updated_nha");
                        updateNhaInList(updatedNha); // 🟢 GỌI Ở ĐÂY!
                    }
                }
        );
    }

    public void goToChiTietNha(Nha nha){
        Intent intent = new Intent(this, ChiTietNhaActivity.class);
        intent.putExtra("nhaAddress", nha.getAddress());
        intent.putExtra("nhaID", nha.getID());
        intent.putExtra("nhaArea", nha.getArea());;
        intent.putExtra("nhaHouseType", nha.getHouseType());
        intent.putExtra("nhaResident", nha.getResident());
        intent.putExtra("khachID", nha.getKhachID());
        launcher.launch(intent);
    }

    public void goToThemNha(){
        Intent intent = new Intent(this, ThemNhaActivity.class);
        intent.putExtra("khachID", txtIDKhach.getText().toString());
        startActivity(intent);
    }

    public void goToSuaKhach(){
        Intent intent = new Intent(this, SuaKhachActivity.class);
        intent.putExtra("khachName", txtName.getText().toString());
        intent.putExtra("khachPhone", txtPhone.getText().toString());
        intent.putExtra("khachEmail", txtEmail.getText().toString());
        intent.putExtra("khachId", txtIDKhach.getText().toString());
        startActivity(intent);

    }

    void loadNha(String khachID){
        nd.getAllNhaOfKhach(khachID, nhaList, new NhaDAO.NhaListCallback() {
            @Override
            public void onNhaListLoaded(List<Nha> list) {
                nhaAdapter = new NhaAdapter(nhaList,ChiTietKhachActivity.this);
                recyclerView.setAdapter(nhaAdapter);
            }

            @Override
            public void onCancelled(String errorMessage) {

            }
        });
    }

    private void updateNhaInList(Nha nha){
        for(int i = 0; i < nhaList.size(); i++){
            if(nhaList.get(i).getID() == nha.getID()){
                nhaList.set(i, nha);
                nhaAdapter.notifyItemChanged(i);
                break;
            }
        }
    }

    public void XoaKhach(){
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa nhà này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Gọi hàm xóa ở đây
                    kd.deleteKhach(txtIDKhach.getText().toString(), new KhachDAO.DeleteKhachCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ChiTietKhachActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onError(String errorMessage) {

                        }
                    });


                })
                .setNegativeButton("Hủy", null)
                .show();



    }

}
