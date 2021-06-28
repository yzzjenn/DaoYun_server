package com.yzz.system.dao;

import java.sql.Timestamp;

public class SignHistorySmallDto {
    /**
     * id
     */
    private Long id;

    /**
     * 出勤人数
     */
    private Integer attendance;

    /**
     * 缺勤人数
     */
    private Integer absence;

    /**
     * 发起时间
     */
    private Timestamp createTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getAbsence() {
        return absence;
    }

    public void setAbsence(Integer absence) {
        this.absence = absence;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }
}
