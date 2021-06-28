package com.yzz.system.service;

import com.yzz.system.dao.CourseDto;
import com.yzz.system.dao.CourseQueryCriteria;
import com.yzz.system.dao.CouserSmallDto;
import com.yzz.system.pojo.Course;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface CourseService {
    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(CourseQueryCriteria criteria, Pageable pageable);


    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<CourseDto>
     */
    List<CourseDto> queryAll(CourseQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return CourseDto
     */
    CourseDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return CourseDto
     */
    CourseDto create(Course resources);

    Course findCourseById(Long id);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Course resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);


    CourseDto createCourse(Course resource);

    Course findEntityByCode(String code);

    CourseDto findByCode(String code);

    Boolean courseBelong(String code, String phone);

    List<CouserSmallDto> findByUserId(Long id);

    List<CourseDto> findAll();
}
