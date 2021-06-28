package com.yzz.system.pojo;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yzz.base.BaseEntity;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Set;


@Entity
@Table(name = "student")
public class Student extends BaseEntity implements Serializable {

    public Student(Long id) {
        this.id = id;
    }

    public Student() {

    }
    /**
     * 学生id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ApiModelProperty(value = "姓名")
    @Column(name = "name", nullable = false)
//    @NotBlank
    private String name;

    @ApiModelProperty(value = "学号")
    @Column(name = "student_number", nullable = false)
//    @NotBlank
    private String studentNumber;

    @ApiModelProperty(value = "创建日期")
    @Column(name = "create_time")
    @CreationTimestamp
    private Timestamp createTime;

    private String password;

    @JsonIgnore
    @ApiModelProperty(value = "班课")
    @ManyToMany(mappedBy = "students")
    private Set<Course> courses;


    @ApiModelProperty(value = "院校")
    @OneToOne
    @JoinColumn(name = "college_id")
    private Dept college;

    @ApiModelProperty(value = "邮箱")
    @Column(name = "email", nullable = false)
//    @NotBlank
    private String email;

    /**
     * 状态：1启用、0禁用
     */
    @Column(name = "enabled")
    private Boolean enabled;

    @ApiModelProperty(value = "手机号")
    @Column(name = "phone", nullable = false)
    @NotBlank
    private String phone;

    @ApiModelProperty(value = "最后修改密码时间")
    @Column(name = "last_password_reset_time")
    private Timestamp lastPasswordResetTime;

    @ApiModelProperty(value = "性别")
    @Column(name = "sex")
    private String sex = "男";

    public void copy(Student source) {
        BeanUtil.copyProperties(source, this, CopyOptions.create().setIgnoreNullValue(true));
    }

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

    public String getStudentNumber() {
        return studentNumber;
    }

    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Course> getCourses() {
        return courses;
    }

    public void setCourses(Set<Course> courses) {
        this.courses = courses;
    }

    public Dept getCollege() {
        return college;
    }

    public void setCollege(Dept college) {
        this.college = college;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Timestamp getLastPasswordResetTime() {
        return lastPasswordResetTime;
    }

    public void setLastPasswordResetTime(Timestamp lastPasswordResetTime) {
        this.lastPasswordResetTime = lastPasswordResetTime;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


}
