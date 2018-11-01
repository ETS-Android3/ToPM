package com.example.knk.topm.Object;

public class User {

    public String name;
    public String pw;
    public String id;
    public String birth;

    User(String id, String pw, String name, String birth){
        this.id=id;
        this.pw=pw;
        this.name = name;
        this.birth=birth;
    }

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
}
