package com.yzz.system.service;


import com.yzz.system.dao.UserDto;
import com.yzz.system.dao.UserQueryCriteria;
import com.yzz.system.pojo.User;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.data.domain.Pageable;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

public interface UserService {
    /**
     * 根据ID查询
     *
     * @param id ID
     * @return /
     */
    UserDto findById(long id);

    /**
     * 新增用户
     *
     * @param resources /
     */
    void create(User resources);

    /**
     * 编辑用户
     *
     * @param resources /
     */
    void update(User resources);

    /**
     * 删除用户
     *
     * @param ids /
     */
    void delete(Set<Long> ids);

    /**
     * 根据用户名查询
     *
     * @param userName /
     * @return /
     */
    UserDto findByName(String userName);

    /**
     * 修改密码
     *
     * @param username        用户名
     * @param encryptPassword 密码
     */
    void updatePass(String username, String encryptPassword);

    /**
     * 修改头像
     *
     * @param file 文件
     * @return /
     */
    Map<String, String> updateAvatar(MultipartFile file);

    /**
     * 修改邮箱
     *
     * @param username 用户名
     * @param email    邮箱
     */
    void updateEmail(String username, String email);

    /**
     * 查询全部
     *
     * @param criteria 条件
     * @param pageable 分页参数
     * @return /
     */
    Object queryAll(UserQueryCriteria criteria, Pageable pageable);

    /**
     * 查询全部不分页
     *
     * @param criteria 条件
     * @return /
     */
    List<UserDto> queryAll(UserQueryCriteria criteria);

    /**
     * 按类型查询全部
     *
     * @param type 类型
     * @return /
     */
    List<UserDto> findByType(String type, Pageable pageable);

    /**
     * 导出数据
     *
     * @param queryAll 待导出的数据
     * @param response /
     * @throws IOException /
     */
    void download(List<UserDto> queryAll, HttpServletResponse response) throws IOException;

    /**
     * 用户自助修改资料
     *
     * @param resources /
     */
    void updateCenter(User resources);

    /**
     * 查询用户
     *
     * @param userName /
     */
    void checkExist(String userName);

    /**
     * 检查手机号是否已经注册
     *
     * @param phone
     * @return
     */
    Boolean checkRegister(String phone);

    /**
     * 通过手机号或邮箱查询
     *
     * @param count
     * @return
     */
    UserDto findByPhoneOrEmail(String count);

    void updatePassByPhone(String phone, String encryptPassword);
}
