package cn.com.lisz.common.data.util;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

import cn.com.lisz.common.data.dao.IBaseDao;
import cn.com.lisz.common.data.entity.BaseEntity;
import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.model.web.PaggingModel;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.util.CollectionUtils;
import cn.com.lisz.common.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

import static java.util.stream.Collectors.toList;

public class PaggingUtils {
	/**
	 * 默认分页显示数
	 */
	private static final int PAGE_SIZE = 20;

	private static final String KEY_ASC_ORDER = "asc_order";
	private static final String KEY_DESC_ORDER = "desc_order";

	/**
	 * 取得分页
	 *
	 * @param dao
	 *            数据操作类
	 * @param mapper
	 *            对象映射
	 * @param page
	 *            页码
	 * @param size
	 *            分页尺寸
	 * @param <TEntity>
	 * @param <TModel>
	 * @return
	 */
	public static <TEntity extends BaseEntity, TModel extends BaseModel> PaggingModel<TModel> getPagging(
			JpaRepository<TEntity, String> dao, Function<TEntity, TModel> mapper, int page, int size) {
		return getPagging(dao, mapper, page, size, null, false);
	}

	/**
	 * 取得分页
	 *
	 * @param dao
	 *            数据操作类
	 * @param mapper
	 *            对象映射
	 * @param page
	 *            页码
	 * @param size
	 *            分页尺寸
	 * @param entityType
	 *            实体类类型
	 * @param isAsc
	 *            是否正序
	 * @param <TEntity>
	 * @param <TModel>
	 * @return
	 */
	public static <TEntity extends BaseEntity, TModel extends BaseModel> PaggingModel<TModel> getPagging(
			JpaRepository<TEntity, String> dao, Function<TEntity, TModel> mapper, int page, int size,
			Class<TEntity> entityType, boolean isAsc) {
		if (dao == null || mapper == null) {
			return new PaggingModel<TModel>();
		}
		page -= 1;
		if (page < 1) {
			page = 0;
		}
		if (size < 1) {
			size = PAGE_SIZE;
		}
		Sort sort = null;
		if (entityType != null) {
			String orderBy = EntityUtils.getEntityOrderFieldName(entityType);
			sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);
		}

		PageRequest pageRequest = sort == null ? PageRequest.of(page, size) : PageRequest.of(page, size, sort);
		Page<TModel> pageInfo = dao.findAll(pageRequest).map(mapper);
		List<TModel> models = pageInfo.getContent();

		return new PaggingModel<TModel>(page + 1, size, pageInfo.getTotalElements(), pageInfo.getTotalPages(), models);
	}

	/**
	 * 取得分页
	 *
	 * @param dao
	 *            数据操作类
	 * @param mapper
	 *            对象映射
	 * @param entityType
	 *            实体类类型
	 * @param conditions
	 *            请求条件列表
	 * @param page
	 *            页码
	 * @param size
	 *            分页尺寸
	 * @param isAsc
	 *            是否正序
	 * @param <TEntity>
	 * @param <TModel>
	 * @return
	 */

	public static <TEntity extends BaseEntity, TModel extends BaseModel> PaggingModel<TModel> getPagging(
			IBaseDao<TEntity, ?> dao, Function<TEntity, TModel> mapper, Class<TEntity> entityType,
			List<RequestCondition> conditions, int page, int size, boolean isAsc) {
		if (dao == null || mapper == null || entityType == null) {
			return new PaggingModel<TModel>();
		}
		page -= 1;
		if (page < 0) {
			page = 0;
		}
		if (size < 1) {
			size = PAGE_SIZE;
		}

		Sort sort = null;
		List<RequestCondition> orderConditions;
		if (!CollectionUtils.isEmpty(conditions)
				&& !CollectionUtils.isEmpty(orderConditions = conditions.stream().filter(a -> {
					return StringUtils.equalsIgnoreCase(a.getType(), KEY_ASC_ORDER, KEY_DESC_ORDER)
							&& !StringUtils.isEmpty(a.getValue());
				}).collect(toList()))) {
			List<Sort.Order> orders = new ArrayList<>();

			orderConditions.forEach(a -> {
				if (EntityUtils.existEntryField(entityType, a.getValue().toString())) {
					orders.add(KEY_ASC_ORDER.equalsIgnoreCase(a.getType()) ? Sort.Order.asc(a.getValue().toString())
							: Sort.Order.desc(a.getValue().toString()));
				}
			});

			if (0 < orders.size()) {
				sort = Sort.by(orders);
			}
		}

		if (sort == null) {
			String orderBy = EntityUtils.getEntityOrderFieldName(entityType);
			sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);
		}

		Specification<TEntity> specification = dao.getSpecification(entityType, conditions, true);

		Pageable pageRequest = PageRequest.of(page, size, sort);
		Page<TModel> pageInfo = dao.findAll(specification, pageRequest).map(mapper);

		return new PaggingModel<TModel>(page + 1, size, pageInfo.getTotalElements(), pageInfo.getTotalPages(),
				pageInfo.getContent());
	}

	public static <TEntity extends BaseEntity, TModel extends BaseModel> PaggingModel<TModel> getPagging(
			IBaseDao<TEntity, ?> dao, Function<TEntity, TModel> mapper, Class<TEntity> entityType,
			Specification<TEntity> specification, int page, int size, boolean isAsc) {
		if (dao == null || mapper == null || entityType == null || specification == null) {
			return new PaggingModel<TModel>();
		}

		page -= 1;
		if (page < 0) {
			page = 0;
		}
		if (size < 1) {
			size = PAGE_SIZE;
		}
		String orderBy = EntityUtils.getEntityOrderFieldName(entityType);
		Sort sort = Sort.by(isAsc ? Sort.Direction.ASC : Sort.Direction.DESC, orderBy);

		Pageable pageRequest = PageRequest.of(page, size, sort);
		Page<TModel> pageInfo = null;
		try {
			pageInfo = dao.findAll(specification, pageRequest).map(mapper);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return new PaggingModel<TModel>(page + 1, size, pageInfo.getTotalElements(), pageInfo.getTotalPages(),
				pageInfo.getContent());
	}
}
