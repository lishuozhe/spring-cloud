package cn.com.lisz.consumer.admin.remote;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.lisz.common.model.base.PermissionModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.consumer.admin.remote.hystrix.PermissionRemoteHystrix;

@FeignClient(name = "spring-cloud-producer-base", path = "/permission", fallback = PermissionRemoteHystrix.class)
public interface PermissionRemote {

	/**
	 * 新增
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public Long add(@RequestBody PermissionModel model);

	/**
	 * 删除
	 *
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "/del", method = RequestMethod.POST)
	public boolean del(@RequestBody List<RequestCondition> conditions);

	/**
	 * 更新
	 *
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/edit", method = RequestMethod.POST)
	public boolean edit(@RequestBody PermissionModel model);

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public PermissionModel get(@RequestParam(value = "id") Long id);

	/**
	 * 条件查看
	 * 
	 * @param conditions
	 * @return
	 */
	@RequestMapping(value = "/findOne", method = RequestMethod.POST)
	public PermissionModel findOne(@RequestBody List<RequestCondition> conditions);

	/**
	 * 判断存在
	 * 
	 * @param conditions
	 * @return
	 */
	@RequestMapping(value = "/exist", method = RequestMethod.POST)
	public boolean exist(@RequestBody List<RequestCondition> conditions);

	/**
	 * 条件查询
	 * 
	 * @param conditions
	 * @return
	 */
	@RequestMapping(value = "/list", method = RequestMethod.POST)
	public List<PermissionModel> list(@RequestBody List<RequestCondition> conditions);

	/**
	 * 分页查询
	 * 
	 * @param conditions
	 * @return
	 */
	@RequestMapping(value = "/page", method = RequestMethod.POST)
	public PaggingModel<PermissionModel> page(@RequestParam(value = "pageSize", defaultValue = "5") Integer size,
			@RequestParam(value = "pageNum", defaultValue = "1") Integer page,
			@RequestBody List<RequestCondition> conditions);
}
