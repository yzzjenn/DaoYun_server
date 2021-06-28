package com.yzz.system.repository;

import com.yzz.system.pojo.Dict;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Set;

public interface DictRepository extends JpaRepository<Dict, Long>, JpaSpecificationExecutor<Dict> {

    /**
     * 删除
     *
     * @param ids /
     */
    void deleteByIdIn(Set<Long> ids);

    /**
     * 查询
     *
     * @param ids /
     * @return /
     */
    List<Dict> findByIdIn(Set<Long> ids);

    /**
     * 查询
     *
     * @param name /
     * @return /
     */
//    @Query("select dict0_.id as dict_id1_6_, dict0_.description \n" +
//            "as descript6_6_, dict0_.name as name7_6_ from Dict dict0_ where dict0_.name like CONCAT('%',:name,'%') or dict0_.description like CONCAT('%',:name,'%')", nativeQuery = true)
    List<Dict> findByNameLike(String name);


    List<Dict> findByDescriptionLike(String name);
}
