package com.example.distributedbook.entity;

import com.example.distributedcommondatasource.entity.BaseIdEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;


/**
 * Created with IntelliJ IDEA.
 *
 * @author zhang tong
 * date: 2020/12/21 17:17
 * description:
 */
@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity
@Table(name = "t_book")
public class Book  {
	/**
	 * 主键
	 */
	@Id
	protected Long id;
	/**
	*
	*/
	private String bookName;
	/**
	* 用户id
	*/
	private Long userId;
	/**
	* 创建时间
	*/
	private LocalDateTime createdTime;
	/**
	* 删除状态：1已删除 0未删除
	*/
	private Integer deleteState;

}
