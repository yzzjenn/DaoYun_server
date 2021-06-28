package com.yzz.system.service.Impl;

import com.yzz.system.dao.CourseDto;
import com.yzz.system.dao.CourseQueryCriteria;
import com.yzz.system.dao.CouserSmallDto;
import com.yzz.system.dao.UserDto;
import com.yzz.system.mapper.CourseMapper;
import com.yzz.system.mapper.CourseSmallMapper;
import com.yzz.system.mapper.UserMapper;
import com.yzz.system.pojo.Course;
import com.yzz.system.repository.CourseRepository;
import com.yzz.system.repository.CourseStudentRepository;
import com.yzz.system.service.CourseService;
import com.yzz.system.service.RoleService;
import com.yzz.system.service.UserService;
import com.yzz.util.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "course")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class CourseServiceImpl implements CourseService {

    private final CourseRepository courseRepository;
    private final CourseMapper courseMapper;
    private final UserMapper userMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final CourseSmallMapper courseSmallMapper;
    private final CourseStudentRepository courseStudentRepository;


    @Override
    public Map<String, Object> queryAll(CourseQueryCriteria criteria, Pageable pageable) {
        Page<Course> page = courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        List<Course> courses = page.getContent();
        courses.forEach(course -> {
            Integer count = courseStudentRepository.countByIdCourseId(course.getId());
            System.out.println(count);
            course.setStudentCount(count);
        });
        return PageUtil.toPage(page.map(courseMapper::toDto));
    }

    @Override
    @Cacheable
    public List<CourseDto> queryAll(CourseQueryCriteria criteria) {
        return courseMapper.toDto(courseRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public CourseDto findById(Long id) {
        Course course = courseRepository.findById(id).orElseGet(Course::new);
        ValidationUtil.isNull(course.getId(), "Course", "id", id);
        return courseMapper.toDto(course);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public CourseDto create(Course resources) {
        /* 获得创建人信息 */
        UserDto user = userService.findByName(SecurityUtils.getCurrentUsername());
        if (user != null) {
            resources.setCreateUser(userMapper.toEntity(user));
        }
        /* 随机生成课程码 */
        String courseCode = StringUtils.randomNumStr(7);
        List<String> codes = courseRepository.findCourseCodes();
        while (codes.contains(courseCode)) {
            courseCode = StringUtils.randomNumStr(7);
        }
        resources.setCourseCode(courseCode);
        return courseMapper.toDto(courseRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Course resources) {
        Course course = courseRepository.findById(resources.getId()).orElseGet(Course::new);
        ValidationUtil.isNull(course.getId(), "Course", "id", resources.getId());
        course.copy(resources);
        courseRepository.save(course);
    }


    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            courseRepository.deleteById(id);
        }
    }

    @Override
//    @Cacheable(key = "'loadCourseById:'+#p0")
    public Course findCourseById(Long id) {
        Course course = courseRepository.findById(id).orElseGet(Course::new);
        return course;
    }


    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public CourseDto createCourse(Course resources) {
        /*随机生成课程码*/
        String courseCode = StringUtils.randomNumStr(7);
        List<String> codes = courseRepository.findCourseCodes();
        while (codes.contains(courseCode)) {
            courseCode = StringUtils.randomNumStr(7);
        }
        resources.setCourseCode(courseCode);
        return courseMapper.toDto(courseRepository.save(resources));
    }

    @Override
    public CourseDto findByCode(String code) {
        Course course = courseRepository.findByCourseCode(code);
        ValidationUtil.isNull(course.getCourseCode(), "Course", "courseCode", code);
        return courseMapper.toDto(course);
    }

    @Override
    public Boolean courseBelong(String code, String phone) {
        Course course = courseRepository.findByCourseCode(code);
        ValidationUtil.isNull(course.getCourseCode(), "Course", "courseCode", code);
        if (course.getCreateUser().getPhone().equals(phone)) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public Course findEntityByCode(String code) {
        Course course = courseRepository.findByCourseCode(code);
        ValidationUtil.isNull(course, "course", "courseCode", code);
        return course;
    }

    @Override
    public List<CouserSmallDto> findByUserId(Long id) {
        List<Course> course = courseRepository.findByUserId(id);
        return courseSmallMapper.toDto(course);
    }

    @Override
    public List<CourseDto> findAll() {
        List<Course> course = courseRepository.findAll();
        return courseMapper.toDto(course);
    }

}
