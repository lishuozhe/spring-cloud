package cn.com.lisz.common.model.web;

import java.util.List;

import cn.com.lisz.common.model.oauth.UserModel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DelModel<ID> {

	private List<ID> ids;
	private UserModel userModel;
	
	
}
