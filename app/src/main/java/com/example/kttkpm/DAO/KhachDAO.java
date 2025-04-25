package com.example.kttkpm.DAO;

import android.widget.Toast;

import androidx.annotation.NonNull;

import com.example.kttkpm.models.Khach;
import com.example.kttkpm.utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class KhachDAO {
    private DatabaseReference khachRef;

    public KhachDAO(){
        khachRef = FirebaseUtil.getKhachRef();
    }

    public interface KhachListCallback {
        void onKhachListLoaded(List<Khach> khachList);
        void onCancelled(String errorMessage);
    }

    public interface AddKhachCallback {
        void onSuccess();

        void onError(String errorMessage);

    }
    public interface UpdateKhachCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
    public interface DeleteKhachCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    // Thêm khách hàng mới
    public void addKhach(Khach khach, final AddKhachCallback callback) {
        khach.setId(khach.getId());

        khachRef.child(khach.getId()).setValue(khach)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());// Gọi onError() khi thất bại
                });
    }

    // Lấy toàn bộ danh sách khách hàng
    public void getAllKhach(List<Khach> khachList,final KhachListCallback callback) {
        khachRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                khachList.clear();
                for (DataSnapshot khachSnapshot : dataSnapshot.getChildren()) {
                    Khach khach = khachSnapshot.getValue(Khach.class);
                    if (khach != null) {
                        khachList.add(khach);
                    }
                    callback.onKhachListLoaded(khachList);
                }

                System.out.println("Nap thanh cong " + khachList.size());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần

            }
        });
    }

    public void updateKhach(Khach khach, final UpdateKhachCallback callback) {
        khachRef.child(khach.getId()).setValue(khach)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());// Gọi onError() khi thất bại
                });
    }
    // Xóa khách hàng theo ID
    public void deleteKhach(String khachid, final DeleteKhachCallback callback) {
        khachRef.child(khachid).removeValue()
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());// Gọi onError() khi thất bại

                });
    }
}
