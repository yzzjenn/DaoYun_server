package com.yzz.system.pojo;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@ApiModel(description = "学生课程表")
@Table(name = "course_student")
public class CourseStudent {
    @EmbeddedId
    private SignHistoryPrimaryKey id;

    Integer experience;  // 额外字段经验值存在联合主键，所以用@EmbeddedId 来标注

    public CourseStudent(SignHistoryPrimaryKey id, Integer experience) {
        this.id = id;
        this.experience = experience;
    }

    public CourseStudent() {

    }

    public SignHistoryPrimaryKey getId() {
        return id;
    }

    public void setId(SignHistoryPrimaryKey id) {
        this.id = id;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }
}

