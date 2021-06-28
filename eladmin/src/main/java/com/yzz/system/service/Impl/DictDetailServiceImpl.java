package com.yzz.system.service.Impl;


import com.yzz.system.dao.DictDetailDto;
import com.yzz.system.dao.DictDetailQueryCriteria;
import com.yzz.system.mapper.DictDetailMapper;
import com.yzz.system.pojo.Dict;
import com.yzz.system.pojo.DictDetail;
import com.yzz.system.repository.DictDetailRepository;
import com.yzz.system.repository.DictRepository;
import com.yzz.system.service.DictDetailService;
import com.yzz.util.PageUtil;
import com.yzz.util.QueryHelp;
import com.yzz.util.RedisUtils;
import com.yzz.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictDetailServiceImpl implements DictDetailService {

    private final DictRepository dictRepository;
    private final DictDetailRepository dictDetailRepository;
    private final DictDetailMapper dictDetailMapper;
    private final RedisUtils redisUtils;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(DictDetail resources) {
        dictDetailRepository.save(resources);
        // 清理缓存
        delCaches(resources);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(DictDetail resources) {

        DictDetail dictDetail = dictDetailRepository.findById(resources.getId()).orElseGet(DictDetail::new);
        ValidationUtil.isNull(dictDetail.getId(), "DictDetail", "id", resources.getId());
        resources.setId(dictDetail.getId());
        dictDetailRepository.save(resources);
        // 清理缓存
        delCaches(resources);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Long id) {
        DictDetail dictDetail = dictDetailRepository.findById(id).orElseGet(DictDetail::new);
        // 清理缓存
        delCaches(dictDetail);
        dictDetailRepository.deleteById(id);

    }

    @Override
    public Map<String, Object> queryAll(DictDetailQueryCriteria criteria, Pageable pageable) {
        Page<DictDetail> page = dictDetailRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(dictDetailMapper::toDto));
    }

    @Override
    //@Cacheable(key = "'name:' + #p0")
    public List<DictDetailDto> getDictByName(String name) {
//        System.out.println("111" +dictDetailRepository.findByDictName(name));
        return dictDetailMapper.toDto(dictDetailRepository.findByDictName(name));
    }

    public void delCaches(DictDetail dictDetail) {
        Dict dict = dictRepository.findById(dictDetail.getDict().getId()).orElseGet(Dict::new);
        redisUtils.del("dept::name:" + dict.getName());
    }
}
