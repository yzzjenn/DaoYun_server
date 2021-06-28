package com.yzz.system.repository;

import com.yzz.system.pojo.SysVal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface SysValRepository  extends JpaRepository<SysVal, Long>, JpaSpecificationExecutor<SysVal> {
}

