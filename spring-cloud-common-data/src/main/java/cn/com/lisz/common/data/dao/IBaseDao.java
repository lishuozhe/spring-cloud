package cn.com.lisz.common.data.dao;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import cn.com.lisz.common.data.dao.model.QueryCondition;
import cn.com.lisz.common.data.dao.model.QueryConditionItem;
import cn.com.lisz.common.data.dao.model.QueryRelationship;
import cn.com.lisz.common.data.entity.BaseEntity;
import cn.com.lisz.common.data.util.EntityUtils;
import cn.com.lisz.common.data.util.QueryUtils;
import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.model.KeyValuePair;
import cn.com.lisz.common.model.TripleFun;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.util.CollectionUtils;
import cn.com.lisz.common.util.StringUtils;

import javax.persistence.criteria.*;
import javax.transaction.Transactional;
import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * 通用映射类
 * 
 * @author lisz
 *
 * @param <T>
 * @param <ID><TEntity
 *            extends BaseEntity> extends JpaRepository<TEntity, String>,
 *            JpaSpecificationExecutor<TEntity>
 */
public interface IBaseDao<TEntity extends BaseEntity, ID extends Serializable>
		extends JpaRepository<TEntity, ID>, JpaSpecificationExecutor<TEntity> {

	default Predicate getPredicate(Class<?> entityType, List<RequestCondition> requestCondition, Path<?> join,
			CriteriaBuilder criteriaBuilder) {
		QueryCondition queryConditions = QueryUtils.getAndCondition(entityType, requestCondition);

		if (!CollectionUtils.isEmpty(queryConditions)) {
			AtomicReference<Predicate> whereRef = new AtomicReference<>();
			List<QueryConditionItem> queryItems = queryConditions.getConditionItems();

			queryItems.stream().filter(a -> QueryRelationship.In != a.getRelationship()).forEach(a -> {
				Expression<Boolean> where = whereRef.get();
				Predicate expression = QueryUtils.wrapQueryItem(join, criteriaBuilder, a);

				if (expression != null) {
					whereRef.set(where == null ? expression : criteriaBuilder.and(where, expression));
				}
			});

			queryItems.stream().filter(a -> QueryRelationship.In == a.getRelationship())
					.map(QueryConditionItem::getField).distinct().forEach(field -> {
						Expression<Boolean> where = whereRef.get();
						Predicate expression = QueryUtils.wrapInQueryItem(join, criteriaBuilder, queryItems, queryItems
								.stream().filter(item -> StringUtils.equals(field, item.getField())).findFirst().get());

						if (expression != null) {
							whereRef.set(where == null ? expression : criteriaBuilder.and(where, expression));
						}
					});

			return whereRef.get();
		}

		return null;
	}

	/**
	 * JPA检索对象取得
	 *
	 * @param entityType
	 *            实体类类型
	 * @param requestCondition
	 *            查询条件
	 * @param optionalUser
	 *            登录用户信息
	 * @param <TEntity>
	 * @return
	 */
	@SuppressWarnings("hiding")
	default <TEntity extends BaseEntity> Specification<TEntity> getSpecification(Class<TEntity> entityType,
			List<RequestCondition> requestCondition, boolean isDistinct) {
		QueryCondition queryConditions = QueryUtils.getAndCondition(entityType, requestCondition);
		if (!CollectionUtils.isEmpty(queryConditions)) {
			return (Specification<TEntity>) (root, query, criteriaBuilder) -> {
				AtomicReference<Expression<Boolean>> whereRef = new AtomicReference<>();
				List<QueryConditionItem> queryItems = queryConditions.getConditionItems();

				queryItems.stream().filter(a -> QueryRelationship.In != a.getRelationship()).forEach(a -> {
					Expression<Boolean> where = whereRef.get();
					Expression<Boolean> expression = QueryUtils.wrapQueryItem(root, criteriaBuilder, a);

					if (expression != null) {
						whereRef.set(where == null ? expression : criteriaBuilder.and(where, expression));
					}
				});

				queryItems.stream().filter(a -> QueryRelationship.In == a.getRelationship()).map(a -> a.getField())
						.distinct().forEach(field -> {
							Expression<Boolean> where = whereRef.get();
							Expression<Boolean> expression = QueryUtils.wrapInQueryItem(root, criteriaBuilder,
									queryItems,
									queryItems.stream().filter(item -> StringUtils.equals(field, item.getField()))
											.findFirst().get());

							if (expression != null) {
								whereRef.set(where == null ? expression : criteriaBuilder.and(where, expression));
							}
						});

				Expression<Boolean> whereExpression;
				if ((whereExpression = whereRef.get()) != null) {
					query.where(whereExpression).distinct(isDistinct);
				}
				return null;
			};
		}

		return null;
	}

	/**
	 * JPA检索对象取得
	 *
	 * @param entityType
	 *            实体类类型
	 * @param requestCondition
	 *            查询条件
	 * @param optionalUser
	 *            登录用户信息
	 * @param <TEntity>
	 * @return
	 */
	@SuppressWarnings("hiding")
	default <TEntity extends BaseEntity> Specification<TEntity> getSpecification(Class<TEntity> entityType,
			List<RequestCondition> requestCondition,
			TripleFun<Root<TEntity>, CriteriaQuery<?>, CriteriaBuilder, Predicate> extFilter) {
		QueryCondition queryConditions = QueryUtils.getAndCondition(entityType, requestCondition);

		if (!CollectionUtils.isEmpty(queryConditions)) {
			return (Specification<TEntity>) (root, query, criteriaBuilder) -> {
				Expression<Boolean> where = null;
				List<QueryConditionItem> queryItems = queryConditions.getConditionItems();

				Expression<Boolean> expression;
				for (int i = 0; i < queryItems.size(); i++) {
					if (where == null) {
						where = QueryUtils.wrapQueryItem(root, criteriaBuilder, queryItems.get(i));
					} else {
						expression = QueryUtils.wrapQueryItem(root, criteriaBuilder, queryItems.get(i));
						if (expression != null) {
							where = criteriaBuilder.and(where, expression);
						}
					}
				}

				List<String> inFields = queryItems.stream().filter(a -> QueryRelationship.In == a.getRelationship())
						.map(a -> a.getField()).distinct().collect(toList());
				for (int i = 0; i < inFields.size(); i++) {
					if (where == null) {
						where = QueryUtils.wrapInQueryItem(root, criteriaBuilder, queryItems, queryItems.get(i));
					} else {
						expression = QueryUtils.wrapInQueryItem(root, criteriaBuilder, queryItems, queryItems.get(i));
						if (expression != null) {
							where = criteriaBuilder.and(where, expression);
						}
					}
				}

				Predicate extFilterPredicate;
				if (extFilter != null
						&& (extFilterPredicate = extFilter.invoke(root, query, criteriaBuilder)) != null) {
					where = where == null ? extFilterPredicate : criteriaBuilder.and(where, extFilterPredicate);
				}

				if (where != null) {
					query.where(where);
				}
				return null;
			};
		}

		if (extFilter != null) {
			return (Specification<TEntity>) (root, query, criteriaBuilder) -> {
				Predicate extFilterPredicate;
				if ((extFilterPredicate = extFilter.invoke(root, query, criteriaBuilder)) != null) {
					query.where(extFilterPredicate);
				}

				return null;
			};
		}

		return null;
	}

	/**
	 * JPA检索对象取得(关联查询)
	 *
	 * @param entityType
	 *            实体类类型
	 * @param conditions
	 *            查询条件
	 * @return JPA检索对象
	 */
	default Specification<TEntity> getSpecification(Class<TEntity> entityType, List<RequestCondition> conditions) {
		Objects.requireNonNull(entityType);

		if (CollectionUtils.isEmpty(conditions)) {
			return null;
		}

		List<Field> relationFields = Stream.of(entityType.getDeclaredFields())
				.filter(field -> BaseEntity.class.isAssignableFrom(field.getType())).collect(toList());

		List<KeyValuePair<String, List<RequestCondition>>> relationConditions = relationFields.stream().map(field -> {
			String fieldName = field.getName();
			return new KeyValuePair<>(fieldName, conditions.stream().filter(a -> {
				String type;
				return !StringUtils.isEmpty((type = a.getType())) && type.startsWith(fieldName + QueryUtils.DOT);
			}).map(a -> new RequestCondition(a.getType().substring(fieldName.length() + 1), a.getValue()))
					.collect(toList()));
		}).collect(toList());

		List<RequestCondition> selfConditions = conditions.stream().filter(a -> {
			String type;
			return !StringUtils.isEmpty(type = a.getType())
					&& relationConditions.stream().noneMatch(i -> type.startsWith(i.getKey() + QueryUtils.DOT));
		}).collect(toList());

		return getSpecification(entityType, selfConditions, (root, query, criteriaBuilder) -> {
			List<Predicate> relationWhere = relationConditions.stream().map(a -> {
				Path<?> carownerJoin = root.join(a.getKey(), JoinType.LEFT);
				AtomicReference<Predicate> reference = new AtomicReference<>();

				relationFields.stream().filter(i -> StringUtils.equals(a.getKey(), i.getName())).map(Field::getType)
						.findFirst().ifPresent(relationType -> {
							reference.set(getPredicate(relationType, a.getValue(), carownerJoin, criteriaBuilder));
						});

				return reference.get();
			}).collect(toList());

			return QueryUtils.prepareCondition(criteriaBuilder, relationWhere);
		});
	}

	default boolean exist(Class<TEntity> entityType, String id) {
		return 0 < count(entityType, id);
	}

	default boolean exist(Class<TEntity> entityType, List<RequestCondition> conditions) {
		return 0 < count(entityType, conditions);
	}

	default long count(Class<TEntity> entityType, String id) {
		return count(entityType, Collections.singletonList(new RequestCondition(EntityUtils.FIELD_ID, id)));
	}

	default long count(Class<TEntity> entityType, List<RequestCondition> conditions) {
		return count(getSpecification(entityType, conditions, true));
	}

	default boolean remove(Class<TEntity> entityType, List<RequestCondition> conditions) {
		List<TEntity> entities = findAll(getSpecification(entityType, conditions, true));

		if (!CollectionUtils.isEmpty(entities)) {
			deleteAll(entities);
			return true;
		}

		return false;
	}

	default Optional<TEntity> getEntity(Class<TEntity> entityType, List<RequestCondition> conditions) {
		Objects.requireNonNull(entityType, "实体类类型不能为NULL");

		return findOne(getSpecification(entityType, conditions));
	}

	default <TModel extends BaseModel> TModel get(Class<TModel> modelType, ID id) {
		return get(id, a -> EntityUtils.mapping(modelType, a));
	}

	default <TModel extends BaseModel> TModel get(ID id, Function<TEntity, TModel> mapper) {
		Objects.requireNonNull(mapper);

		Optional<TEntity> optionalEntity;
		return (optionalEntity = findById(id)).isPresent() ? mapper.apply(optionalEntity.get()) : null;
	}

	default <TModel extends BaseModel> TModel get(Class<TModel> modelType, Specification<TEntity> spec) {
		return get(spec, a -> EntityUtils.mapping(modelType, a));
	}

	default <TModel extends BaseModel> TModel get(Specification<TEntity> spec, Function<TEntity, TModel> mapper) {
		Objects.requireNonNull(spec);
		Objects.requireNonNull(mapper);

		Optional<TEntity> optionalEntity = null;
		return (optionalEntity = findOne(spec)).isPresent() ? mapper.apply(optionalEntity.get()) : null;
	}

	default <TModel extends BaseModel> Optional<TModel> get(Class<TEntity> entityType,
			List<RequestCondition> conditions, Function<TEntity, TModel> mapper) {
		Objects.requireNonNull(entityType, "实体类型不能为NULL");
		Objects.requireNonNull(mapper, "类型转换处理不能为NULL");

		return findOne(getSpecification(entityType, conditions, true)).map(mapper);
	}

	/**
	 * 取得实体列表
	 *
	 * @param entityType
	 *            实体类类型
	 * @param conditions
	 *            查询条件
	 * @param user
	 *            权限过滤用用户信息
	 * @return
	 */
	default List<TEntity> getEntityList(Class<TEntity> entityType, List<RequestCondition> conditions) {
		Objects.requireNonNull(entityType, "实体类型不能为NULL");

		return findAll(getSpecification(entityType, conditions));
	}

	/**
	 * 取得模型列表
	 *
	 * @param entityType
	 *            实体类类型
	 * @param conditions
	 *            查询条件
	 * @param mapper
	 *            映射转换处理
	 * @param user
	 *            权限过滤用用户信息
	 * @param <TModel>
	 * @return
	 */
	default <TModel extends BaseModel> List<TModel> getList(Class<TEntity> entityType,
			List<RequestCondition> conditions, Function<TEntity, TModel> mapper) {
		Objects.requireNonNull(entityType, "实体类型不能为NULL");
		Objects.requireNonNull(mapper, "类型转换处理不能为NULL");

		return findAll(getSpecification(entityType, conditions)).stream().map(mapper).collect(toList());
	}

	default <TModel extends BaseModel> List<TModel> getList(Class<TModel> modelType, Specification<TEntity> spec) {
		Objects.requireNonNull(modelType);
		Objects.requireNonNull(spec);
		return getList(spec, a -> EntityUtils.mapping(modelType, a));
	}

	default <TModel extends BaseModel> List<TModel> getList(Specification<TEntity> spec,
			Function<TEntity, TModel> mapper) {
		Objects.requireNonNull(spec);
		Objects.requireNonNull(mapper);

		return findAll(spec).stream().map(mapper).collect(toList());
	}

	/**
	 * 事务保存数据
	 *
	 * @param entities
	 *            实体列表
	 * @return 保存的数据
	 */
	@Transactional(rollbackOn = Exception.class)
	default List<TEntity> saveWithTransaction(List<TEntity> entities) {
		return saveAll(entities);
	}

}
