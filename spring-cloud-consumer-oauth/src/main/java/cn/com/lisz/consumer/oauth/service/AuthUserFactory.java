package cn.com.lisz.consumer.oauth.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import cn.com.lisz.common.model.base.UserModel;
import cn.com.lisz.common.oauth.AuthUser;

public final class AuthUserFactory {

	private AuthUserFactory() {
	}

	public static AuthUser create(UserModel model) {
		return new AuthUser(model.getId(), model.getUsername(), model.getPassword(),
				mapToGrantedAuthorities(getPermission(model)));
	}

	private static List<GrantedAuthority> mapToGrantedAuthorities(List<String> authorities) {
		return authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
	}

	private static List<String> getPermission(UserModel model) {
		List<String> list = new ArrayList<String>();
		if (model.getRoles() != null) {
			model.getRoles().forEach(role -> {
				if (role.getPermissions() != null) {
					role.getPermissions().forEach(permission -> {
						list.add(permission.getCode());
					});
				}
			});
		}
		return list;
	}
}
