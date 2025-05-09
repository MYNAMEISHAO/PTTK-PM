package com.example.kttkpm.DAO;

import androidx.annotation.NonNull;

import com.example.kttkpm.models.Khach;
import com.example.kttkpm.models.Nha;
import com.example.kttkpm.utils.FirebaseUtil;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class NhaDAO{
    private DatabaseReference nhaRef;
    public NhaDAO(){
        nhaRef = FirebaseUtil.getNhaRef();
    }

    public interface AllNhaListCallback {
        void onNhaListLoaded(List<Nha> list);
        void onCancelled(String errorMessage);
    }
    public interface NhaListCallback {
        void onNhaListLoaded(List<Nha> list);
        void onCancelled(String errorMessage);
    }
    public interface AddNhaCallback {
        void onSuccess();

        void onError(String errorMessage);

        void onFailure();
    }
    public interface UpdateNhaCallback {
        void onSuccess();
        void onError(String errorMessage);
    }
    public interface DeleteNhaCallback {
        void onSuccess();
        void onError(String errorMessage);
    }

    // Thêm Nha mới cho khách
    public void addNha(Nha nha, final KhachDAO.AddKhachCallback callback) {
        nha.setID(nha.getID());
        nhaRef.child(nha.getID()).setValue(nha)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());// Gọi onError() khi thất bại
                });
    }

    // Lấy toàn bộ danh sách khách hàng
    public void getAllNhaOfKhach(String khachID,List<Nha> nhaList,final NhaDAO.NhaListCallback callback) {
        nhaRef.orderByChild("khachID").equalTo(khachID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nhaList.clear();
                for (DataSnapshot nhaSnapshot : dataSnapshot.getChildren()) {
                    Nha nha = nhaSnapshot.getValue(Nha.class);
                    if (nha != null) {
                        nhaList.add(nha);
                        System.out.println("Nap thanh cong nha " + nha.getAddress());
                    }
                    callback.onNhaListLoaded(nhaList);
                }

                System.out.println("Nap thanh cong " + nhaList.size());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần

            }
        });
    }

    public void updateNha(Nha nha, final NhaDAO.UpdateNhaCallback callback) {
        nhaRef.child(nha.getID()).setValue(nha)
                .addOnSuccessListener(aVoid -> {
                    callback.onSuccess(); // Gọi onSuccess() khi thành công
                })
                .addOnFailureListener(e -> {
                    callback.onError(e.getMessage());// Gọi onError() khi thất bại
                });
    }
    // Xóa khách hàng theo ID
    public void deleteNha(String khachId, final NhaDAO.DeleteNhaCallback callback) {
        nhaRef.child(khachId).removeValue().addOnSuccessListener(aVoid -> {
            callback.onSuccess(); // Gọi onSuccess() khi thành công
        }).addOnFailureListener(e -> {
            callback.onError(e.getMessage());// Gọi onError() khi thất bại
        });
    }

    public void getAllNha(List<Nha> nhaList,final NhaDAO.AllNhaListCallback callback) {
        nhaRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                nhaList.clear();
                for (DataSnapshot nhaSnapshot : dataSnapshot.getChildren()) {
                    Nha nha = nhaSnapshot.getValue(Nha.class);
                    if (nha != null) {
                        nhaList.add(nha);
                    }
                    callback.onNhaListLoaded(nhaList);
                }

                System.out.println("Nap thanh cong " + nhaList.size());
            }


            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // Xử lý lỗi nếu cần

            }
        });
    };

    public Nha getNhaByID(String nhaID, List<Nha> nhaList) {
        for (Nha nha : nhaList) {
            if (nha.getID().equals(nhaID)) {
                return nha;
            }
        }
        return null;
    }


}
