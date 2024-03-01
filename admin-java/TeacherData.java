package com.admin.spzone;

public class TeacherData {
    String name,email,phone,image,key,pass;

    public TeacherData() {
    }

    public TeacherData(String name, String email, String phone, String image,String pass) {
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.image = image;
        this.pass = pass;
       // this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPass() {return pass;}

    public void setPass(String pass) {this.pass = pass;}
}
