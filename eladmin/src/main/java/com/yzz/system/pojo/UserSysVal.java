package com.yzz.system.pojo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name="user_sys_val")
public class UserSysVal implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "user_id")
    private Long userId;


    @OneToOne
    @JoinColumn(name = "val_id")
    private SysVal sysVal;

    @Column(name = "value")
    private Integer value;

//    @Column(name = "name")
//    private Integer name;
//
//    @Column(name = "remark")
//    private Integer remark;
//
//    public Integer getName() {
//        return name;
//    }
//
//    public void setName(Integer name) {
//        this.name = name;
//    }
//
//    public Integer getRemark() {
//        return remark;
//    }
//
//    public void setRemark(Integer remark) {
//        this.remark = remark;
//    }

    public void copy(UserSysVal source){
        BeanUtil.copyProperties(source,this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public SysVal getSysVal() {
        return sysVal;
    }

    public void setSysVal(SysVal sysVal) {
        this.sysVal = sysVal;
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }
}
