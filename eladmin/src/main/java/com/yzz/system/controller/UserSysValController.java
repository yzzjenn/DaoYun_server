package com.yzz.system.controller;

import com.yzz.annotation.AnonymousAccess;
import com.yzz.system.dao.SysValQueryCriteria;
import com.yzz.system.pojo.SysVal;
import com.yzz.system.pojo.UserSysVal;
import com.yzz.system.service.UserSysValService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "学习行为变量")
@RestController
@RequestMapping("/api/userSysVal")
public class UserSysValController {

    private final UserSysValService userSysValService;

    public UserSysValController(UserSysValService userSysValService) {
        this.userSysValService = userSysValService;
    }

    @ApiOperation("查询行为变量")
    @GetMapping
    @PreAuthorize("@el.check('userSysVal:list')")
    @AnonymousAccess
    public ResponseEntity<Object> getUserSysVals(Long userId){
        return new ResponseEntity<>(userSysValService.findByUser(userId), HttpStatus.OK);
    }

    @ApiOperation("查询行为变量")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('sysVal:list')")
    @AnonymousAccess
    public ResponseEntity<Object> queryAll() {
        return new ResponseEntity<>(userSysValService.queryAll(new SysValQueryCriteria()), HttpStatus.OK);
    }

    @PostMapping
    @ApiOperation("新增行为变量")
    @PreAuthorize("@el.check('userSysVal:add')")
    @AnonymousAccess
    public ResponseEntity<Object> create(@Validated @RequestBody UserSysVal resources){
        userSysValService.create(resources.getSysVal());
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @PutMapping
    @ApiOperation("修改行为变量")
    @PreAuthorize("@el.check('userSysVal:edit')")
    @AnonymousAccess
    public ResponseEntity<Object> update(@Validated @RequestBody UserSysVal resources){
        userSysValService.update(resources.getSysVal());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除行为变量")
    @PreAuthorize("@el.check('userSysVal:del')")
    @DeleteMapping
    @AnonymousAccess
    public ResponseEntity<Object> deleteAll(@RequestBody Long id) {
        userSysValService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
