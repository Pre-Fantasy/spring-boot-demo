package com.example.demo.model;

import javax.persistence.*;
import java.util.Set;

@Table(name = "user_info")
public class UserInfo {
    @Id
    private String id;

    @Column(name = "user_name")
    private String userName;


    private String password;

    /*加密盐值*/
    private String salt;

    /*用户所有角色值， 用于shiro做角色权限的判断*/
    @Transient
    private Set<String> roles;

    /*用户所有权限值，用于shiro做资源权限的判断*/
    @Transient
    private Set<String> perms;


    /**
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(String id) {
        this.id = id == null ? null : id.trim();
    }

    /**
     * @return user_name
     */
    public String getUserName() {
        return userName;
    }

    /**
     * @param userName
     */
    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    /**
     * @return password
     */
    public String getPassword() {
        return password;
    }

    /**
     * @param password
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * @return salt
     */
    public String getSalt() {
        return salt;
    }

    /**
     * @param salt
     */
    public void setSalt(String salt) {
        this.salt = salt == null ? null : salt.trim();
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getPerms() {
        return perms;
    }

    public void setPerms(Set<String> perms) {
        this.perms = perms;
    }
}