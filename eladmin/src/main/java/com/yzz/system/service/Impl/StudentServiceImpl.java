package com.yzz.system.service.Impl;

import com.yzz.exception.EntityNotFoundException;
import com.yzz.system.dao.CouserSmallDto;
import com.yzz.system.dao.StudentDto;
import com.yzz.system.dao.StudentQueryCriteria;
import com.yzz.system.mapper.CourseSmallMapper;
import com.yzz.system.mapper.StudentMapper;
import com.yzz.system.pojo.Course;
import com.yzz.system.pojo.Student;
import com.yzz.system.repository.CourseStudentRepository;
import com.yzz.system.repository.StudentRepository;
import com.yzz.system.service.StudentService;
import com.yzz.util.PageUtil;
import com.yzz.util.QueryHelp;
import com.yzz.util.ValidationUtil;
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
import java.util.*;


@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "student")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;
    private final CourseStudentRepository courseStudentRepository;
    private final CourseSmallMapper courseSmallMapper;


    @Override
    @Cacheable
    public Map<String, Object> queryAll(StudentQueryCriteria criteria, Pageable pageable) {
        Page<Student> page = studentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(studentMapper::toDto));
    }

    @Override
    @Cacheable
    public List<StudentDto> queryAll(StudentQueryCriteria criteria) {
        return studentMapper.toDto(studentRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public StudentDto findById(Long id) {
        Student student = studentRepository.findById(id).orElseGet(Student::new);
        ValidationUtil.isNull(student.getId(), "Student", "id", id);
        return studentMapper.toDto(student);
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public StudentDto create(Student resources) {
        return studentMapper.toDto(studentRepository.save(resources));
    }

    @Override
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(Student resources) {
        Student student = studentRepository.findById(resources.getId()).orElseGet(Student::new);
        ValidationUtil.isNull(student.getId(), "Student", "id", resources.getId());
        student.copy(resources);
        studentRepository.save(student);
    }

    @Override
    @CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            studentRepository.deleteById(id);
        }
    }

    @Override
    public StudentDto findByName(String name) { // 根据邮箱或电话查询学生用户 name只是一个传入参数
        Student student;
        if (ValidationUtil.isEmail(name)) {
            student = studentRepository.findByEmail(name);
        } else {
            student = studentRepository.findByPhone(name);
        }
        if (student == null) {
            throw new EntityNotFoundException(Student.class, "name", name);
        } else {
            return studentMapper.toDto(student);
        }
    }

//    @Override
//    public void download(List<StudentDto> all, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (StudentDto student : all) {
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("姓名", student.getName());
//            map.put("学号", student.getStudentNumber());
//            map.put("创建日期", student.getCreateTime());
//            map.put("学院", student.getCollege().getName());
//            map.put("邮箱", student.getEmail());
//            map.put("状态", student.getEnabled());
//            map.put("手机号码", student.getPhone());
//            map.put("最后修改密码的日期", student.getLastPasswordResetTime());
//            map.put("性别", student.getSex());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
//    }


    @Override
    public Student findByCount(String count) {
        Student student;
        if (ValidationUtil.isEmail(count)) {
            student = studentRepository.findByEmail(count);
        } else {
            student = studentRepository.findByPhone(count);
        }
        if (student == null) {
            throw new EntityNotFoundException(Student.class, "count", count);
        } else {
            return student;
        }
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String count, String pass) {
        studentRepository.updatePass(count, pass, new Date());
    }


    @Override
    public List<CouserSmallDto> findJoinCourse(Long id) {
        Student student = studentRepository.findById(id).orElseGet(Student::new);
        ValidationUtil.isNull(student.getId(), "Student", "id", id);
        List<Course> coursess = new ArrayList<>(student.getCourses());
        return courseSmallMapper.toDto(coursess);
    }
}
