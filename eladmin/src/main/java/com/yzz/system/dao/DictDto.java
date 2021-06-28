package com.yzz.system.dao;

import com.yzz.base.BaseDTO;

import java.io.Serializable;
import java.util.List;

public class DictDto extends BaseDTO implements Serializable {

    private Long id;

    private List<DictDetailDto> dictDetails;

    private String name;

    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<DictDetailDto> getDictDetails() {
        return dictDetails;
    }

    public void setDictDetails(List<DictDetailDto> dictDetails) {
        this.dictDetails = dictDetails;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
