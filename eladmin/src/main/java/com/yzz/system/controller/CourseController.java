package com.yzz.system.controller;


import com.yzz.annotation.AnonymousAccess;
import com.yzz.exception.BadRequestException;
import com.yzz.system.dao.CourseDto;
import com.yzz.system.dao.CourseQueryCriteria;
import com.yzz.system.dao.DeptDto;
import com.yzz.system.dao.StudentDto;
import com.yzz.system.mapper.StudentMapper;
import com.yzz.system.pojo.Course;
import com.yzz.system.pojo.CourseStudent;
import com.yzz.system.pojo.SignHistoryPrimaryKey;
import com.yzz.system.pojo.Student;
import com.yzz.system.pojo.vo.WebCourseVo;
import com.yzz.system.repository.CourseStudentRepository;
import com.yzz.system.service.CourseService;
import com.yzz.system.service.DeptService;
import com.yzz.system.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Api(tags = "班课管理")
@RestController
@RequestMapping("/api/course")
@RequiredArgsConstructor
public class CourseController {
    private final CourseService courseService;
    private final UserService userService;
    private final StudentMapper studentMapper;
    private final CourseStudentRepository courseStudentRepository;
    private final DeptService deptService;


    @ApiOperation("查询部分课程信息")
    @GetMapping(value = "/partInfo")
    @AnonymousAccess
//    @PreAuthorize("@el.check('course:list')")
    public ResponseEntity<Object> getSmallCourse() {
        String schoolName;
        List<CourseDto> courseDto = courseService.findAll();
        List<WebCourseVo> webCourseVos = new ArrayList<>();
        for (int i = 0; i < courseDto.size(); i++) {
            DeptDto deptDto = deptService.findById(courseDto.get(i).getCollege().getId());

            if (deptDto.getPid() != null) {
                DeptDto pDeptDto = deptService.findById(deptDto.getPid());
                schoolName = pDeptDto.getName();
            } else schoolName = courseDto.get(i).getCollege().getName();

            WebCourseVo webCourseVo1 = new WebCourseVo(courseDto.get(i).getId(), courseDto.get(i).getCourseName(), schoolName, courseDto.get(i).getCollege());
            webCourseVos.add(webCourseVo1);
        }
        return new ResponseEntity<>(webCourseVos, HttpStatus.OK);
//        return new ResponseEntity<>(courseService.queryAll(criteria,pageable),HttpStatus.OK);
    }

    @ApiOperation("查询课程信息")
    @GetMapping
    @PreAuthorize("@el.check('course:list')")
    public ResponseEntity<Object> getCourses(CourseQueryCriteria criteria, Pageable pageable) {
        return new ResponseEntity<>(courseService.queryAll(criteria, pageable), HttpStatus.OK);
    }


    @ApiOperation("新增课程信息")
    @PostMapping
    @PreAuthorize("@el.check('course:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Course resources) {
        return new ResponseEntity<>(courseService.create(resources), HttpStatus.CREATED);
    }


    @ApiOperation("修改课程信息")
    @PutMapping
    @PreAuthorize("@el.check('course:edit')")
    public ResponseEntity<Object> update(@Validated @RequestBody Course resources) {
        courseService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation("删除课程")
    @DeleteMapping
    @PreAuthorize("@el.check('course:del')")
    public ResponseEntity<Object> deleteAll(@RequestBody Long[] ids) {
        courseService.deleteAll(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @ApiOperation("按课程编码查询选课学生")
    @GetMapping(value = "/student")
    @PreAuthorize("@el.check('course:list')")
    public ResponseEntity<Object> courseStudents(Long id) {
        Set<Student> students = courseService.findCourseById(id).getStudents();
        Set<StudentDto> studentDtos = new HashSet<>();
        if (students != null && students.size() != 0) {
            for (Student student : students) {
                SignHistoryPrimaryKey pk = new SignHistoryPrimaryKey(id, student.getId());
                CourseStudent cs = courseStudentRepository.findById(pk).orElseGet(CourseStudent::new);
                if (cs != null) {
                    Integer experience = cs.getExperience();
                    studentDtos.add(studentMapper.toDto(student, experience));
                } else {
                    throw new BadRequestException("获取学生经验值失败");
                }
            }
        }
        return new ResponseEntity<>(studentDtos, HttpStatus.OK);
//        return new ResponseEntity<>(cs.getExperience(),HttpStatus.OK);
    }

}
