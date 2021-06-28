package com.yzz.system.dao;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.io.Serializable;
import java.sql.Timestamp;

public class StudentDto implements Serializable {

        /**
         * 学生id
         */
        private Long id;

        /**
         * 姓名
         */
        private String name;

        /**
         * 学号
         */
        private String studentNumber;

        @JsonIgnore
        private String password;

        /**
         * 创建日期
         */
        private Timestamp createTime;

        /**
         * 学院
         */
        private DeptSmallDto college;

        /**
         * 邮箱
         */
        private String email;

        /**
         * 状态：1启用、0禁用
         */
        private Boolean enabled;

        /**
         * 手机号码
         */
        private String phone;

        /**
         * 最后修改密码的日期
         */
        private Timestamp lastPasswordResetTime;

        /**
         * 性别
         */
        private String sex;

        /**
         * 经验值
         */
        private Integer experience;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public DeptSmallDto getCollege() {
        return college;
    }

    public void setCollege(DeptSmallDto college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getLastPasswordResetTime() {
        return lastPasswordResetTime;
    }

    public void setLastPasswordResetTime(Timestamp lastPasswordResetTime) {
        this.lastPasswordResetTime = lastPasswordResetTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}
