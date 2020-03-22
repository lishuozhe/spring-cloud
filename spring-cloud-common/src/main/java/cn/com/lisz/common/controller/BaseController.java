package cn.com.lisz.common.controller;

import java.io.Serializable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.service.IBaseService;

public class BaseController<T extends BaseModel, ID extends Serializable, TService extends IBaseService<T, ID, ?>> {

	@Autowired
	private IBaseService<T, ID, ?> baseService;

	/**
	 * 查看
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/get", method = RequestMethod.GET)
	public T get(@RequestParam ID id) {
		return baseService.get(id);
	}

	/**
	 * 删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "/delete", method = RequestMethod.POST)
	public int delete(@RequestParam ID id) {
		return baseService.delete(id);
	}

	/**
	 * 保存
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/save", method = RequestMethod.POST)
	public int save(@RequestBody T record) {
		return baseService.save(record);
	}

	/**
	 * 更新
	 * 
	 * @param record
	 * @return
	 */
	@RequestMapping(value = "/update", method = RequestMethod.POST)
	public int update(@RequestBody T record) {
		return baseService.update(record);
	}

}
