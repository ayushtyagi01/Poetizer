package com.example.shareit;

public class Users {
    public String email,user,password,phone,uid,image_uri;
    public Users(){

    }
    public Users(String email, String user, String password, String phone,String uid,String image_uri) {
        this.email=email;
        this.password=password;
        this.phone=phone;
        this.user=user;
        this.uid=uid;
        this.image_uri=image_uri;
    }

    public String getImage_uri() {
        return image_uri;
    }

    public String getUser() {
        return user;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String getPhone() {
        return phone;
    }

    public String getUid() {
        return uid;
    }
}
