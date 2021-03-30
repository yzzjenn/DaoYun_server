package com.eladmin.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class JobSmallDto implements Serializable {
    private Long id;

    private String name;
}
