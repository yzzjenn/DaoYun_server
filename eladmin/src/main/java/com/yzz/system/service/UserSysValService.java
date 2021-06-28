package com.yzz.system.service;

import com.yzz.system.dao.SysValDto;
import com.yzz.system.dao.SysValQueryCriteria;
import com.yzz.system.dao.UserSysValDto;
import com.yzz.system.pojo.SysVal;
import com.yzz.system.pojo.UserSysVal;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface UserSysValService {

    /**
     * 查询数据分页
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String,Object>
     */
//    Map<String,Object> queryAll(UserSysValQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     * @param criteria 条件参数
     * @return List<UserSysValDto>
     */
     List<SysValDto> queryAll(SysValQueryCriteria criteria);

    Map<String, Object> findByUser(Long userId);


    /**
     * 根据ID查询
     * @param id ID
     * @return UserSysValDto
     */
    UserSysValDto findById(Long id);

    /**
     * 创建
     * @param resources /
     * @return UserSysValDto
     */
    void create(SysVal resources);

    /**
     * 编辑
     * @param resources /
     */
    void update(SysVal resources);

    /**
     * 多选删除
     * @param id /
     */
    void delete(Long id);

    /**
     * 导出数据
     * @param all 待导出的数据
     * @param response /
     * @throws IOException /
     */
//    void download(List<UserSysValDto> all, HttpServletResponse response) throws IOException;
}
