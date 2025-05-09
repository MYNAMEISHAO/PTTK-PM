package com.example.kttkpm;

import com.example.kttkpm.models.NhaDangKi;

public interface OnNhaDangKiClickListener {
    void onNhaDangKiClick(NhaDangKi nhaDangKi);
    void onDeleteNhaClick(NhaDangKi nha, int position);
}
