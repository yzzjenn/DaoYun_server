package com.yzz.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class MobileUserVo implements Serializable {

    private Long id;

    private String phone;

    private String name;

    private String sex;

    private String status;

    private String school;

    private String college;

    private String number;

    private String email;

    public MobileUserVo(Long id, String phone, String name, String sex, String status, String school, String college, String number, String email) {
        this.id = id;
        this.phone = phone;
        this.name = name;
        this.sex = sex;
        this.status = status;
        this.school = school;
        this.college = college;
        this.number = number;
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}