package cn.com.lisz.producer.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;

import cn.com.lisz.common.data.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_permission")
public class Permission extends BaseEntity {

	private Long pid;
	
	@Column(nullable = false, length = 30)
	private String code;

	@Column(nullable = false, length = 20)
	private String name;
	
	@Column(nullable = false, length = 1, columnDefinition = "char default 0 comment '权限类型: 0-系统, 1-模块, 2-接口'")
	private String type;
	
	private Integer sort;
	
}