package com.yzz.system.service.Impl;

import com.yzz.system.dao.RoleSmallDto;
import com.yzz.system.dao.SignHistoryDto;
import com.yzz.system.dao.SignHistoryQueryCriteria;
import com.yzz.system.dao.UserDto;
import com.yzz.system.mapper.SignHistoryMapper;
import com.yzz.system.mapper.StudentSmallMapper;
import com.yzz.system.pojo.Course;
import com.yzz.system.pojo.SignHistory;
import com.yzz.system.pojo.Student;
import com.yzz.system.pojo.StudentCourseSign;
import com.yzz.system.repository.CourseRepository;
import com.yzz.system.repository.SignHistoryRepository;
import com.yzz.system.repository.StudentCourseSignRepository;
import com.yzz.system.service.CourseService;
import com.yzz.system.service.RoleService;
import com.yzz.system.service.SignHistoryService;
import com.yzz.system.service.UserService;
import com.yzz.util.PageUtil;
import com.yzz.util.QueryHelp;
import com.yzz.util.SecurityUtils;
import com.yzz.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "signHistory")
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class SignHistoryServiceImpl implements SignHistoryService {

    private final SignHistoryRepository signHistoryRepository;
    private final SignHistoryMapper signHistoryMapper;
    private final StudentCourseSignRepository studentCourseSignRepository;
    private final StudentSmallMapper studentSmallMapper;
    private final UserService userService;
    private final RoleService roleService;
    private final CourseRepository courseRepository;
    private final CourseService courseService;


    @Override
    //@Cacheable
    public Map<String, Object> queryAll(SignHistoryQueryCriteria criteria, Pageable pageable) {
        UserDto user = userService.findByName(SecurityUtils.getCurrentUsername());
        List<RoleSmallDto> roleSet = roleService.findByUsersId(user.getId());
        Long id = user.getId();
        Boolean isTeacher = false;
        for (RoleSmallDto role : roleSet) {
            if ("学校管理员".equals(role.getName()) || "普通用户".equals(role.getName())) {
                isTeacher = true;
                break;
            }
        }
        List<SignHistory> signHistories = new ArrayList<>();
        if (isTeacher) {
            int pageSize = pageable.getPageSize();
            Long offSet = pageable.getOffset();
            List<Course> courses = courseRepository.findByUserId(id);
            for (Course course : courses) {
                signHistories.addAll(course.getSignHistory());
            }
            List<SignHistoryDto> content = signHistories.stream().map(signHistoryMapper::toDto).collect(Collectors.toList());
            Map<String, Object> map = new LinkedHashMap<>(2);
            map.put("content", content);
            map.put("totalElements", content.size());
            return map;
        }
        Page<SignHistory> page = signHistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(signHistoryMapper::toDto));
    }

    @Override
    //@Cacheable
    public List<SignHistoryDto> queryAll(SignHistoryQueryCriteria criteria) {
        return signHistoryMapper.toDto(signHistoryRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder)));
    }

    @Override
    @Cacheable(key = "#p0")
    public SignHistoryDto findById(Long id) {
        SignHistory signHistory = signHistoryRepository.findById(id).orElseGet(SignHistory::new);
        ValidationUtil.isNull(signHistory.getId(), "SignHistory", "id", id);
        return signHistoryMapper.toDto(signHistory);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public SignHistoryDto create(SignHistory resources) {
        return signHistoryMapper.toDto(signHistoryRepository.save(resources));
    }

    @Override
//    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void update(SignHistory resources) {
        SignHistory signHistory = signHistoryRepository.findById(resources.getId()).orElseGet(SignHistory::new);
        ValidationUtil.isNull(signHistory.getId(), "SignHistory", "id", resources.getId());
        signHistory.copy(resources);
        signHistoryRepository.save(signHistory);
    }

    /**
     * 发起签到
     *
     * @param resource 签到信息
     */
    @Override
    public SignHistory releaseSign(SignHistory resource) {
//        SignHistoryDto signHistoryDto = create(resource);
        SignHistory signHistory = signHistoryRepository.save(resource);
        signDestruction(signHistoryRepository.save(resource));
        return signHistory;
    }

    /**
     * 定时任务，指定分钟后改变签到状态
     *
     * @param signHistory 签到信息
     */
    private void signDestruction(SignHistory signHistory) {
        //以下示例为程序调用结束继续运行
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        try {
            executorService.schedule(() -> {
                if (signHistory.getStatus()) {
                    signHistory.setStatus(false);
                    signHistoryRepository.save(signHistory);
                }
            }, 10 * 60 * 1000L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Map<String, Object> findSignHistoryStudentsById(Long id) {
        List<StudentCourseSign> signList = studentCourseSignRepository.findBySignHistory_Id(id);
        Set<Map> attendances = new HashSet<>();
        Set<Map> absences = new HashSet<>();
        for (StudentCourseSign sign : signList) {
            Map<String, Object> studentSignInfo = new HashMap<>(3);
            Student student = sign.getStudent();
            studentSignInfo.put("studentName", student.getName());
            studentSignInfo.put("studentNumber", student.getStudentNumber());
            studentSignInfo.put("signTime", sign.getCreateTime());
           /* if (sign.getAttendance()) {
                attendances.add(studentSmallMapper.toDto(sign.getStudent()));
            } else {
                absences.add(studentSmallMapper.toDto(sign.getStudent()));
            }*/
            if (sign.getAttendance()) {
                attendances.add(studentSignInfo);
            } else {
                absences.add(studentSignInfo);
            }
        }
        Map<String, Object> result = new HashMap<>(2);
        result.put("attendances", attendances);
        result.put("absences", absences);
        return result;
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void deleteAll(Long[] ids) {
        for (Long id : ids) {
            signHistoryRepository.deleteById(id);
        }
    }

//    @Override
//    public void download(List<SignHistoryDto> all, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (SignHistoryDto signHistory : all) {
//            Map<String, Object> map = new LinkedHashMap<>();
//            /*map.put("课程名", signHistory.getCourse().getCourseName());
//            map.put("课程编码", signHistory.getCourse().getCourseCode());
//            map.put("授课教师", signHistory.getCourse().getTeacherName());*/
//            map.put("出勤人数", signHistory.getAttendance());
//            map.put("缺勤人数", signHistory.getAbsence());
//            map.put("发起时间", signHistory.getCreateTime());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
//    }
}
