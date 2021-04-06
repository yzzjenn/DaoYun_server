package com.yzz.system.service.Impl;

import com.yzz.consts.DataScopeEnum;
import com.yzz.system.dao.RoleSmallDto;
import com.yzz.system.dao.UserDto;
import com.yzz.system.pojo.Dept;
import com.yzz.system.service.DataService;
import com.yzz.system.service.DeptService;
import com.yzz.system.service.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "data")
public class DataServiceImpl implements DataService {
    private final RoleService roleService;
    private final DeptService deptService;



    /**
     * 用户角色改变时需清理缓存
     *
     * @param user /
     * @return /
     */
    @Override
    @Cacheable(key = "'user:' + #p0.id")
    public List<Long> getDeptIds(UserDto user) {
        // 用于存储部门id
        Set<Long> deptIds = new HashSet<>();
        // 查询用户角色
        List<RoleSmallDto> roleSet = roleService.findByUsersId(user.getId());
        // 获取对应的部门ID
        for (RoleSmallDto role : roleSet) {
            DataScopeEnum dataScopeEnum = DataScopeEnum.find(role.getDataScope());
            switch (Objects.requireNonNull(dataScopeEnum)) {
                case THIS_LEVEL:
                    deptIds.add(user.getDept().getId());
                    break;
                case CUSTOMIZE:
                    deptIds.addAll(getCustomize(deptIds, role));
                    break;
                default:
                    break;
            }
        }
        return new ArrayList<>(deptIds);
    }

    /**
     * 获取自定义的数据权限
     *
     * @param deptIds 部门ID
     * @param role    角色
     * @return 数据权限ID
     */
    public Set<Long> getCustomize(Set<Long> deptIds, RoleSmallDto role) {
        Set<Dept> depts = deptService.findByRoleId(role.getId());
        for (Dept dept : depts) {
            deptIds.add(dept.getId());
            List<Dept> deptChildren = deptService.findByPid(dept.getId());
            if (deptChildren != null && deptChildren.size() != 0) {
                deptIds.addAll(deptService.getDeptChildren(dept.getId(), deptChildren));
            }
        }
        return deptIds;
    }



    public List<Long> getDeptChildren(List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept != null && dept.getEnabled()) {
                        List<Dept> depts = deptService.findByPid(dept.getId());
                        if (depts.size() != 0) {
                            list.addAll(getDeptChildren(depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }
}
