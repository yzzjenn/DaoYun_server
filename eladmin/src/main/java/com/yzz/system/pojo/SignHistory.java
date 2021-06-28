package com.yzz.system.pojo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity

@Table(name = "sign_history")
public class SignHistory implements Serializable {

    public SignHistory(Long id) {
        this.id = id;
    }
    public SignHistory() {

    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "课程id")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    /**
     * 方便查询，没有实际显示
     **/
    @Column(name = "course_id", insertable = false, updatable = false)
    private Long courseId;


    @ApiModelProperty(value = "出勤人数")
    @Column(name = "attendance")
    private Integer attendance = 0;


    @ApiModelProperty(value = "缺勤人数")
    @Column(name = "absence")
    private Integer absence = 0;

    @ApiModelProperty(value = "状态")
    @Column(name = "status")
    private Boolean status = true;

    @ApiModelProperty(value = "班课编码")
    @Column(name = "code")
    private String code;

    @ApiModelProperty(value = "发起时间")
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public void copy(SignHistory source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Integer getAttendance() {
        return attendance;
    }

    public void setAttendance(Integer attendance) {
        this.attendance = attendance;
    }

    public Integer getAbsence() {
        return absence;
    }

    public void setAbsence(Integer absence) {
        this.absence = absence;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "SignHistory{" +
                "id=" + id +
                ", course=" + course.getCourseName() +
                ", code=" + code +
                ", attendance=" + code +
                ", absence=" + absence +
                ", createTime=" + createTime +
                '}';
    }
}

