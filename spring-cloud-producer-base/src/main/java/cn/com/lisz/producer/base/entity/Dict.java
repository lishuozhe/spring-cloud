package cn.com.lisz.producer.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;

import cn.com.lisz.common.data.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_dict")
public class Dict extends BaseEntity {

	private Long pid;

	private String value;

	private String label;

	private String type;

	private String description;

	private Integer sort;

}