package com.example.musicplayer;

public class UserBean {

    public UserBean(String userNam, String userPwd) {
        this.userNam = userNam;
        this.userPwd = userPwd;
    }

    public String getUserNam() {
        return userNam;
    }

    public void setUserNam(String userNam) {
        this.userNam = userNam;
    }

    public String getUserPwd() {
        return userPwd;
    }

    public void setUserPwd(String userPwd) {
        this.userPwd = userPwd;
    }

    private String userNam,userPwd;
}
