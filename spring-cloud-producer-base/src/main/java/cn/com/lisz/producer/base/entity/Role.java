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
@Entity(name = "sys_role")
public class Role extends BaseEntity {

	@Column(nullable = false, length = 10)
	private String code;

	@Column(nullable = false, length = 20)
	private String name;

}