package com.yzz.system.dao;


import com.yzz.annotation.Query;
import lombok.Data;

import java.sql.Timestamp;
import java.util.List;

@Data
public class SignHistoryQueryCriteria {
    @Query(type = Query.Type.INNER_LIKE, propName = "courseName", joinName = "course")
    private String courseName;

    @Query(type = Query.Type.INNER_LIKE, propName = "courseCode", joinName = "course")
    private String courseCode;

    @Query(type = Query.Type.BETWEEN)
    private List<Timestamp> createTime;
}
