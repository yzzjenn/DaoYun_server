package com.yzz.system.dao;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;

@Data
public class UserSysValDto implements Serializable {

    /**
     * 防止精度丢失
     */
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    private Long userId;

    private SysValDto sysVal;

    private Integer value;

//
//    private Integer name;
//
//    private Integer remark;
}
