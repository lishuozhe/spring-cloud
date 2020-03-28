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
public class EditModel<TModel> {

	private TModel model;
	
	private UserModel userModel;
	
	
	
}
