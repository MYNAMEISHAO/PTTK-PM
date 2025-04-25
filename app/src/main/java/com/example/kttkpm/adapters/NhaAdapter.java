package com.example.kttkpm.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kttkpm.OnNhaClickListener;
import com.example.kttkpm.R;
import com.example.kttkpm.models.Nha;

import java.util.List;

public class NhaAdapter extends RecyclerView.Adapter<NhaAdapter.NhaViewHolder> {
    private List<Nha> nhaList;
    public OnNhaClickListener onNhaClickListener;

    public NhaAdapter(List<Nha> nhaList, OnNhaClickListener onNhaClickListener) {
        this.nhaList = nhaList;
        this.onNhaClickListener = onNhaClickListener;
    }


    @NonNull
    @Override
    public NhaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_nha, parent, false);
        return new NhaViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NhaViewHolder holder, int position) {
        Nha nha = nhaList.get(position);
        holder.txtDiaChi.setText(nha.getAddress());
        holder.txtDienTich.setText(String.valueOf(nha.getArea()));
    }

    @Override
    public int getItemCount() {
        return nhaList.size();
    }

    public class NhaViewHolder extends RecyclerView.ViewHolder {
        TextView txtDiaChi, txtDienTich;

        public NhaViewHolder(@NonNull View itemView) {
            super(itemView);
            txtDiaChi = itemView.findViewById(R.id.txtAddress);
            txtDienTich = itemView.findViewById(R.id.txtArea);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if(position != RecyclerView.NO_POSITION && onNhaClickListener != null) {
                        Nha nha = nhaList.get(position);
                        onNhaClickListener.onNhaClick(nha);
                    }
                }
            });
        }
    }
}