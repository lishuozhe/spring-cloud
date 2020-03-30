package cn.com.lisz.common.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import lombok.Getter;
import lombok.Setter;

/**
 * 通用基类
 * 
 * @author lisz
 *
 */
@Getter
@Setter
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {

	// 删除状态
	public static final String FINAL_DELETE = "1";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected Long platformId;

	protected Long createBy;

	protected Date createTime;

	protected Long updateBy;

	protected Date updateTime;

	@Column(length = 200)
	protected String remarks;

	@Column(nullable = false, length = 1, columnDefinition = "char default 0")
	protected String delFlag;

}
