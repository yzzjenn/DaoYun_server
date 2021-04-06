package com.yzz.system.dao;

import com.yzz.base.BaseDTO;

import java.io.Serializable;
import java.util.Objects;
import java.util.Set;


public class RoleDto extends BaseDTO implements Serializable {
    private Long id;

    private Set<MenuDto> menus;

    private Set<DeptDto> depts;

    private String name;

    private String dataScope;

    private Integer level;

    private String description;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        RoleDto roleDto = (RoleDto) o;
        return Objects.equals(id, roleDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Set<MenuDto> getMenus() {
        return menus;
    }

    public void setMenus(Set<MenuDto> menus) {
        this.menus = menus;
    }

    public Set<DeptDto> getDepts() {
        return depts;
    }

    public void setDepts(Set<DeptDto> depts) {
        this.depts = depts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDataScope() {
        return dataScope;
    }

    public void setDataScope(String dataScope) {
        this.dataScope = dataScope;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
