package com.yzz.system.dao;

import java.io.Serializable;
import java.sql.Timestamp;

public class StudentCourseSignDto implements Serializable {

    private Long id;

    /**
     * 签到历史id
     */
    private SignHistoryDto signHistory;

    /**
     * 学生
     */
    private StudentSmallDto student;

    /**
     * 是否出勤
     */
    private Boolean attendance;

    /**
     * 是否补签
     */
    private Boolean replenish;

    /**
     * 签到时间
     */
    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SignHistoryDto getSignHistory() {
        return signHistory;
    }

    public void setSignHistory(SignHistoryDto signHistory) {
        this.signHistory = signHistory;
    }

    public StudentSmallDto getStudent() {
        return student;
    }

    public void setStudent(StudentSmallDto student) {
        this.student = student;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    public Boolean getReplenish() {
        return replenish;
    }

    public void setReplenish(Boolean replenish) {
        this.replenish = replenish;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
