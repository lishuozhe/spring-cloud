package cn.com.lisz.consumer.admin.remote;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.lisz.common.model.BasePage;
import cn.com.lisz.consumer.admin.entity.Dict;
import cn.com.lisz.consumer.admin.remote.hystrix.DictRemoteHystrix;

@FeignClient(name = "spring-cloud-producer-base", fallback = DictRemoteHystrix.class)
public interface DictRemote {

	@RequestMapping(value = "/dict/get")
	public Dict get(@RequestParam(value = "id") Long id);

	@RequestMapping(value = "/dict/listOne", method = RequestMethod.POST)
	public Dict listOne(@RequestBody Dict record);

	@RequestMapping(value = "/dict/save", method = RequestMethod.POST)
	public int save(@RequestBody Dict record);

	@RequestMapping(value = "/dict/delete", method = RequestMethod.POST)
	public int delete(@RequestParam(value = "id") Long id);

	@RequestMapping(value = "/dict/update", method = RequestMethod.POST)
	public int update(@RequestBody Dict record);

	@RequestMapping(value = "/dict/page", method = RequestMethod.POST)
	public BasePage<Dict> page(@RequestParam(value = "pageSize", defaultValue = "5") Integer pageSize,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum, @RequestBody Dict record);
}
