package com.yzz.system.dao;

import lombok.Data;

import java.io.Serializable;

@Data
public class RoleSmallDto implements Serializable {
    private Long id;

    private String name;

    private Integer level;

    private String dataScope;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }
}
