package com.yzz.system.pojo.vo;

import javax.validation.constraints.NotBlank;

public class MobileAuthVo {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

    @Override
    public String toString() {
        return "{username=" + username + ", password= ******}";
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
}