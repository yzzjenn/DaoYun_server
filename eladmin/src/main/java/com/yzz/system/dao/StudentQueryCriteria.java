package com.yzz.system.dao;

import com.yzz.annotation.Query;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

public class StudentQueryCriteria {
    @Query(propName = "id", type = Query.Type.IN, joinName = "college")
    private Set<Long> collegeIds;

    @Query(blurry = "email,name,studentNumber")
    private String blurry;

    @Query
    private Boolean enabled;

    private Long collegeId;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;

    public Set<Long> getCollegeIds() {
        return collegeIds;
    }

    public void setCollegeIds(Set<Long> collegeIds) {
        this.collegeIds = collegeIds;
    }

    public String getBlurry() {
        return blurry;
    }

    public void setBlurry(String blurry) {
        this.blurry = blurry;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Long getCollegeId() {
        return collegeId;
    }

    public void setCollegeId(Long collegeId) {
        this.collegeId = collegeId;
    }

    public List<Timestamp> getCreateTime() {
        return createTime;
    }

    public void setCreateTime(List<Timestamp> createTime) {
        this.createTime = createTime;
    }
}
