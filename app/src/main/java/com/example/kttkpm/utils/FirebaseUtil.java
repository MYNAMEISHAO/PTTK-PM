package com.example.kttkpm.utils;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseUtil {
    private static final FirebaseDatabase database = FirebaseDatabase.getInstance();
    private static final String KHACH_PATH = "Khach";
    private static final String NHA_PATH = "Nha";

    public static DatabaseReference getKhachRef() {
        return database.getReference(KHACH_PATH);
    }

    public static DatabaseReference getNhaRef() {
        return database.getReference(NHA_PATH);
    }
}