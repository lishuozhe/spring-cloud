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
@Entity(name = "sys_user")
public class User extends BaseEntity {

	@Column(length = 20, nullable = false)
	private String username;

	@Column(length = 50)
	private String email;

	@Column(length = 20)
	private String mobile;

	@Column(length = 64, nullable = false)
	private String password;

	@Column(length = 64)
	private String authUsername;

	@Column(length = 100)
	private String authPassword;

}