package com.example.kttkpm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.OnDichVuClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.models.DichVu;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class DichVuAdapter extends RecyclerView.Adapter<DichVuAdapter.DichVuViewHolder> {
    private List<DichVu> dichVuList;
    private List<DichVu> selectedDichVuList;
    private OnDichVuClickListener onDichVuClickListener;
    private NumberFormat currencyFormat;

    public DichVuAdapter(List<DichVu> dichVuList, OnDichVuClickListener onDichVuClickListener) {
        this.dichVuList = dichVuList;
        this.selectedDichVuList = new ArrayList<>();
        this.onDichVuClickListener = onDichVuClickListener;
        this.currencyFormat = NumberFormat.getCurrencyInstance(new Locale("vi", "VN"));
    }

    @NonNull
    @Override
    public DichVuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_dichvu, parent, false);
        return new DichVuViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DichVuViewHolder holder, int position) {
        DichVu dichVu = dichVuList.get(position);
        holder.txtTenDichVu.setText(dichVu.getTenDichVu());
        holder.txtMoTa.setText(dichVu.getMoTa());
        holder.txtGia.setText("Giá: " + currencyFormat.format(dichVu.getGia()));
    }

    @Override
    public int getItemCount() {
        return dichVuList.size();
    }

    public class DichVuViewHolder extends RecyclerView.ViewHolder {
        TextView txtTenDichVu, txtMoTa, txtGia;

        public DichVuViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTenDichVu = itemView.findViewById(R.id.txtTenDichVu);
            txtMoTa = itemView.findViewById(R.id.txtMoTa);
            txtGia = itemView.findViewById(R.id.txtGia);

            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    DichVu dichVu = dichVuList.get(position);
                    onDichVuClickListener.onDichVuClick(dichVu);
                }
            });

        }
    }
}
