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
@Entity(name = "sys_dict")
public class Dict extends BaseEntity {

	private Long pid;

	@Column(nullable = false, length = 10)
	private String value;

	@Column(nullable = false, length = 20)
	private String label;

	@Column(length = 10)
	private String code;

	@Column(length = 20)
	private String codeName;

	private Integer sort;

}