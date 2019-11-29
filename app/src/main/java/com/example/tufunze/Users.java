package com.example.tufunze;

public class Users {
    String Email, Image, Name, Phone;

    public Users() {
    }

    public Users(String email, String image, String name, String phone) {
        Email = email;
        Image = image;
        Name = name;
        Phone = phone;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }
}
