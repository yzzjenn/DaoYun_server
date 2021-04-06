package com.yzz.system.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.Serializable;

@Data
public class MenuMetaVo implements Serializable {

    private String title;

    private String icon;

    private Boolean noCache;

    public MenuMetaVo(String title, String icon, Boolean noCache) {
        this.title = title;
        this.icon = icon;
        this.noCache = noCache;
    }
}
