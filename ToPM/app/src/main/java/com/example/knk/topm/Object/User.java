package com.example.knk.topm.Object;

public class User {

    public String name;
    public String pw;
    public String id;
    public String birth;
    // public boolean isStaff; // 이거 넣고 가입하는 코드 등등 손봐야 합니다아 ^^

    public User(String id, String pw, String name, String birth){
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
