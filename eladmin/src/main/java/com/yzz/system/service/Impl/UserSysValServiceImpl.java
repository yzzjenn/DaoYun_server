package com.yzz.system.service.Impl;

import cn.hutool.core.lang.Snowflake;
import cn.hutool.core.util.IdUtil;
import com.yzz.system.dao.*;
import com.yzz.system.mapper.SysValMapper;
import com.yzz.system.mapper.UserSysValMapper;
import com.yzz.system.pojo.Dict;
import com.yzz.system.pojo.DictDetail;
import com.yzz.system.pojo.SysVal;
import com.yzz.system.pojo.UserSysVal;
import com.yzz.system.repository.SysValRepository;
import com.yzz.system.repository.UserSysValRepository;
import com.yzz.system.service.UserSysValService;
import com.yzz.util.QueryHelp;
import com.yzz.util.RedisUtils;
import com.yzz.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(propagation = Propagation.SUPPORTS, readOnly = true, rollbackFor = Exception.class)
public class UserSysValServiceImpl implements UserSysValService {

    private final UserSysValRepository userSysValRepository;
    private final SysValRepository sysValRepository;
    private final UserSysValMapper userSysValMapper;
    private final SysValMapper sysValMapper;
    private final RedisUtils redisUtils;
    @Override
    //@Cacheable
    public Map<String, Object> findByUser(Long userId){
        List<UserSysVal> vals = userSysValRepository.findByUserId(userId);
        List<SysVal> temps = vals.stream().map(UserSysVal::getSysVal).collect(Collectors.toList());
        List<Long> ids = temps.stream().map(SysVal::getId).collect(Collectors.toList());
        List<SysVal> sysVals = sysValRepository.findAll();
        sysVals.forEach(sysVal -> {
            /*用户变量表不存在对应的系统变量值，则新增一个*/
            if (!ids.contains(sysVal.getId())) {
                UserSysVal newVal = new UserSysVal();
                newVal.setUserId(userId);
                newVal.setSysVal(new SysVal(sysVal.getId()));
                newVal.setValue(sysVal.getDefaultValue());
                vals.add(userSysValRepository.save(newVal));
            }
        });
        List<UserSysValDto> dtos = userSysValMapper.toDto(vals);
        Map<String,Object> map = new LinkedHashMap<>(2);
        map.put("content",dtos);
        map.put("totalElements",dtos.size());
        return map;
    }

    @Override
    public List<SysValDto> queryAll(SysValQueryCriteria sysval) {
        List<SysVal> list = sysValRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, sysval, cb));
        return sysValMapper.toDto(list);
    }

    @Override
    //@Cacheable(key = "#p0")
//    public UserSysValDto findById(Long id) {
//        UserSysVal userSysVal = userSysValRepository.findById(id).orElseGet(UserSysVal::new);
//        ValidationUtil.isNull(userSysVal.getId(),"UserSysVal","id",id);
//        return userSysValMapper.toDto(userSysVal);
//    }
    public UserSysValDto findById(Long id) {
        UserSysVal userSysVal = userSysValRepository.findById(id).orElseGet(UserSysVal::new);
        ValidationUtil.isNull(userSysVal.getId(),"SysVal","id",id);
        return userSysValMapper.toDto(userSysVal);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
//    public void create(UserSysVal resources) {
//        Snowflake snowflake = IdUtil.createSnowflake(1, 1);
//        resources.setId(snowflake.nextId());
//        userSysValRepository.save(resources);
//    }
    public void create(SysVal resources){
        sysValRepository.save(resources);
    }

    @Override
    //@CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
//    public void update(UserSysVal resources) {
//        UserSysVal userSysVal = userSysValRepository.findById(resources.getId()).orElseGet(UserSysVal::new);
//        ValidationUtil.isNull( userSysVal.getId(),"UserSysVal","id",resources.getId());
//        userSysVal.copy(resources);
//        System.out.println((userSysVal));
//        userSysValRepository.save(userSysVal);
//    }
    public  void update(SysVal resources){
        SysVal sysVal = sysValRepository.findById(resources.getId()).orElseGet(SysVal::new);
        ValidationUtil.isNull(sysVal.getId(), "SysVal", "id", resources.getId());
        resources.setId(sysVal.getId());
        sysValRepository.save(resources);
    }

    @Override
    //@CacheEvict(allEntries = true)
    public void delete(Long id) {
        UserSysVal userSysVal = userSysValRepository.findById(id).orElseGet(UserSysVal::new);
        // 清理缓存
        userSysValRepository.deleteById(id);
        sysValRepository.deleteById(userSysVal.getSysVal().getId());
    }
}
