package com.example.distributedcommondatasource.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2018/8/9 10:04
 * description:
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@MappedSuperclass
public abstract class BaseIdEntity implements Serializable {
    /**
     * 主键
     */
    @Id
    protected Long id;

}
