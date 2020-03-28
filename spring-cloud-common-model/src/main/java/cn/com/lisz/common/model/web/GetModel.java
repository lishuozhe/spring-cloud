package cn.com.lisz.common.model.web;

import cn.com.lisz.common.model.oauth.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetModel<ID> {

	private ID id;
	private UserModel userModel;
	
	
}
