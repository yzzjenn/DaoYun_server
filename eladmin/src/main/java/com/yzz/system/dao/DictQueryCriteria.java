package com.yzz.system.dao;

import com.yzz.annotation.Query;

public class DictQueryCriteria {
    @Query(blurry = "name,description")
    private String blurry;
}
