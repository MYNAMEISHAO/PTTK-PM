package com.example.kttkpm.activity.kyhopdong;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.DAO.HopDongDAO;
import com.example.kttkpm.DAO.NhaDAO;
import com.example.kttkpm.OnNhaDangKiClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.adapters.NhaDangKiAdapter;
import com.example.kttkpm.helper.IDGenerate;
import com.example.kttkpm.models.DichVu;
import com.example.kttkpm.models.HopDong;
import com.example.kttkpm.models.Nha;
import com.example.kttkpm.models.NhaDangKi;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class DanhSachNhaDangKiActivity extends AppCompatActivity implements OnNhaDangKiClickListener {

    private RecyclerView recyclerView;
    private NhaDangKiAdapter adapter;
    private List<NhaDangKi> nhaDangKiList;
    private Button btnTroVe, btnThem, btnXacNhan;
    private HopDongDAO hopDongDAO;
    private Nha currentSelectedNha;
    private String khachID;
    private List<HopDong> hopDongList;
    private ActivityResultLauncher<Intent> chonNhaLauncher, chonDichVuLauncher;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.danhsachhopdong);

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
        btnThem = findViewById(R.id.btnThem);
        btnXacNhan = findViewById(R.id.btnXacnhan1);

        // Khởi tạo danh sách và DAO
        hopDongDAO = new HopDongDAO();
        nhaDangKiList = new ArrayList<>();
        hopDongList = new ArrayList<>();

        adapter = new NhaDangKiAdapter(nhaDangKiList, this);
        recyclerView.setAdapter(adapter);

        setupLaunchers();
        loadHopDongList();

        // Thiết lập sự kiện cho các nút
        btnTroVe.setOnClickListener(v -> finish());
        btnThem.setOnClickListener(v -> {
            // TODO: Thêm xử lý khi người dùng muốn thêm nhà mới
            Toast.makeText(this, "Chức năng thêm nhà đang được phát triển", Toast.LENGTH_SHORT).show();
            goToChonNha();
        });
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHopDong();
            }
        });



    }


    private void setupLaunchers() {
        // Launcher cho việc chọn nhà
        chonNhaLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Lấy nhà đã chọn từ kết quả trả về
                        currentSelectedNha = (Nha) result.getData().getSerializableExtra("selectedNha");
                        if (currentSelectedNha != null) {
                            // Mở màn hình chọn dịch vụ
                            Intent intent = new Intent(this, ChonDichVuActivity.class);
                            intent.putExtra("selectedNha", currentSelectedNha);
                            chonDichVuLauncher.launch(intent);
                        }
                    }
                }
        );

        // Launcher cho việc chọn dịch vụ
        chonDichVuLauncher = registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(),
                result -> {
                    if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                        // Lấy dịch vụ đã chọn từ kết quả trả về
                        DichVu selectedDichVu = (DichVu) result.getData().getSerializableExtra("selectedDichVu");
                        if (selectedDichVu != null && currentSelectedNha != null) {
                            // Tạo đối tượng NhaDangKi từ nhà và dịch vụ đã chọn
                            // Tạo ID cho NhaDangKi
                            String nhaDangKiID = "NDK" + System.currentTimeMillis();
                            NhaDangKi nhaDangKi = new NhaDangKi(currentSelectedNha.getID(), nhaDangKiID, selectedDichVu.getId());
                            android.util.Log.d("DanhSachNhaDangKiActivity", "Created NhaDangKi: nhaID=" + currentSelectedNha.getID() + ", ID=" + nhaDangKiID + ", dichVuID=" + selectedDichVu.getId());

                            // Thêm vào danh sách và cập nhật giao diện
                            boolean isDuplicate = false;
                            for (NhaDangKi item : nhaDangKiList) {
                                if (item.getNhaID().equals(currentSelectedNha.getID())) {
                                    // Nếu nhà đã tồn tại, cập nhật dịch vụ
                                    item.setDichVu(selectedDichVu.getId());
                                    isDuplicate = true;
                                    break;
                                }
                            }

                            // Nếu nhà chưa tồn tại, thêm mới vào danh sách
                            if (!isDuplicate) {
                                nhaDangKiList.add(nhaDangKi);
                                // Thêm nhà và dịch vụ vào adapter để hiển thị
                                adapter.addNhaAndDichVu(currentSelectedNha, selectedDichVu);
                            }
                            adapter.notifyDataSetChanged();

                            Toast.makeText(this, "Đã thêm nhà với dịch vụ: " + selectedDichVu.getTenDichVu(),
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );
    }

    private void goToChonNha() {
        Intent intent = new Intent(this, ChonNhaActivity.class);
        intent.putExtra("khachID", khachID);
        chonNhaLauncher.launch(intent);
    }

    private void saveHopDong() {
        String HopDongID, Status;
        long NgayBatDau, NgayKetThuc;

        IDGenerate idg = new IDGenerate();
        HopDongID = idg.generateHopDongID(hopDongList);
        Status = "Chờ xác nhận";
        NgayBatDau = System.currentTimeMillis();  // Lấy timestamp hiện tại
        System.out.println("Ngay bat dau: " + new Date(NgayBatDau));
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(NgayBatDau);
        calendar.add(Calendar.YEAR, 1000);  // Thêm 1000 năm do vô thời hạn
        NgayKetThuc = calendar.getTimeInMillis();
        System.out.println("Ngay bat dau: " + new Date(NgayKetThuc));

        HopDong hd = new HopDong(HopDongID, khachID, Status, NgayBatDau, NgayKetThuc, nhaDangKiList);
        hopDongDAO.addHopDong(hd, new HopDongDAO.AddHopDongCallback() {
            @Override
            public void onSuccess() {
                Toast.makeText(DanhSachNhaDangKiActivity.this,
                        "Đã lưu danh sách nhà đăng kí",
                        Toast.LENGTH_SHORT).show();
                setResult(RESULT_OK);
                finish();
            }

            @Override
            public void onError(String errorMessage) {
                Toast.makeText(DanhSachNhaDangKiActivity.this,
                        "Lỗi khi lưu: " + errorMessage,
                        Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadHopDongList() {
        hopDongDAO.getAllHopDong(hopDongList, new HopDongDAO.HopDongListCallback() {
            @Override
            public void onHopDongListLoaded(List<HopDong> list) {
                hopDongList = list;
            }

            @Override
            public void onCancelled(String errorMessage) {
                Toast.makeText(DanhSachNhaDangKiActivity.this, "Lỗi tải danh sách nhà đăng kí: " + errorMessage, Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onNhaDangKiClick(NhaDangKi nhaDangKi) {
        Toast.makeText(this, "Đã chọn nhà đăng kí", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onDeleteNhaClick(NhaDangKi nha, int position) {
        // Xử lý khi người dùng nhấn nút xóa
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("Xác nhận xóa")
                .setMessage("Bạn có chắc muốn xóa nhà này khỏi danh sách không?")
                .setPositiveButton("Xóa", (dialog, which) -> {
                    // Xóa nhà khỏi danh sách hiển thị
                    nhaDangKiList.remove(position);
                    adapter.notifyItemRemoved(position);
                    Toast.makeText(this, "Đã xóa nhà khỏi danh sách", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Hủy", null)
                .show();
    }
}


