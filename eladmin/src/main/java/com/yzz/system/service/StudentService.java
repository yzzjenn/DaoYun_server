package com.yzz.system.service;

import com.yzz.system.dao.CouserSmallDto;
import com.yzz.system.dao.StudentDto;
import com.yzz.system.dao.StudentQueryCriteria;
import com.yzz.system.pojo.Student;
import org.springframework.data.domain.Pageable;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface StudentService {
    /**
     * 查询数据分页
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return Map<String, Object>
     */
    Map<String, Object> queryAll(StudentQueryCriteria criteria, Pageable pageable);

    /**
     * 查询所有数据不分页
     *
     * @param criteria 条件参数
     * @return List<StudentDto>
     */
    List<StudentDto> queryAll(StudentQueryCriteria criteria);

    /**
     * 根据ID查询
     *
     * @param id ID
     * @return StudentDto
     */
    StudentDto findById(Long id);

    /**
     * 创建
     *
     * @param resources /
     * @return StudentDto
     */
    StudentDto create(Student resources);

    /**
     * 编辑
     *
     * @param resources /
     */
    void update(Student resources);

    /**
     * 多选删除
     *
     * @param ids /
     */
    void deleteAll(Long[] ids);

    /**
     * 根据名字查询学生
     *
     * @param name /
     */
    StudentDto findByName(String name);


    /**
     * 导出数据
     *
     * @param all      待导出的数据
     * @param response /
     * @throws IOException /
     */
//    void download(List<StudentDto> all, HttpServletResponse response) throws IOException;

    /**
     * 根据邮箱email或手机号phone查询学生
     *
     * @param count /
     */
    Student findByCount(String count);

    void updatePass(String count, String pass);

    List<CouserSmallDto> findJoinCourse(Long id);
}
