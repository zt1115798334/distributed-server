package com.example.distributedcommondatasource.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/9 10:04
 * description:统一定义id的entity基类.
 * *
 * * 基类统一定义id的属性名称、数据类型、列名映射及生成策略.
 * * Oracle需要每个Entity独立定义id的SEQUCENCE时，不继承于本类而改为实现一个Idable的接口。
 */
// JPA 基类的标识
@Setter
@Getter
@MappedSuperclass
public abstract class BasePageEntity implements Serializable {

    /**
     * 需要排序的字段
     */
    @Transient
    protected String sortName;

    /**
     * 排序类型
     */
    @Transient
    protected String sortOrder;

    /**
     * 页数
     */
    @Transient
    protected int pageNumber = 1;

    /**
     * 每页显示数量
     */
    @Transient
    protected int pageSize = 10;

    /**
     * 时间类型
     */
    @Transient
    private String timeType;

    /**
     * 开始时间 字符串
     */
    @Transient
    protected String startTimeStr;

    /**
     * 结束时间 字符串
     */
    @Transient
    protected String endTimeStr;

    /**
     * 开始日期
     */
    @Transient
    protected LocalDate startDate;

    /**
     * 开始日期
     */
    @Transient
    protected LocalDate endDate;

    /**
     * 开始时间
     */
    @Transient
    protected LocalDateTime startDateTime;

    /**
     * 结束时间
     */
    @Transient
    protected LocalDateTime endDateTime;



}
