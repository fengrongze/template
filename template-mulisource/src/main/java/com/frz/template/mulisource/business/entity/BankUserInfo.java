package com.frz.template.mulisource.business.entity;

public class BankUserInfo {
    private Integer id;

    private String userName;

    private String userClassName;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserClassName() {
        return userClassName;
    }

    public void setUserClassName(String userClassName) {
        this.userClassName = userClassName == null ? null : userClassName.trim();
    }
}