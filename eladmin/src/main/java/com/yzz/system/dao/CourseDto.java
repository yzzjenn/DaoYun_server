package com.yzz.system.dao;

import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


@Data
public class CourseDto implements Serializable {

    private Long id;

    private String className;

    private String courseName;

    private String courseCode;

    @NotNull
    private Boolean enabled;

    @NotNull
    private Boolean joinPermission;

    private String semester;

    /**
     * 选课人数
     */
    private Integer studentCount;

    private Integer count;

    /**
     * 授课老师姓名
     */
    private String teacherName;

    /**
     * 所属院校
     */
    private DeptSmallDto college;

    /**
     * 课程创建者名字
     */
    private String userName;

    /**
     * 签到发起次数
     */
    private Integer signCount;

    private List<SignHistorySmallDto> signHistory;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getJoinPermission() {
        return joinPermission;
    }

    public void setJoinPermission(Boolean joinPermission) {
        this.joinPermission = joinPermission;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Integer getStudentCount() {
        return studentCount;
    }

    public void setStudentCount(Integer studentCount) {
        this.studentCount = studentCount;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public DeptSmallDto getCollege() {
        return college;
    }

    public void setCollege(DeptSmallDto college) {
        this.college = college;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getSignCount() {
        return signCount;
    }

    public void setSignCount(Integer signCount) {
        this.signCount = signCount;
    }

    public List<SignHistorySmallDto> getSignHistory() {
        return signHistory;
    }

    public void setSignHistory(List<SignHistorySmallDto> signHistory) {
        this.signHistory = signHistory;
    }
}
