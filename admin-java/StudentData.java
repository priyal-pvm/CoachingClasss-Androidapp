package com.admin.spzone;

public class StudentData {
    private String name,rollno,passtxt,email,phone,batch,std;

    public StudentData() {
    }

    public StudentData(String name, String rollno, String passtxt, String email, String phone, String batch, String std) {
        this.name = name;
        this.rollno = rollno;
        this.passtxt = passtxt;
        this.email = email;
        this.phone = phone;
        this.batch = batch;
        this.std=std;
    }

    public String getStd() { return std;   }

    public void setStd(String std) {this.std = std;}

    public String getName() { return name;  }

    public void setName(String name) {
        this.name = name;
    }

    public String getRollno() {
        return rollno;
    }

    public void setRollno(String rollno) {
        this.rollno = rollno;
    }

    public String getPasstxt() {
        return passtxt;
    }

    public void setPasstxt(String passtxt) {
        this.passtxt = passtxt;
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

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }
}
