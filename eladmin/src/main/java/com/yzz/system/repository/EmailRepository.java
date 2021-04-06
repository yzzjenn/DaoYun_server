package com.yzz.system.repository;

import com.yzz.system.pojo.EmailConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmailRepository extends JpaRepository<EmailConfig, Long> {
}

