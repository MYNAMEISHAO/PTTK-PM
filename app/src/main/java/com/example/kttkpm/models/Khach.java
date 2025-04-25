package com.example.kttkpm.models;

public class Khach {
    private String ID, Name, Email, Phone;

    public Khach() {}
    public Khach(String id, String name, String email, String phone) {
        this.ID= id;
        this.Name = name;
        this.Email = email;
        this.Phone = phone;
    }

    public String getId() { return ID; }
    public String getName() { return Name; }
    public String getEmail() { return Email; }
    public String getPhone() { return Phone; }

    public void setEmail(String email) {
        this.Email = email;
    }

    public void setId(String id) {
        this.ID = id;
    }

    public void setName(String name) {
        this.Name = name;
    }

    public void setPhone(String phone) {
        this.Phone = phone;
    }
}
