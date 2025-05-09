package com.example.kttkpm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.DAO.DichVuDAO;
import com.example.kttkpm.DAO.NhaDAO;
import com.example.kttkpm.OnNhaDangKiClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.models.DichVu;
import com.example.kttkpm.models.Nha;
import com.example.kttkpm.models.NhaDangKi;

import java.util.ArrayList;
import java.util.List;

public class NhaDangKiAdapter extends RecyclerView.Adapter<NhaDangKiAdapter.NhaDangKiViewHolder> {
    private List<NhaDangKi> nhaDangKiList;
    private OnNhaDangKiClickListener onNhaDangKiClickListener;
    private NhaDAO nhaDAO;
    private List<Nha> nhaList;
    private List<DichVu> dichVuList;
    private DichVuDAO dichVuDAO;

    public NhaDangKiAdapter(List<NhaDangKi> nhaDangKiList, OnNhaDangKiClickListener onNhaDangKiClickListener) {
        // Đảm bảo danh sách không bao giờ là null
        this.nhaDangKiList = (nhaDangKiList != null) ? nhaDangKiList : new ArrayList<>();
        this.onNhaDangKiClickListener = onNhaDangKiClickListener;
        this.nhaDAO = new NhaDAO();
        this.dichVuDAO = new DichVuDAO();
        this.nhaList = new ArrayList<>();
        this.dichVuList = new ArrayList<>();
        loadData();
    }

    private void loadData() {
        android.util.Log.d("NhaDangKiAdapter", "Loading data from Firebase...");

        // Tải danh sách nhà và dịch vụ từ Firebase
        nhaDAO.getAllNha(nhaList, new NhaDAO.AllNhaListCallback() {
            @Override
            public void onNhaListLoaded(List<Nha> list) {
                android.util.Log.d("NhaDangKiAdapter", "Loaded " + list.size() + " nha from Firebase");
                nhaList.clear();
                nhaList.addAll(list);

                // Tải dịch vụ sau khi đã tải xong nhà
                loadDichVu();
            }

            @Override
            public void onCancelled(String errorMessage) {
                android.util.Log.e("NhaDangKiAdapter", "Error loading nha: " + errorMessage);
                // Vẫn tải dịch vụ ngay cả khi có lỗi
                loadDichVu();
            }
        });
    }

    private void loadDichVu() {
        dichVuDAO.getAllDichVu(dichVuList, new DichVuDAO.DichVuListCallback() {
            @Override
            public void onDichVuListLoaded(List<DichVu> list) {
                android.util.Log.d("NhaDangKiAdapter", "Loaded " + list.size() + " dichVu from Firebase");
                dichVuList.clear();
                dichVuList.addAll(list);
                notifyDataSetChanged();

                // In ra thông tin để debug
                logLoadedData();
            }

            @Override
            public void onCancelled(String errorMessage) {
                android.util.Log.e("NhaDangKiAdapter", "Error loading dichVu: " + errorMessage);
                notifyDataSetChanged();
            }
        });
    }

    private void logLoadedData() {
        android.util.Log.d("NhaDangKiAdapter", "=== Loaded Data Summary ===");
        android.util.Log.d("NhaDangKiAdapter", "NhaDangKi items: " + nhaDangKiList.size());

        for (int i = 0; i < nhaDangKiList.size(); i++) {
            NhaDangKi item = nhaDangKiList.get(i);
            android.util.Log.d("NhaDangKiAdapter", "Item " + i + ": nhaID=" + item.getNhaID() + ", dichVuID=" + item.getDichVu());
        }

        android.util.Log.d("NhaDangKiAdapter", "Nha items: " + nhaList.size());
        for (Nha nha : nhaList) {
            android.util.Log.d("NhaDangKiAdapter", "Nha: id=" + nha.getID() + ", address=" + nha.getAddress());
        }

        android.util.Log.d("NhaDangKiAdapter", "DichVu items: " + dichVuList.size());
        for (DichVu dichVu : dichVuList) {
            android.util.Log.d("NhaDangKiAdapter", "DichVu: id=" + dichVu.getId() + ", name=" + dichVu.getTenDichVu());
        }
    }

