package com.yzz.system.dao;

import com.yzz.annotation.Query;

public class SysValQueryCriteria {
    @Query(blurry = "name,remark")
    private String blurry;
}
