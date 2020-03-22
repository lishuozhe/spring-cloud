package cn.com.lisz.consumer.remote.hystrix;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.lisz.common.model.BasePage;
import cn.com.lisz.consumer.entity.Dict;
import cn.com.lisz.consumer.remote.DictRemote;

@Component
public class DictRemoteHystrix implements DictRemote {

	@Override
	public Dict get(@RequestParam(value = "id") Long id) {
		return null;
	}

	@Override
	public Dict listOne(@RequestBody Dict record) {
		return null;
	}

	@Override
	public int save(@RequestBody Dict record) {
		return 0;
	}

	@Override
	public int delete(@RequestParam(value = "id") Long id) {
		return 0;
	}

	@Override
	public int update(@RequestBody Dict record) {
		return 0;
	}

	@Override
	public BasePage<Dict> page(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestBody Dict record) {
		return null;
	}

}