    @NonNull
    @Override
    public NhaDangKiViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nhadangki, parent, false);
        return new NhaDangKiViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhaDangKiViewHolder holder, int position) {
        NhaDangKi nhaDangKi = nhaDangKiList.get(position);
        android.util.Log.d("NhaDangKiAdapter", "Binding NhaDangKi at position " + position + " with nhaID: " + nhaDangKi.getNhaID() + ", ID: " + nhaDangKi.getID() + ", dichVuID: " + nhaDangKi.getDichVu());

        // Kiểm tra nhaList trước khi sử dụng
        if (nhaList != null && !nhaList.isEmpty()) {
            android.util.Log.d("NhaDangKiAdapter", "nhaList size: " + nhaList.size());
            Nha nha = findNhaById(nhaDangKi.getNhaID());
            if (nha != null) {
                holder.txtDiaChi.setText(nha.getAddress());
                holder.txtDienTich.setText(String.valueOf(nha.getArea()) + " m²");
                holder.txtLoaiNha.setText(nha.getHouseType());
                android.util.Log.d("NhaDangKiAdapter", "Set nha info: " + nha.getAddress() + ", " + nha.getArea() + ", " + nha.getHouseType());
            } else {
                holder.txtDiaChi.setText("Không tìm thấy thông tin nhà");
                holder.txtDienTich.setText("N/A");
                holder.txtLoaiNha.setText("N/A");
                android.util.Log.d("NhaDangKiAdapter", "Nha not found for ID: " + nhaDangKi.getNhaID());

                // In ra tất cả các ID trong danh sách để debug
                android.util.Log.d("NhaDangKiAdapter", "Available nha IDs:");
                for (Nha n : nhaList) {
                    android.util.Log.d("NhaDangKiAdapter", "  - " + n.getID() + ": " + n.getAddress());
                }
            }
        } else {
            holder.txtDiaChi.setText("Đang tải thông tin nhà...");
            holder.txtDienTich.setText("...");
            holder.txtLoaiNha.setText("...");
            android.util.Log.d("NhaDangKiAdapter", "nhaList is null or empty");
        }

        // Kiểm tra dichVuList trước khi sử dụng
        if (dichVuList != null && !dichVuList.isEmpty()) {
            android.util.Log.d("NhaDangKiAdapter", "dichVuList size: " + dichVuList.size());
            // Tìm đối tượng DichVu tương ứng
            DichVu dichVu = findDichVuById(nhaDangKi.getDichVu());
            if (dichVu != null) {
                holder.txtDichVu.setText(dichVu.getTenDichVu());
                android.util.Log.d("NhaDangKiAdapter", "Set dichVu info: " + dichVu.getTenDichVu());
            } else {
                holder.txtDichVu.setText("Chưa đăng ký");
                android.util.Log.d("NhaDangKiAdapter", "DichVu not found for ID: " + nhaDangKi.getDichVu());

                // In ra tất cả các ID trong danh sách để debug
                android.util.Log.d("NhaDangKiAdapter", "Available dichVu IDs:");
                for (DichVu dv : dichVuList) {
                    android.util.Log.d("NhaDangKiAdapter", "  - " + dv.getId() + ": " + dv.getTenDichVu());
                }
            }
        } else {
            holder.txtDichVu.setText("Đang tải thông tin dịch vụ...");
            android.util.Log.d("NhaDangKiAdapter", "dichVuList is null or empty");
        }

        // Xử lý sự kiện khi nhấn nút xóa
        holder.btnDelete.setOnClickListener(v -> {
            if (onNhaDangKiClickListener != null) {
                onNhaDangKiClickListener.onDeleteNhaClick(nhaDangKi, position);
            }
        });
    }

    public Nha findNhaById(String nhaID) {
        if (nhaList == null) {
            android.util.Log.d("NhaDangKiAdapter", "nhaList is null");
            return null;
        }

        android.util.Log.d("NhaDangKiAdapter", "Looking for nhaID: " + nhaID + " in list of size: " + nhaList.size());

        for (Nha nha : nhaList) {
            android.util.Log.d("NhaDangKiAdapter", "Checking nha with ID: " + nha.getID());
            if (nha.getID().equals(nhaID)) {
                android.util.Log.d("NhaDangKiAdapter", "Found matching nha: " + nha.getAddress());
                return nha;
            }
        }
        android.util.Log.d("NhaDangKiAdapter", "No matching nha found for ID: " + nhaID);
        return null;
    }

    public DichVu findDichVuById(String dichVuID) {
        if (dichVuList == null) {
            android.util.Log.d("NhaDangKiAdapter", "dichVuList is null");
            return null;
        }

        android.util.Log.d("NhaDangKiAdapter", "Looking for dichVuID: " + dichVuID + " in list of size: " + dichVuList.size());

        for (DichVu dichVu : dichVuList) {
            android.util.Log.d("NhaDangKiAdapter", "Checking dichVu with ID: " + dichVu.getId());
            if (dichVu.getId().equals(dichVuID)) {
                android.util.Log.d("NhaDangKiAdapter", "Found matching dichVu: " + dichVu.getTenDichVu());
                return dichVu;
            }
        }
        android.util.Log.d("NhaDangKiAdapter", "No matching dichVu found for ID: " + dichVuID);
        return null;
    }

    @Override
    public int getItemCount() {
        // Đảm bảo không bao giờ trả về null
        return (nhaDangKiList != null) ? nhaDangKiList.size() : 0;
    }

    /**
     * Thêm nhà và dịch vụ vào danh sách của adapter để hiển thị
     * @param nha Nhà đã chọn
     * @param dichVu Dịch vụ đã chọn
     */
    public void addNhaAndDichVu(Nha nha, DichVu dichVu) {
        // Kiểm tra xem nhà đã có trong danh sách chưa
        boolean nhaExists = false;
        for (Nha n : nhaList) {
            if (n.getID().equals(nha.getID())) {
                nhaExists = true;
                break;
            }
        }

        // Nếu chưa có, thêm vào danh sách
        if (!nhaExists) {
            nhaList.add(nha);
            android.util.Log.d("NhaDangKiAdapter", "Added nha to adapter: " + nha.getID() + ", " + nha.getAddress());
        }

        // Kiểm tra xem dịch vụ đã có trong danh sách chưa
        boolean dichVuExists = false;
        for (DichVu dv : dichVuList) {
            if (dv.getId().equals(dichVu.getId())) {
                dichVuExists = true;
                break;
            }
        }

        // Nếu chưa có, thêm vào danh sách
        if (!dichVuExists) {
            dichVuList.add(dichVu);
            android.util.Log.d("NhaDangKiAdapter", "Added dichVu to adapter: " + dichVu.getId() + ", " + dichVu.getTenDichVu());
        }
    }

    public class NhaDangKiViewHolder extends RecyclerView.ViewHolder {
        TextView txtDiaChi, txtDienTich, txtLoaiNha, txtDichVu;
        ImageButton btnDelete;

        public NhaDangKiViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiaChi = itemView.findViewById(R.id.txtDiaChi);
            txtDienTich = itemView.findViewById(R.id.txtDienTich);
            txtLoaiNha = itemView.findViewById(R.id.txtLoaiNha);
            txtDichVu = itemView.findViewById(R.id.txtDichVu);
            btnDelete = itemView.findViewById(R.id.btnDelete);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onNhaDangKiClickListener != null) {
                    NhaDangKi nhaDangKi = nhaDangKiList.get(position);
                    onNhaDangKiClickListener.onNhaDangKiClick(nhaDangKi);
                }
            });
        }
    }
}
