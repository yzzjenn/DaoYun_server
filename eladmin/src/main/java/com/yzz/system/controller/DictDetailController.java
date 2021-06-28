package com.yzz.system.controller;

import com.yzz.annotation.AnonymousAccess;
import com.yzz.exception.BadRequestException;
import com.yzz.system.dao.DictDetailDto;
import com.yzz.system.dao.DictDetailQueryCriteria;
import com.yzz.system.pojo.DictDetail;
import com.yzz.system.service.DictDetailService;
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

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequiredArgsConstructor
@Api(tags = "字典详情管理")
@RequestMapping("/api/dictDetail")
public class DictDetailController {
    private final DictDetailService dictDetailService;
    private static final String ENTITY_NAME = "dictDetail";

    @ApiOperation("查询字典详情")
    @GetMapping
    @AnonymousAccess
//    public ResponseEntity<Object> query(DictDetailQueryCriteria criteria,
//                                        @PageableDefault(sort = {"dictSort"}, direction = Sort.Direction.ASC) Pageable pageable) {
//        return new ResponseEntity<>(dictDetailService.queryAll(criteria, pageable), HttpStatus.OK);
//    }
    public ResponseEntity<Object> query(@RequestParam String dictName,@PageableDefault(sort = {"dictSort"}, direction = Sort.Direction.ASC) Pageable pageable){

        List<DictDetailDto> dicts = dictDetailService.getDictByName(dictName);
        System.out.println(dictName);
        System.out.println(dicts);
        return new ResponseEntity<>(dicts, HttpStatus.OK);
    }

    @ApiOperation("查询多个字典详情")
    @GetMapping(value = "/map")
    @AnonymousAccess
    public ResponseEntity<Object> getDictDetailMaps(@RequestParam String dictName) {
        String[] names = dictName.split("[,，]");
        Map<String, List<DictDetailDto>> dictMap = new HashMap<>(16);
        for (String name : names) {
            dictMap.put(name, dictDetailService.getDictByName(name));
        }
        return new ResponseEntity<>(dictMap, HttpStatus.OK);
    }

    @ApiOperation("新增字典详情")
    @PostMapping
    @PreAuthorize("@el.check('dict:add')")
    @AnonymousAccess
    public ResponseEntity<Object> create(@Validated @RequestBody DictDetail resources) {
        if (resources.getId() != null) {
            throw new BadRequestException("A new " + ENTITY_NAME + " cannot already have an ID");
        }
        dictDetailService.create(resources);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

    @ApiOperation("修改字典详情")
    @PutMapping
    @PreAuthorize("@el.check('dict:edit')")
    @AnonymousAccess
    public ResponseEntity<Object> update(@Validated(DictDetail.Update.class) @RequestBody DictDetail resources) {
        dictDetailService.update(resources);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ApiOperation("删除字典详情")
    @DeleteMapping(value = "/{id}")
    @PreAuthorize("@el.check('dict:del')")
    @AnonymousAccess
    public ResponseEntity<Object> delete(@PathVariable Long id) {
        dictDetailService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
