package com.eladmin.service;

import com.eladmin.config.EmailConfig;
import com.eladmin.pojo.vo.EmailVo;
import org.springframework.scheduling.annotation.Async;

public interface EmailService {
    /**
     * 更新邮件配置
     *
     * @param emailConfig 邮箱配置
     * @param old         /
     * @return /
     * @throws Exception /
     */
    EmailConfig config(EmailConfig emailConfig, EmailConfig old) throws Exception;

    /**
     * 查询配置
     *
     * @return EmailConfig 邮件配置
     */
    EmailConfig find();

    /**
     * 发送邮件
     *
     * @param emailVo     邮件发送的内容
     * @param emailConfig 邮件配置
     * @throws Exception /
     */
    @Async
    void send(EmailVo emailVo, EmailConfig emailConfig);

    /**
     * 发送邮件 邮件配置从application中获取
     *
     * @param emailVo 邮件发送的内容
     * @throws Exception /
     */
    @Async
    void send(EmailVo emailVo);
}
