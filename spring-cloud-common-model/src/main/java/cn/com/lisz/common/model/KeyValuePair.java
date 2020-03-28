package cn.com.lisz.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class KeyValuePair<TKey, TValue> {

	private TKey key;

	private TValue value;
	
}
