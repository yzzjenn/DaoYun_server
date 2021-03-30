package com.eladmin.service.Impl;



import com.eladmin.dao.JobSmallDto;
import com.eladmin.dao.RoleSmallDto;
import com.eladmin.dao.UserDto;
import com.eladmin.dao.UserQueryCriteria;
import com.eladmin.mapper.UserMapper;
import com.eladmin.pojo.User;
import com.eladmin.repository.UserRepository;
import com.eladmin.service.UserService;
import exception.EntityExistException;
import exception.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import util.PageUtil;
import util.RedisUtils;
import util.ValidationUtil;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@CacheConfig(cacheNames = "user")
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final RedisUtils redisUtils;


    @Override
    @Cacheable(key = "'id:' + #p0")
    @Transactional(rollbackFor = Exception.class)
    public UserDto findById(long id) {
        User user = userRepository.findById(id).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(), "User", "id", id);
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void create(User resources) {
        if (userRepository.findByUsername(resources.getUsername()) != null) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }
        if (userRepository.findByEmail(resources.getEmail()) != null) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        userRepository.save(resources);

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void update(User resources) {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        ValidationUtil.isNull(user.getId(), "User", "id", resources.getId());
        User user1 = userRepository.findByUsername(resources.getUsername());
        User user2 = userRepository.findByEmail(resources.getEmail());

        if (user1 != null && !user.getId().equals(user1.getId())) {
            throw new EntityExistException(User.class, "username", resources.getUsername());
        }

        if (user2 != null && !user.getId().equals(user2.getId())) {
            throw new EntityExistException(User.class, "email", resources.getEmail());
        }
        // 如果用户的角色改变
        if (!resources.getRoles().equals(user.getRoles())) {
            redisUtils.del("data::user:" + resources.getId());
            redisUtils.del("menu::user:" + resources.getId());
            redisUtils.del("role::auth:" + resources.getId());
        }
        user.setUsername(resources.getUsername());
        user.setEmail(resources.getEmail());
        user.setEnabled(resources.getEnabled());
        user.setRoles(resources.getRoles());
        user.setDept(resources.getDept());
        user.setJobs(resources.getJobs());
        user.setPhone(resources.getPhone());
        user.setNickName(resources.getNickName());
        user.setGender(resources.getGender());
        userRepository.save(user);
        // 清除缓存
        delCaches(user.getId(), user.getUsername());

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void delete(Set<Long> ids) {
        for (Long id : ids) {
            // 清理缓存
            UserDto user = findById(id);
            delCaches(user.getId(), user.getUsername());
        }
        userRepository.deleteAllByIdIn(ids);

    }

    @Override
    @Cacheable(key = "'username:' + #p0")
    public UserDto findByName(String userName) {
        //User user = userRepository.findByUsername(userName);
        User user;
        if (ValidationUtil.isEmail(userName)) {
            user = userRepository.findByEmail(userName);
        } else {
            user = userRepository.findByUsername(userName);
            if (user == null) {
                user = userRepository.findByPhone(userName);
            }
        }
        if (user == null) {
            throw new EntityNotFoundException(User.class, "name", userName);
        } else {
            return userMapper.toDto(user);
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePass(String username, String encryptPassword) {
        userRepository.updatePass(username, encryptPassword, new Date());
        redisUtils.del("user::username:" + username);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, String> updateAvatar(MultipartFile file) {
        return null;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateEmail(String username, String email) {
        userRepository.updateEmail(username, email);
        redisUtils.del("user::username:" + username);
    }

    @Override
    public Object queryAll(UserQueryCriteria criteria, Pageable pageable) {
        Page<User> page = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder), pageable);
        return PageUtil.toPage(page.map(userMapper::toDto));
    }

    @Override
    public List<UserDto> queryAll(UserQueryCriteria criteria) {
        List<User> users = userRepository.findAll((root, criteriaQuery, criteriaBuilder) -> QueryHelp.getPredicate(root, criteria, criteriaBuilder));
        return userMapper.toDto(users);
    }

    @Override
    public List<UserDto> findByType(String type, Pageable pageable) {
        List<User> users = userRepository.findByType(type);
        return userMapper.toDto(users);
    }

    @Override
    public void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException {
//        List<Map<String, Object>> list = new ArrayList<>();
//        for (UserDto userDTO : queryAll) {
//            List<String> roles = userDTO.getRoles().stream().map(RoleSmallDto::getName).collect(Collectors.toList());
//            Map<String, Object> map = new LinkedHashMap<>();
//            map.put("用户名", userDTO.getUsername());
//            map.put("角色", roles);
//            map.put("部门", userDTO.getDept().getName());
//            map.put("岗位", userDTO.getJobs().stream().map(JobSmallDto::getName).collect(Collectors.toList()));
//            map.put("邮箱", userDTO.getEmail());
//            map.put("状态", userDTO.getEnabled() ? "启用" : "禁用");
//            map.put("手机号码", userDTO.getPhone());
//            map.put("修改密码的时间", userDTO.getPwdResetTime());
//            map.put("创建日期", userDTO.getCreateTime());
//            list.add(map);
//        }
//        FileUtil.downloadExcel(list, response);
    }

    @Override
    public void updateCenter(User resources) {
        User user = userRepository.findById(resources.getId()).orElseGet(User::new);
        user.setNickName(resources.getNickName());
        user.setPhone(resources.getPhone());
        user.setGender(resources.getGender());
        userRepository.save(user);
        // 清理缓存
        delCaches(user.getId(), user.getUsername());
    }

    @Override
    public void checkExist(String userName) {
        User user;
        if (ValidationUtil.isEmail(userName)) {
            user = userRepository.findByEmail(userName);
        } else {
            user = userRepository.findByUsername(userName);
        }
        if (user == null) {
            throw new RuntimeException("该用户不存在");
        }

    }

    @Override
    public Boolean checkRegister(String phone) {
        if (userRepository.findByPhone(phone) != null) {
            return false;
        }
        return true;
    }

    @Override
    public UserDto findByPhoneOrEmail(String count) {
        User user;
        if (ValidationUtil.isEmail(count)) {
            user = userRepository.findByEmail(count);
        } else {
            user = userRepository.findByPhone(count);
        }
        return userMapper.toDto(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updatePassByPhone(String phone, String encryptPassword) {
        userRepository.updatePassByPhone(phone, encryptPassword, new Date());
    }
    /**
     * 清理缓存
     *
     * @param id /
     */
    public void delCaches(Long id, String username) {
        redisUtils.del("user::id:" + id);
        redisUtils.del("user::username:" + username);
    }
}
