package com.yzz.system.pojo.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.yzz.system.dao.DeptSmallDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class WebCourseVo implements Serializable {
    private Long id;

    private String courseName;

    private String school;

    private DeptSmallDto college;

    public WebCourseVo(Long id, String courseName, String school, DeptSmallDto college) {
        this.id = id;
        this.courseName = courseName;
        this.school = school;
        this.college = college;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public DeptSmallDto getCollege() {
        return college;
    }

    public void setCollege(DeptSmallDto college) {
        this.college = college;
    }
}

