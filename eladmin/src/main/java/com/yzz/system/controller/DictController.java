package com.yzz.system.controller;

import com.yzz.annotation.AnonymousAccess;
import com.yzz.exception.BadRequestException;
import com.yzz.system.dao.DictDetailDto;
import com.yzz.system.dao.DictDto;
import com.yzz.system.dao.DictQueryCriteria;
import com.yzz.system.pojo.Dict;
import com.yzz.system.service.DictService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Set;


@RestController
@RequiredArgsConstructor
@Api(tags = "字典管理")
@RequestMapping("/api/dict")
public class DictController {
    private final DictService dictService;
    private static final String ENTITY_NAME = "dict";

    @ApiOperation("查询字典")
    @GetMapping(value = "/all")
    @PreAuthorize("@el.check('dict:list')")
    @AnonymousAccess
    public ResponseEntity<Object> queryAll() {
        return new ResponseEntity<>(dictService.queryAll(new DictQueryCriteria()), HttpStatus.OK);
    }



    @ApiOperation("查询字典")
    @GetMapping
    @PreAuthorize("@el.check('dict:list')")
    @AnonymousAccess
    public ResponseEntity<Object> query(DictQueryCriteria resources, Pageable pageable) {
        return new ResponseEntity<>(dictService.queryAll(resources, pageable), HttpStatus.OK);
    }

    @ApiOperation("根据字典名或者描述查询字典")
    @GetMapping(value = "/search")
    @AnonymousAccess
    public ResponseEntity<Object> search(@RequestParam String dictName){
        System.out.println(dictName);
        List<DictDto> dicts = dictService.getDictByName(dictName);
        System.out.println(dicts);
        return new ResponseEntity<>(dicts, HttpStatus.OK);
    }

    @ApiOperation("新增字典")
    @PostMapping
    @PreAuthorize("@el.check('dict:add')")
    @AnonymousAccess
    public ResponseEntity<Object> create(@Validated @RequestBody Dict resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        dictService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }


    @ApiOperation("修改字典")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    @AnonymousAccess
    public ResponseEntity<Object> update(@Validated(Dict.Update.class) @RequestBody Dict resources) {
        dictService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ApiOperation("删除字典")
    @DeleteMapping
    @PreAuthorize("@el.check('dict:del')")
    @AnonymousAccess
    public ResponseEntity<Object> delete(@RequestBody Set<Long> ids) {
        dictService.delete(ids);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
