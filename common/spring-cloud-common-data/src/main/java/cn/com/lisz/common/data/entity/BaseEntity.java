package cn.com.lisz.common.data.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.MappedSuperclass;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.Getter;
import lombok.Setter;

/**
 * 实体基类
 * 
 * @author lisz
 *
 */
@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class BaseEntity {

	// 删除状态
	public static final String FINAL_DELETE = "1";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	protected Long id;

	protected Long platformId;
	
	@Column(length = 20)
	protected String createBy;

	@CreatedDate
	protected Date createTime;
	
	@Column(length = 20)
	protected String updateBy;

	@LastModifiedDate
	protected Date updateTime;

	@Column(length = 200)
	protected String remarks;

	@Column(nullable = false, insertable = false, length = 1, columnDefinition = "char default 0 comment '删除状态: 0-否, 1-是'")
	protected String delFlag;

}
