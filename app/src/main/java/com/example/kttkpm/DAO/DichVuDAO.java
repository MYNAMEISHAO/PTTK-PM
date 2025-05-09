package com.example.kttkpm.DAO;

import androidx.annotation.NonNull;

import com.example.kttkpm.models.DichVu;
import com.example.kttkpm.utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class DichVuDAO {
    private DatabaseReference dichVuRef;

    public DichVuDAO() {
        dichVuRef = FirebaseUtil.getDichVuRef();
    }

    public interface DichVuListCallback {
        void onDichVuListLoaded(List<DichVu> list);
        void onCancelled(String errorMessage);
    }

    public interface AddDichVuCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public interface UpdateDichVuCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    public interface DeleteDichVuCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    // Thêm dịch vụ mới
    public void addDichVu(DichVu dichVu, final AddDichVuCallback callback) {
        dichVu.setId(dichVu.getId());

        dichVuRef.child(dichVu.getId()).setValue(dichVu)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage()); // Gọi onError() khi thất bại
                });
    }

    // Lấy toàn bộ danh sách dịch vụ
    public void getAllDichVu(List<DichVu> dichVuList, final DichVuListCallback callback) {
        dichVuRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dichVuList.clear();
                for (DataSnapshot dichVuSnapshot : dataSnapshot.getChildren()) {
                    DichVu dichVu = dichVuSnapshot.getValue(DichVu.class);
                    if (dichVu != null) {
                        dichVuList.add(dichVu);
                    }
                }
                callback.onDichVuListLoaded(dichVuList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled(databaseError.getMessage());
            }
        });
    }

    // Cập nhật dịch vụ
    public void updateDichVu(DichVu dichVu, final UpdateDichVuCallback callback) {
        dichVuRef.child(dichVu.getId()).setValue(dichVu)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage()); // Gọi onError() khi thất bại
                });
    }

    // Xóa dịch vụ theo ID
    public void deleteDichVu(String dichVuId, final DeleteDichVuCallback callback) {
        dichVuRef.child(dichVuId).removeValue()
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage()); // Gọi onError() khi thất bại
                });
    }
}
