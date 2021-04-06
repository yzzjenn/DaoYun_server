package com.yzz.system.service;



import com.yzz.system.dao.UserDto;
import com.yzz.system.pojo.Dept;

import java.util.List;

public interface DataService {
    /**
     * 获取数据权限
     *
     * @param user /
     * @return /
     */
    List<Long> getDeptIds(UserDto user);

    /**
     * 获取子部门
     *
     * @param deptList /
     * @return /
     */
    List<Long> getDeptChildren(List<Dept> deptList);
}
