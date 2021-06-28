package com.yzz.system.service;

import com.yzz.system.dao.DictDetailDto;
import com.yzz.system.dao.DictDto;
import com.yzz.system.dao.DictQueryCriteria;
import com.yzz.system.pojo.Dict;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface DictService {
    /**
     * 分页查询
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Map<String, Object> queryAll(DictQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部数据
     *
     * @param dict /
     * @return /
     */
    List<DictDto> queryAll(DictQueryCriteria dict);

    /**
     * 创建
     *
     * @param resources /
     * @return /
     */
    void create(Dict resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Dict resources);

    /**
     * 删除
     *
     * @param ids /
     */
    void delete(Set<Long> ids);
    /**
     * 根据字典名称搜索字典
     *
     * @param name 字典名称
     * @return /
     */
    List<DictDto> getDictByName(String name);

}
