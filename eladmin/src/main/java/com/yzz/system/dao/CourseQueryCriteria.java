package com.yzz.system.dao;

import com.yzz.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class CourseQueryCriteria {
    @Query(blurry = "courseName,courseCode")
    private String blurry;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
