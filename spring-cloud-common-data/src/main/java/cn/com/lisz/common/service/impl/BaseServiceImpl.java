package cn.com.lisz.common.service.impl;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import cn.com.lisz.common.data.dao.IBaseDao;
import cn.com.lisz.common.data.entity.BaseEntity;
import cn.com.lisz.common.data.util.EntityUtils;
import cn.com.lisz.common.data.util.PaggingUtils;
import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.service.IBaseService;
import cn.com.lisz.common.util.CollectionUtils;
import cn.com.lisz.common.util.StringUtils;

/**
 * service 基类
 * 
 * @author lisz
 *
 * @param <TEntity>
 * @param <ID>
 * @param <TModel>
 * @param <TDao>
 */
public class BaseServiceImpl<TEntity extends BaseEntity, ID extends Serializable, TModel extends BaseModel, TDao extends IBaseDao<TEntity, ID>>
		implements IBaseService<TEntity, ID, TModel, TDao> {

	private static final int INDEX_ENTITY = 0;
	private static final int INDEX_VIEWMODEL = 2;
	private static final int PARAM_COUNT_GENERIC = 4;

	protected Logger logger;

	public BaseServiceImpl() {
		logger = LoggerFactory.getLogger(this.getClass());
	}

	/**
	 * 数据访问类
	 */
	@Autowired
	protected TDao dao;

	private Type[] genericTypes;

	private void initGenericTypes() {
		if (this.genericTypes == null || this.genericTypes.length == 0) {
			Type type = getClass().getGenericSuperclass();

			if (type instanceof ParameterizedType) {
				this.genericTypes = ((ParameterizedType) type).getActualTypeArguments();
			}
		}
	}

	@SuppressWarnings("unchecked")
	private Class<TModel> getModelType() {
		initGenericTypes();

		if (this.genericTypes.length == PARAM_COUNT_GENERIC) {
			try {
				return (Class<TModel>) this.genericTypes[INDEX_VIEWMODEL];
			} catch (Exception e) {

			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	private Class<TEntity> getEntityType() {
		initGenericTypes();

		if (this.genericTypes.length == PARAM_COUNT_GENERIC) {
			try {
				return (Class<TEntity>) this.genericTypes[INDEX_ENTITY];
			} catch (Exception e) {

			}
		}

		return null;
	}

	@SuppressWarnings("unchecked")
	@Override
	public ID add(TModel model) {
		Assert.notNull(model, "model could't be null");

		try {
			TEntity entity = EntityUtils.mapping(getEntityType(), model);

			if (entity == null) {
				return null;
			}

			dao.save(entity);

			return (ID) entity.getId();
		} catch (Exception e) {
			logger.error(String.format("Add data by error, model: %s", EntityUtils.getEntryInfo(model)), e);
		}

		return null;
	}

	@Override
	public boolean delete(List<RequestCondition> conditions) {
		if (!CollectionUtils.isEmpty(conditions)) {
			try {
				Iterable<TEntity> entities = dao.getEntityList(getEntityType(), conditions);
				entities.forEach(a -> {
					a.setDelFlag("1");
					a.setUpdateBy(a.getCreateBy());
					a.setUpdateTime(new Date());
				});
				return this.dao.saveAll(entities) != null;
			} catch (Exception e) {
				logger.error(String.format("数据删除失败, ids:%s", conditions), e);
			}
		}

		return false;

	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean edit(TModel model) {
		Objects.requireNonNull(model, "model could't be null");

		AtomicReference<TEntity> reference = new AtomicReference<>();
		ID id = (ID) model.getId();

		if (!StringUtils.isEmpty(id)) {
			try {
				dao.findById(id).ifPresent(entity -> {
					if (entity.getCreateBy() == null || model.getUpdateBy() == null) {
						EntityUtils.clone(model, entity);
						reference.set(dao.save(entity));
					} else {
						if (entity.getCreateBy().equals(model.getUpdateBy())) {
							EntityUtils.clone(model, entity);
							reference.set(dao.save(entity));
						}
					}
				});
			} catch (Exception e) {
				logger.error(String.format("编辑处理异常, model:%s", EntityUtils.getEntryInfo(model)), e);
			}
		}

		return reference.get() != null;
	}

	@Override
	public TModel get(ID id) {
		return dao.get(getModelType(), id);
	}

	@Override
	public Optional<TModel> get(List<RequestCondition> conditions) {
		return dao.get(getEntityType(), conditions, a -> EntityUtils.mapping(getModelType(), a));
	}

	@Override
	public boolean exist(List<RequestCondition> conditions) {
		if (CollectionUtils.isEmpty(conditions)) {
			return false;
		}
		return dao.exist(getEntityType(), conditions);
	}

	@Override
	public List<TModel> list(List<RequestCondition> conditions) {
		try {

			return dao.getList(getEntityType(), conditions, a -> EntityUtils.mapping(getModelType(), a));

		} catch (Exception e) {
			logger.error(String.format("数据列表取得失败, conditions:%s", conditions), e);
		}

		return new ArrayList<>();
	}

	@Override
	public PaggingModel<TModel> pagging(List<RequestCondition> conditions, int page, int size) {
		logger.info(String.format("Pagging, conditions:%s, page:%s, size:%s", EntityUtils.getEntryInfo(conditions),
				page, size));
		return PaggingUtils.getPagging(dao, a -> EntityUtils.mapping(getModelType(), a), getEntityType(), conditions,
				page, size, false);
	}

}
