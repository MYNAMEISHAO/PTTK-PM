package com.example.kttkpm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.OnKhachClickListener;
import com.example.kttkpm.activity.QuanLyKhachActivity;
import com.example.kttkpm.R;
import com.example.kttkpm.models.Khach;

import java.util.List;

public class KhachAdapter extends RecyclerView.Adapter<KhachAdapter.KhachViewHolder> {
    private List<Khach> khachList;
    private OnKhachClickListener onKhachClickListener;

    public KhachAdapter(List<Khach> khachList, OnKhachClickListener onKhachClickListener) {
        this.khachList = khachList;
        this.onKhachClickListener = onKhachClickListener;
    }

    @NonNull
    @Override
    public KhachViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_khach, parent, false);
        return new KhachViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull KhachViewHolder holder, int position) {
        Khach k = khachList.get(position);
        holder.txtTen.setText(k.getName());
        holder.txtSdt.setText(k.getPhone());
        holder.txtEmail.setText(k.getEmail());
    }

    @Override
    public int getItemCount() {
        return khachList.size();
    }

    public class KhachViewHolder extends RecyclerView.ViewHolder {
        TextView txtTen, txtSdt, txtEmail;

        public KhachViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTen = itemView.findViewById(R.id.txtTen);
            txtSdt = itemView.findViewById(R.id.txtSdt);
            txtEmail = itemView.findViewById(R.id.txtEmail);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && onKhachClickListener != null) {
                        Khach khach = khachList.get(position);
                        onKhachClickListener.onKhachClick(khach);
                    }
                }
            });
        }
    }
}