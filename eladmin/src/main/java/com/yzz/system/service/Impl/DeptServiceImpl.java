package com.yzz.system.service.Impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import com.yzz.exception.BadRequestException;
import com.yzz.system.dao.DeptDto;
import com.yzz.system.dao.DeptQueryCriteria;
import com.yzz.system.mapper.DeptMapper;
import com.yzz.system.pojo.Dept;
import com.yzz.system.pojo.User;
import com.yzz.system.repository.DeptRepository;
import com.yzz.system.repository.RoleRepository;
import com.yzz.system.repository.UserRepository;
import com.yzz.system.service.DeptService;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.yzz.util.QueryHelp;
import com.yzz.util.RedisUtils;
import com.yzz.util.ValidationUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dept")
public class DeptServiceImpl implements DeptService {
    private final DeptRepository deptRepository;
    private final DeptMapper deptMapper;
    private final UserRepository userRepository;
    private final RedisUtils redisUtils;
    private final RoleRepository roleRepository;
//    private final CourseRepository courseRepository;

    @Override
    public List<DeptDto> queryAll(DeptQueryCriteria criteria, Boolean isQuery) throws Exception {
        Sort sort = new Sort(Sort.Direction.ASC, "deptSort");
        if (isQuery) {
            criteria.setPidIsNull(true);
            List<Field> fields = QueryHelp.getAllFields(criteria.getClass(), new ArrayList<>());
            List<String> fieldNames = new ArrayList<String>() {{
                add("pidIsNull");
                add("enabled");
            }};
            for (Field field : fields) {
                //???????????????????????????????????????private??????????????????
                field.setAccessible(true);
                Object val = field.get(criteria);
                if (fieldNames.contains(field.getName())) {
                    continue;
                }
                if (ObjectUtil.isNotNull(val)) {
                    criteria.setPidIsNull(null);
                    break;
                }
            }
        }
        return deptMapper.toDto(deptRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), sort));
    }

    @Override
    @Cacheable(key = "'id:' + #p0")
    public DeptDto findById(Long id) {
        Dept dept = deptRepository.findById(id).orElseGet(Dept::new);
        ValidationUtil.isNull(dept.getId(), "Dept", "id", id);
        return deptMapper.toDto(dept);
    }

    @Override
    @Cacheable(key = "'pid:' + #p0")
    public List<Dept> findByPid(long pid) {
        return deptRepository.findByPid(pid);
    }

    @Override
    public Set<Dept> findByRoleId(Long id) {
        return deptRepository.findByRoleId(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Dept resources) {
        deptRepository.save(resources);
        // ?????????????????????
        resources.setSubCount(0);
        if (resources.getPid() != null) {
            // ????????????
            redisUtils.del("dept::pid:" + resources.getPid());
            updateSubCnt(resources.getPid());
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dept resources) {
        // ????????????
        Long pid = findById(resources.getId()).getPid();
        if (resources.getPid() != null && resources.getId().equals(resources.getPid())) {
            throw new BadRequestException("?????????????????????");
        }
        Dept dept = deptRepository.findById(resources.getId()).orElseGet(Dept::new);
        ValidationUtil.isNull(dept.getId(), "Dept", "id", resources.getId());
        resources.setId(dept.getId());
        deptRepository.save(resources);
        if (resources.getPid() == null) {
            updateSubCnt(pid);
        } else {
            pid = resources.getPid();
            updateSubCnt(resources.getPid());
        }
        // ????????????
        delCaches(resources.getId(), pid);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<DeptDto> deptDtos) {
        for (DeptDto deptDto : deptDtos) {
            // ????????????
            delCaches(deptDto.getId(), deptDto.getPid());
            deptRepository.deleteById(deptDto.getId());
            if (deptDto.getPid() != null) {
                updateSubCnt(deptDto.getPid());
            }
        }
    }

    @Override
    public void download(List<DeptDto> deptDtos, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (DeptDto deptDTO : deptDtos) {
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("????????????", deptDTO.getName());
//            map.put("????????????", deptDTO.getEnabled() ? "??????" : "??????");
//            map.put("????????????", deptDTO.getCreateTime());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
    }

    @Override
    public Set<DeptDto> getDeleteDepts(List<Dept> menuList, Set<DeptDto> deptDtos) {
        for (Dept dept : menuList) {
            deptDtos.add(deptMapper.toDto(dept));
            List<Dept> depts = deptRepository.findByPid(dept.getId());
            if (depts != null && depts.size() != 0) {
                getDeleteDepts(depts, deptDtos);
            }
        }
        return deptDtos;
    }

    @Override
    public List<Long> getDeptChildren(Long deptId, List<Dept> deptList) {
        List<Long> list = new ArrayList<>();
        deptList.forEach(dept -> {
                    if (dept != null && dept.getEnabled()) {
                        List<Dept> depts = deptRepository.findByPid(dept.getId());
                        if (deptList.size() != 0) {
                            list.addAll(getDeptChildren(dept.getId(), depts));
                        }
                        list.add(dept.getId());
                    }
                }
        );
        return list;
    }

    @Override
    public List<DeptDto> getSuperior(DeptDto deptDto, List<Dept> depts) {
        if (deptDto.getPid() == null) {
            //depts.addAll(deptRepository.findByPidIsNull());
            return deptMapper.toDto(depts);
        }
        depts.addAll(deptRepository.findByPid(deptDto.getPid()));
        return getSuperior(findById(deptDto.getPid()), depts);
    }

    @Override
    public Object buildTree(List<DeptDto> deptDtos) {
        Set<DeptDto> trees = new LinkedHashSet<>();
        Set<DeptDto> depts = new LinkedHashSet<>();
        List<String> deptNames = deptDtos.stream().map(DeptDto::getName).collect(Collectors.toList());
        boolean isChild;
        for (DeptDto deptDTO : deptDtos) {
            isChild = false;
            if (deptDTO.getPid() == null) {
                trees.add(deptDTO);
            }
            for (DeptDto it : deptDtos) {
                if (it.getPid() != null && deptDTO.getId().equals(it.getPid())) {
                    isChild = true;
                    if (deptDTO.getChildren() == null) {
                        deptDTO.setChildren(new ArrayList<>());
                    }
                    deptDTO.getChildren().add(it);
                }
            }
            if (isChild) {
                depts.add(deptDTO);
            } else if (deptDTO.getPid() != null && !deptNames.contains(findById(deptDTO.getPid()).getName())) {
                depts.add(deptDTO);
            }
        }

        if (CollectionUtil.isEmpty(trees)) {
            trees = depts;
        }
        Map<String, Object> map = new HashMap<>(2);
        map.put("totalElements", deptDtos.size());
        map.put("content", CollectionUtil.isEmpty(trees) ? deptDtos : trees);
        return map;
    }

    private void updateSubCnt(Long deptId) {
        int count = deptRepository.countByPid(deptId);
        deptRepository.updateSubCntById(count, deptId);
    }

    @Override
    public void verification(Set<DeptDto> deptDtos) {
//        Set<Long> deptIds = deptDtos.stream().map(DeptDto::getId).collect(Collectors.toSet());
//        if (userRepository.countByDepts(deptIds) > 0) {
//            throw new BadRequestException("??????????????????????????????????????????????????????");
//        }
//        if (roleRepository.countByDepts(deptIds) > 0) {
//            throw new BadRequestException("??????????????????????????????????????????????????????");
//        }
//        if (courseRepository.countByDepts(deptIds) > 0) {
//            throw new BadRequestException("??????????????????????????????????????????????????????");
//        }
    }

    /**
     * ????????????
     *
     * @param id /
     */
    public void delCaches(Long id, Long pid) {
        List<User> users = userRepository.findByDeptRoleId(id);
        // ??????????????????
        redisUtils.delByKeys("data::user:", users.stream().map(User::getId).collect(Collectors.toSet()));
        redisUtils.del("dept::id:" + id);
        if (pid != null) {
            redisUtils.del("dept::pid:" + pid);
        }
    }

    @Override
    public List<DeptDto> findAll() {
        return deptMapper.toDto(deptRepository.findAll());
    }
}
