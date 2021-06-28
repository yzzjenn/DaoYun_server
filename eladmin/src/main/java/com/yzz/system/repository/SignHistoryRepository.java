package com.yzz.system.repository;


import com.yzz.system.pojo.SignHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface SignHistoryRepository extends JpaRepository<SignHistory, Long>, JpaSpecificationExecutor<SignHistory> {
    List<SignHistory> findByCourseId(Long id);

    List<SignHistory> findByCourseIdOrderByCreateTimeDesc(Long id);

    @Query(value = "select count(*) where course_id = ?1", nativeQuery = true)
    int getSignCountByCourseId(Long id);

    int countByCourseId(Long id);
}
