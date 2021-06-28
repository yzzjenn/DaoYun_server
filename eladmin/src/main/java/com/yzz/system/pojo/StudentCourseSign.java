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
@Table(name = "student_course_sign")
public class StudentCourseSign implements Serializable {

    public StudentCourseSign(Long signHistoryId, Long studentId, Boolean attendance, Boolean replenish) {
        this.signHistory = new SignHistory(signHistoryId);
        this.student = new Student(studentId);
        this.attendance = attendance;
        this.replenish = replenish;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @OneToOne
    @ApiModelProperty(value = "签到历史")
    @JoinColumn(name = "sign_history_id")
    private SignHistory signHistory;

    /**
     * 方便查询，没有实际显示
     **/
    @Column(name = "sign_history_id", insertable = false, updatable = false)
    private Long signId;

    @OneToOne
    @ApiModelProperty(value = "学生")
    @JoinColumn(name = "student_id")
    private Student student;

    /**
     * 方便查询，没有实际显示
     **/
    @Column(name = "student_id", insertable = false, updatable = false)
    private Long studentId;

    @ApiModelProperty(value = "是否出勤")
    private Boolean attendance = true;

    @ApiModelProperty(value = "是否补签")
    private Boolean replenish = false;

    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    public StudentCourseSign() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public SignHistory getSignHistory() {
        return signHistory;
    }

    public void setSignHistory(SignHistory signHistory) {
        this.signHistory = signHistory;
    }

    public Long getSignId() {
        return signId;
    }

    public void setSignId(Long signId) {
        this.signId = signId;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Boolean getAttendance() {
        return attendance;
    }

    public void setAttendance(Boolean attendance) {
        this.attendance = attendance;
    }

    public Boolean getReplenish() {
        return replenish;
    }

    public void setReplenish(Boolean replenish) {
        this.replenish = replenish;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public void copy(StudentCourseSign source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }
}
