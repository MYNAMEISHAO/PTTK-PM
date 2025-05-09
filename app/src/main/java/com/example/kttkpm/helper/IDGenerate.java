package com.example.kttkpm.helper;

import com.example.kttkpm.models.HopDong;
import com.example.kttkpm.models.Khach;
import com.example.kttkpm.models.Nha;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class IDGenerate {
    public String generateKhachID(List<Khach> khachList) {
        if (khachList == null || khachList.isEmpty()) {
            return "K001"; // Danh sách rỗng, ID đầu tiên là K001
        }

        List<Integer> numbers = new ArrayList<>();
        for (Khach khach : khachList) {
            String id = khach.getId();
            if (id != null && id.startsWith("K")) {
                try {
                    int number = Integer.parseInt(id.substring(1));
                    numbers.add(number);
                } catch (NumberFormatException e) {
                    // Xử lý khi id không hợp lệ (không phải số)
                    // Bỏ qua, không thêm vào danh sách
                }
            }
        }

        if (numbers.isEmpty()){
            return "K001";//danh sách chỉ có các id ko hợp lệ, id đầu tiên là K001
        }
        // Sắp xếp các số theo thứ tự tăng dần
        Collections.sort(numbers);

        // Tìm khoảng trống
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i + 1) - numbers.get(i) > 1) {
                // Tìm thấy khoảng trống, trả về ID với số nằm trong khoảng trống đó
                return String.format("K%03d", numbers.get(i) + 1);
            }
        }

        // Không tìm thấy khoảng trống, trả về ID mới là số lớn nhất + 1
        return String.format("K%03d", numbers.get(numbers.size() - 1) + 1);
    }

    public String generateNhaID(List<Nha> nhaList) {
        if (nhaList == null || nhaList.isEmpty()) {
            return "N001"; // Danh sách rỗng, ID đầu tiên là N001
        }

        List<Integer> numbers = new ArrayList<>();
        for (Nha nha : nhaList) {
            String id = nha.getID();
            if (id != null && id.startsWith("N")) {
                try {
                    int number = Integer.parseInt(id.substring(1));
                    numbers.add(number);
                } catch (NumberFormatException e) {
                    // Xử lý khi id không hợp lệ (không phải số)
                    // Bỏ qua, không thêm vào danh sách
                }
            }
        }

        if (numbers.isEmpty()){
            return "N001";//danh sách chỉ có các id ko hợp lệ, id đầu tiên là N001
        }
        // Sắp xếp các số theo thứ tự tăng dần
        Collections.sort(numbers);

        // Tìm khoảng trống
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i + 1) - numbers.get(i) > 1) {
                // Tìm thấy khoảng trống, trả về ID với số nằm trong khoảng trống đó
                return String.format("N%03d", numbers.get(i) + 1);
            }
        }

        // Không tìm thấy khoảng trống, trả về ID mới là số lớn nhất + 1
        return String.format("N%03d", numbers.get(numbers.size() - 1) + 1);
    }

    public String generateHopDongID(List<HopDong> hopDongList) {
        if (hopDongList == null || hopDongList.isEmpty()) {
            return "HD001"; // Danh sách rỗng, ID đầu tiên là HD001
        }

        List<Integer> numbers = new ArrayList<>();
        for (HopDong hopDong : hopDongList) {
            String id = hopDong.getId();
            if (id != null && id.startsWith("HD")) {
                try {
                    int number = Integer.parseInt(id.substring(2));
                    numbers.add(number);
                } catch (NumberFormatException e) {
                    // Xử lý khi id không hợp lệ (không phải số)
                    // Bỏ qua, không thêm vào danh sách
                }
            }
        }

        if (numbers.isEmpty()){
            return "HD001";//danh sách chỉ có các id ko hợp lệ, id đầu tiên là HD001
        }
        // Sắp xếp các số theo thứ tự tăng dần
        Collections.sort(numbers);

        // Tìm khoảng trống
        for (int i = 0; i < numbers.size() - 1; i++) {
            if (numbers.get(i + 1) - numbers.get(i) > 1) {
                // Tìm thấy khoảng trống, trả về ID với số nằm trong khoảng trống đó
                return String.format("HD%03d", numbers.get(i) + 1);
            }
        }

        // Không tìm thấy khoảng trống, trả về ID mới là số lớn nhất + 1
        return String.format("HD%03d", numbers.get(numbers.size() - 1) + 1);
    }


}