package com.yzz.system.service.Impl;

import com.yzz.system.dao.DictDetailDto;
import com.yzz.system.dao.DictDto;
import com.yzz.system.dao.DictQueryCriteria;
import com.yzz.system.mapper.DictMapper;
import com.yzz.system.pojo.Dict;
import com.yzz.system.repository.DictRepository;
import com.yzz.system.service.DictService;
import com.yzz.util.PageUtil;
import com.yzz.util.QueryHelp;
import com.yzz.util.RedisUtils;
import com.yzz.util.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "dict")
public class DictServiceImpl implements DictService {

    private final DictRepository dictRepository;
    private final DictMapper dictMapper;
    private final RedisUtils redisUtils;

    @Override
    public Map<String, Object> queryAll(DictQueryCriteria dict, Pageable pageable) {
        Page<Dict> page = dictRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, dict, cb), pageable);
        return PageUtil.toPage(page.map(dictMapper::toDto));
    }

    @Override
    public List<DictDto> queryAll(DictQueryCriteria dict) {
        List<Dict> list = dictRepository.findAll((root, query, cb) -> QueryHelp.getPredicate(root, dict, cb));
        return dictMapper.toDto(list);
    }

    @Override
    //@Cacheable(key = "'name:' + #p0")
    public List<DictDto> getDictByName(String name) {
        name = '%'+name+'%';
        List<Dict> dicts;
        dicts =dictRepository.findByNameLike(name);

        if(dicts.size() != 0 ){
            return dictMapper.toDto(dictRepository.findByNameLike(name));
        }else{
            return dictMapper.toDto(dictRepository.findByDescriptionLike(name));
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(Dict resources) {
        dictRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(Dict resources) {
        // 清理缓存
        delCaches(resources);
        Dict dict = dictRepository.findById(resources.getId()).orElseGet(Dict::new);
        ValidationUtil.isNull(dict.getId(), "Dict", "id", resources.getId());
        resources.setId(dict.getId());
        dictRepository.save(resources);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        // 清理缓存
        List<Dict> dicts = dictRepository.findByIdIn(ids);
        for (Dict dict : dicts) {
            delCaches(dict);
        }
        dictRepository.deleteByIdIn(ids);
    }

    public void delCaches(Dict dict) {
        redisUtils.del("dept::name:" + dict.getName());
    }
}
