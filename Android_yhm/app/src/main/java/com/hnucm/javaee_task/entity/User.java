package com.hnucm.javaee_task.entity;

public class User {

        private int id;
        private String username;
        private String password;
        private String nickname;
        private String telephone;

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }
    public void setNickname(String nickname) { this.nickname = nickname; }

    public String getTelephone() {
        return telephone;
    }
    public void setTelephone(String telephone) { this.telephone = telephone; }



}

