package com.example.demo.model;
import javax.persistence.Column;
import javax.persistence.Id;

public class UserInfo {

    /*主键*/
    @Id
    private String id;

    /*用户名*/
    @Column(name = "user_name")
    private String user_name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
}
