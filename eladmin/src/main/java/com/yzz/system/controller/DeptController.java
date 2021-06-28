package com.yzz.system.controller;

import cn.hutool.core.collection.CollectionUtil;
import com.yzz.annotation.AnonymousAccess;
import com.yzz.exception.BadRequestException;
import com.yzz.system.dao.DeptDto;
import com.yzz.system.dao.DeptQueryCriteria;
import com.yzz.system.pojo.Dept;
import com.yzz.system.service.DeptService;
import com.yzz.util.PageUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;


@RestController
@RequiredArgsConstructor
@Api(tags = "部门管理")
@RequestMapping("/api/dept")
public class DeptController {
    private final DeptService deptService;
    private static final String ENTITY_NAME = "dept";

    @ApiOperation("查询部门")
    @GetMapping
    @PreAuthorize("@el.check('user:list','dept:list','course:list','student:list')")
    public ResponseEntity<Object> query(DeptQueryCriteria criteria) throws Exception {
        List<DeptDto> deptDtos = deptService.queryAll(criteria, true);
        return new ResponseEntity<>(PageUtil.toPage(deptDtos, deptDtos.size()), HttpStatus.OK);
    }

    @ApiOperation("查询部门:根据ID获取同级与上级数据")
    @PostMapping("/superior")
    @PreAuthorize("@el.check('user:list','dept:list')")
    @AnonymousAccess
    public ResponseEntity<Object> getSuperior(@RequestBody List<Long> ids) {
        Set<DeptDto> deptDtos = new LinkedHashSet<>();
        for (Long id : ids) {
            DeptDto deptDto = deptService.findById(id);
            List<DeptDto> depts = deptService.getSuperior(deptDto, new ArrayList<>());
            deptDtos.addAll(depts);
        }
        return new ResponseEntity<>(deptService.buildTree(new ArrayList<>(deptDtos)), HttpStatus.OK);
    }

    @ApiOperation("查询部门:查询所有部门，不做数据权限")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('course:list','student:list','user:list','dept:list')")
    public ResponseEntity<Object> getAllDepts(DeptQueryCriteria criteria) throws Exception {
        List<DeptDto> deptDtos = deptService.queryAll(criteria, true);
        return new ResponseEntity<>(deptService.buildTree(deptDtos), HttpStatus.OK);
    }

    @ApiOperation("新增部门")
    @PostMapping
    @PreAuthorize("@el.check('dept:add')")
    public ResponseEntity<Object> create(@Validated @RequestBody Dept resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        deptService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("修改部门")
    @PutMapping
    @PreAuthorize("@el.check('dept:edit')")
    public ResponseEntity<Object> update(@Validated(Dept.Update.class) @RequestBody Dept resources) {
        deptService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除部门")
    @DeleteMapping
    @PreAuthorize("@el.check('dept:del')")
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        Set<DeptDto> deptDtos = new HashSet<>();
        for (Long id : ids) {
            List<Dept> deptList = deptService.findByPid(id);
            deptDtos.add(deptService.findById(id));
            if (CollectionUtil.isNotEmpty(deptList)) {
                deptDtos = deptService.getDeleteDepts(deptList, deptDtos);
            }
        }
        // 验证是否被角色或用户或课程关联  部门即院校与三者有外键约束重写了verification方法
        deptService.verification(deptDtos);
        deptService.delete(deptDtos);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
