package com.yzz.system.dao;

import com.yzz.annotation.Query;

public class DictDetailQueryCriteria {
    @Query(type = Query.Type.INNER_LIKE)
    private String label;

    @Query(propName = "name", joinName = "dict_id")
    private String dictName;
}
