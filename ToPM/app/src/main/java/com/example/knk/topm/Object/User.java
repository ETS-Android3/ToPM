package com.example.knk.topm.Object;

import java.io.Serializable;

public class User implements Serializable {

    public String name;
    public String pw;
    public String id;
    public String birth;
    public boolean admin;

    public User(String id, String pw, String name, String birth, boolean admin){
        this.id=id;
        this.pw=pw;
        this.name = name;
        this.birth=birth;
        this.admin = admin;
    }

    public User(){}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPw() {
        return pw;
    }

    public void setPw(String pw) {
        this.pw = pw;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
