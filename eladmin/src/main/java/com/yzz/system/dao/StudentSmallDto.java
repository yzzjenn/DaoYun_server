package com.yzz.system.dao;

import java.io.Serializable;

public class StudentSmallDto implements Serializable {
    private Long id;
    /**
     * 姓名
     */
    private String name;
    /**
     * 学号
     */
    private String studentNumber;

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
}