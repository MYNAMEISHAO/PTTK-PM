package com.example.kttkpm.activity.quanly;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.kttkpm.DAO.NhaDAO;
import com.example.kttkpm.R;
import com.example.kttkpm.models.Nha;

public class ChiTietNhaActivity extends AppCompatActivity {
    Button btnTroVe2, btnSua, btnXoa;
    EditText DiaChi, NhaID, Area, HouseType, Residents, KhachID;
    NhaDAO nd;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.chitietnha);

        btnTroVe2 = findViewById(R.id.btnTroVe2);
        btnTroVe2.setOnClickListener(v -> finish());

        DiaChi = findViewById(R.id.edtAddress);
        NhaID = findViewById(R.id.edtIDNha);
        Area = findViewById(R.id.edtArea);
        HouseType = findViewById(R.id.edtHouseType);
        Residents = findViewById(R.id.edtResident);
        KhachID = findViewById(R.id.edtKhachID);

        DiaChi.setText(getIntent().getStringExtra("nhaAddress"));
        NhaID.setText(getIntent().getStringExtra("nhaID"));
        Area.setText(String.valueOf(getIntent().getFloatExtra("nhaArea", 0)));
        HouseType.setText(getIntent().getStringExtra("nhaHouseType"));
        Residents.setText(String.valueOf(getIntent().getIntExtra("nhaResident", 0)));
        KhachID.setText(getIntent().getStringExtra("khachID"));

        nd = new NhaDAO();

        btnSua = findViewById(R.id.btnSuaNha);
        btnSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SuaNha();

            }
        });
        btnXoa = findViewById(R.id.btnXoaNha);
        btnXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                XoaNha();
            }
        });
    }

    public void SuaNha() {
        String nhaID = NhaID.getText().toString();
        String nhaDiaChi = DiaChi.getText().toString();
        float nhaArea = Float.parseFloat(Area.getText().toString());
        String nhaHouseType = HouseType.getText().toString();
        int nhaResident = Integer.parseInt(Residents.getText().toString());
        String khachID = KhachID.getText().toString();

        nd.updateNha(new Nha(nhaID, nhaDiaChi, nhaHouseType, nhaArea, nhaResident, khachID), new NhaDAO.UpdateNhaCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(ChiTietNhaActivity.this, "Sửa thành công", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onError(String errorMessage) {

            }
        });

    }

    public void XoaNha() {
        new AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa nhà này không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Gọi hàm xóa ở đây
                    nd.deleteNha(NhaID.getText().toString(), new NhaDAO.DeleteNhaCallback() {
                        @Override
                        public void onSuccess() {
                            Toast.makeText(ChiTietNhaActivity.this, "Xóa thành công", Toast.LENGTH_SHORT).show();
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
