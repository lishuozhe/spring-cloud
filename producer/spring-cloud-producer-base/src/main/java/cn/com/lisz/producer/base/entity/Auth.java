package cn.com.lisz.producer.base.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;

import cn.com.lisz.common.data.entity.BaseEntity;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "sys_auth")
public class Auth extends BaseEntity {

	@Column(length = 64)
	private String username;
	
	@Column(length = 100)
	private String password;
	
	@ManyToMany
	@JoinTable(name = "sys_auth_role", joinColumns = @JoinColumn(name = "auth_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private List<Role> roles;
}