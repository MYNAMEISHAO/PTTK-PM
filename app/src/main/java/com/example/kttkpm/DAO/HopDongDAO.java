package com.example.kttkpm.DAO;

import androidx.annotation.NonNull;

import com.example.kttkpm.models.HopDong;
import com.example.kttkpm.utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class HopDongDAO {
    private DatabaseReference hopDongRef;

    public HopDongDAO() {
        hopDongRef = FirebaseUtil.getHopDongRef();
    }

    public interface HopDongListCallback {
        void onHopDongListLoaded(List<HopDong> list);
        void onCancelled(String errorMessage);
    }

    public interface AddHopDongCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    // Thêm hợp đồng mới
    public void addHopDong(HopDong hopDong, final AddHopDongCallback callback) {
        hopDong.setId(hopDong.getId());

        hopDongRef.child(hopDong.getId()).setValue(hopDong)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage()); // Gọi onError() khi thất bại
                });
    }

    // Lấy toàn bộ danh sách hợp đồng
    public void getAllHopDong(List<HopDong> hopDongList, final HopDongListCallback callback) {
        // Kiểm tra nếu danh sách là null, tạo mới danh sách
        final List<HopDong> safeList = (hopDongList != null) ? hopDongList : new ArrayList<>();
        
        hopDongRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                safeList.clear(); // Xóa dữ liệu cũ để tránh trùng lặp
                for (DataSnapshot hopDongSnapshot : dataSnapshot.getChildren()) {
                    HopDong hopDong = hopDongSnapshot.getValue(HopDong.class);
                    if (hopDong != null) {
                        safeList.add(hopDong);
                    }
                }
                callback.onHopDongListLoaded(safeList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                callback.onCancelled(databaseError.getMessage());
            }
        });
    }


}
