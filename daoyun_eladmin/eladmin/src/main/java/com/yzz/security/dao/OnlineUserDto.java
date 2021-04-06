package com.yzz.security.dao;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class OnlineUserDto {
    /**
     * 用户名
     */
    private String userName;

    /**
     * 昵称
     */
    private String nickName;

    /**
     * 岗位
     */
    private String dept;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * IP
     */
    private String ip;

    /**
     * 地址
     */
    private String address;

    /**
     * token
     */
    private String key;

    /**
     * 登录时间
     */
    private Date loginTime;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getBrowser() {
        return browser;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public OnlineUserDto(String userName, String nickName, String dept, String browser, String ip, String address, String key, Date loginTime) {
        this.userName = userName;
        this.nickName = nickName;
        this.dept = dept;
        this.browser = browser;
        this.ip = ip;
        this.address = address;
        this.key = key;
        this.loginTime = loginTime;
    }
}
