package cn.com.lisz.common.data.util;

import cn.com.lisz.common.data.dao.model.QueryCondition;
import cn.com.lisz.common.data.dao.model.QueryConditionItem;
import cn.com.lisz.common.data.dao.model.QueryConditionType;
import cn.com.lisz.common.data.dao.model.QueryRelationship;
import cn.com.lisz.common.data.entity.BaseEntity;
import cn.com.lisz.common.model.web.RequestCondition;
import cn.com.lisz.common.util.CollectionUtils;
import cn.com.lisz.common.util.StringUtils;
import cn.com.lisz.common.util.TypeUtils;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static java.util.stream.Collectors.toList;

/**
 * 查询条件工具类
 * 
 * @author lisz
 *
 */
public class QueryUtils {

	public static final String DOT = ".";
	public static final String INTERVAL = "_";
	public static final String NOT_EQUAL_KEY = "neq";
	public static final String GREAT_KEY = "gt";
	public static final String LESS_KEY = "lt";
	public static final String LIKE_KEY = "like";
	public static final String IN_KEY = "in";
	public static final String NULL_KEY = "null";
	public static final String NOT_NULL_KEY = "notnull";
	public static final String START_KEY = "start";
	public static final String END_KEY = "end";
	public static final String EMPTY_KEY = "empty";
	public static final String NOT_EMPTY_KEY = "notempty";

	public static final String FORMAT_CONDITION = "%s%s%s";

	public static <TEntity extends BaseEntity> Optional<QueryConditionItem> getConditionItem(Class<TEntity> entityType,
			String key, String value) {
		String[] pairs;
		Field entityField = null;
		QueryRelationship relationship = null;

		if (key.contains(INTERVAL) && 1 < (pairs = key.split(INTERVAL)).length && !StringUtils.isEmpty(pairs[0])
				&& !StringUtils.isEmpty(pairs[1])) {
			entityField = EntityUtils.getEntityField(entityType, pairs[1]);
			switch (pairs[0].toLowerCase()) {
			case NOT_EQUAL_KEY:
				if (!StringUtils.isEmpty(value)) {
					relationship = QueryRelationship.NotEquals;
				}
				break;
			case GREAT_KEY:
				if (!StringUtils.isEmpty(value)) {
					relationship = QueryRelationship.Great;
				}
				break;
			case LESS_KEY:
				if (!StringUtils.isEmpty(value)) {
					relationship = QueryRelationship.Less;
				}
				break;
			case LIKE_KEY:
				if (!StringUtils.isEmpty(value)) {
					relationship = QueryRelationship.Like;
				}
				break;
			case NULL_KEY:
				relationship = QueryRelationship.Null;
				break;
			case NOT_NULL_KEY:
				relationship = QueryRelationship.NotNull;
				break;
			case START_KEY:
				relationship = QueryRelationship.Start;
				break;
			case END_KEY:
				relationship = QueryRelationship.End;
				break;
			case EMPTY_KEY:
				relationship = QueryRelationship.Empty;
				break;
			case NOT_EMPTY_KEY:
				relationship = QueryRelationship.NotEmpty;
				break;
			default:
				break;
			}
		} else if (!StringUtils.isEmpty(value)) {
			relationship = QueryRelationship.Equals;
			entityField = EntityUtils.getEntityField(entityType, key);
		}

		if (relationship != null && entityField != null) {
			Object conditionValue = TypeUtils.getValue(entityField.getType(), value);
			return Optional.of(new QueryConditionItem(relationship, entityField.getName(), conditionValue));
		}

		return Optional.empty();
	}

	public static <TEntity extends BaseEntity> QueryCondition getAndCondition(Class<?> entityType,
			List<RequestCondition> reqConditions) {
		List<RequestCondition> conditions = reqConditions == null ? new ArrayList<>()
				: reqConditions.stream().filter(a -> a != null && !StringUtils.isEmpty(a.getType())).collect(toList());
		// 过滤删除条件
		conditions.add(new RequestCondition(EntityUtils.FIELD_DELETE, "0"));
		// Query
		List<QueryConditionItem> queryItems = conditions.stream().map(a -> {
			String[] pairs;
			String type = a.getType();
			Object conditionValue = a.getValue();
			Field entityField = null;
			QueryRelationship relationship = null;

			if (type.contains(INTERVAL) && 1 < (pairs = type.split(INTERVAL)).length && !StringUtils.isEmpty(pairs[0])
					&& !StringUtils.isEmpty(pairs[1])) {
				entityField = EntityUtils.getEntityField(entityType, pairs[1]);
				switch (pairs[0].toLowerCase()) {
				case NOT_EQUAL_KEY:
					if (!StringUtils.isEmpty(a.getValue())) {
						relationship = QueryRelationship.NotEquals;
					}
					break;
				case GREAT_KEY:
					if (!StringUtils.isEmpty(a.getValue())) {
						relationship = QueryRelationship.Great;
					}
					break;
				case LESS_KEY:
					if (!StringUtils.isEmpty(a.getValue())) {
						relationship = QueryRelationship.Less;
					}
					break;
				case LIKE_KEY:
					if (!StringUtils.isEmpty(a.getValue())) {
						relationship = QueryRelationship.Like;
					}
					break;
				case NULL_KEY:
					relationship = QueryRelationship.Null;
					break;
				case NOT_NULL_KEY:
					relationship = QueryRelationship.NotNull;
					break;
				case START_KEY:
					relationship = QueryRelationship.Start;
					break;
				case END_KEY:
					relationship = QueryRelationship.End;
					break;
				case EMPTY_KEY:
					relationship = QueryRelationship.Empty;
					break;
				case NOT_EMPTY_KEY:
					relationship = QueryRelationship.NotEmpty;
					break;
				default:
					break;
				}
			} else if (!StringUtils.isEmpty(a.getValue())) {
				relationship = QueryRelationship.Equals;
				entityField = EntityUtils.getEntityField(entityType, type);
			}

			if (relationship != null && entityField != null) {
				Object value = TypeUtils.getValue(entityField.getType(), conditionValue);
				return new QueryConditionItem(relationship, entityField.getName(), value);
			}

			return null;
		}).filter(Objects::nonNull).collect(toList());

		// In Query
		List<QueryConditionItem> inQueryItems = conditions == null ? new ArrayList<>() : conditions.stream().map(a -> {
			String[] pairs;
			String type = a.getType();
			Object conditionValue = a.getValue();
			Field entityField;

			if (type.toLowerCase().startsWith(IN_KEY + INTERVAL) && 1 < (pairs = type.split(INTERVAL)).length
					&& (entityField = EntityUtils.getEntityField(entityType, pairs[1])) != null) {
				return new QueryConditionItem(QueryRelationship.In, entityField.getName(), conditionValue);
			}

			return null;
		}).filter(Objects::nonNull).collect(toList());

		queryItems.addAll(inQueryItems);
		return new QueryCondition(QueryConditionType.And, queryItems);
	}

	public static <TEntity extends BaseEntity> Predicate wrapQueryItem(Root<TEntity> root, CriteriaBuilder cb,
			QueryConditionItem queryItem) {
		Object value = queryItem.getValue();
		switch (queryItem.getRelationship()) {
		case NotEquals:
			return cb.notEqual(root.get(queryItem.getField()), value);
		case Equals:
			return cb.equal(root.get(queryItem.getField()), value);
		case Great:
			value = queryItem.getValue();
			if (value instanceof Byte) {
				return cb.ge(root.get(queryItem.getField()), (Byte) value);
			}
			if (value instanceof Short) {
				return cb.ge(root.get(queryItem.getField()), (Short) value);
			}
			if (value instanceof Integer) {
				return cb.ge(root.get(queryItem.getField()), (Integer) value);
			}
			if (value instanceof Long) {
				return cb.ge(root.get(queryItem.getField()), (Long) value);
			}
			if (value instanceof Float) {
				return cb.ge(root.get(queryItem.getField()), (Float) value);
			}
			if (value instanceof Double) {
				return cb.ge(root.get(queryItem.getField()), (Double) value);
			}
			if (value instanceof Date) {
				return cb.greaterThanOrEqualTo(root.get(queryItem.getField()).as(Date.class), (Date) value);
			}
			if (value instanceof String) {
				return cb.greaterThanOrEqualTo(root.get(queryItem.getField()).as(String.class), (String) value);
			}
			return null;
		case Less:
			value = queryItem.getValue();
			if (value instanceof Byte) {
				return cb.le(root.get(queryItem.getField()), (Byte) value);
			}
			if (value instanceof Short) {
				return cb.le(root.get(queryItem.getField()), (Short) value);
			}
			if (value instanceof Integer) {
				return cb.le(root.get(queryItem.getField()), (Integer) value);
			}
			if (value instanceof Long) {
				return cb.le(root.get(queryItem.getField()), (Long) value);
			}
			if (value instanceof Float) {
				return cb.le(root.get(queryItem.getField()), (Float) value);
			}
			if (value instanceof Double) {
				return cb.le(root.get(queryItem.getField()), (Double) value);
			}
			if (value instanceof Date) {
				return cb.lessThanOrEqualTo(root.get(queryItem.getField()).as(Date.class), (Date) value);
			}
			if (value instanceof String) {
				return cb.lessThanOrEqualTo(root.get(queryItem.getField()).as(String.class), (String) value);
			}
			return null;
		case Like:
			return cb.like(root.get(queryItem.getField()), String.format("%%%s%%", value));
		case Null:
			return cb.isNull(root.get(queryItem.getField()));
		case NotNull:
			return cb.isNotNull(root.get(queryItem.getField()));
		case Start:
			return cb.like(root.get(queryItem.getField()), String.format("%s%%", value));
		case End:
			return cb.like(root.get(queryItem.getField()), String.format("%%%s", value));
		case Empty:
			return cb.or(cb.isNull(root.get(queryItem.getField())), cb.equal(root.get(queryItem.getField()), ""));
		case NotEmpty:
			return cb.and(cb.isNotNull(root.get(queryItem.getField())),
					cb.notEqual(root.get(queryItem.getField()), ""));
		default:
			break;
		}

		return null;
	}

	public static Object escapeMysqlParamValue(Object value) {
		try {
			if (value != null) {
				if (value instanceof String) {
					String str = value.toString();
					str = str.replaceAll("%", "\\\\%");
					str = str.replaceAll("_", "\\\\_");
					return str;
				}
			}
		} catch (Exception e) {

		}
		return value;
	}

	public static <TEntity1 extends BaseEntity, TEntity2 extends BaseEntity> Predicate wrapQueryItem(Path<?> joinRoot,
			CriteriaBuilder criteriaBuilder, QueryConditionItem queryItem) {
		Object value = queryItem.getValue();
		switch (queryItem.getRelationship()) {
		case NotEquals:
			return criteriaBuilder.notEqual(joinRoot.get(queryItem.getField()), value);
		case Equals:
			return criteriaBuilder.equal(joinRoot.get(queryItem.getField()), value);
		case Great:
			value = queryItem.getValue();
			if (value instanceof Byte) {
				return criteriaBuilder.ge(joinRoot.get(queryItem.getField()), (Byte) value);
			}
			if (value instanceof Short) {
				return criteriaBuilder.ge(joinRoot.get(queryItem.getField()), (Short) value);
			}
			if (value instanceof Integer) {
				return criteriaBuilder.ge(joinRoot.get(queryItem.getField()), (Integer) value);
			}
			if (value instanceof Long) {
				return criteriaBuilder.ge(joinRoot.get(queryItem.getField()), (Long) value);
			}
			if (value instanceof Float) {
				return criteriaBuilder.ge(joinRoot.get(queryItem.getField()), (Float) value);
			}
			if (value instanceof Double) {
				return criteriaBuilder.ge(joinRoot.get(queryItem.getField()), (Double) value);
			}
			if (value instanceof Date) {
				return criteriaBuilder.greaterThanOrEqualTo(joinRoot.get(queryItem.getField()).as(Date.class),
						(Date) value);
			}
			if (value instanceof String) {
				return criteriaBuilder.greaterThanOrEqualTo(joinRoot.get(queryItem.getField()).as(String.class),
						(String) value);
			}
			return null;
		case Less:
			value = queryItem.getValue();
			if (value instanceof Byte) {
				return criteriaBuilder.le(joinRoot.get(queryItem.getField()), (Byte) value);
			}
			if (value instanceof Short) {
				return criteriaBuilder.le(joinRoot.get(queryItem.getField()), (Short) value);
			}
			if (value instanceof Integer) {
				return criteriaBuilder.le(joinRoot.get(queryItem.getField()), (Integer) value);
			}
			if (value instanceof Long) {
				return criteriaBuilder.le(joinRoot.get(queryItem.getField()), (Long) value);
			}
			if (value instanceof Float) {
				return criteriaBuilder.le(joinRoot.get(queryItem.getField()), (Float) value);
			}
			if (value instanceof Double) {
				return criteriaBuilder.le(joinRoot.get(queryItem.getField()), (Double) value);
			}
			if (value instanceof Date) {
				return criteriaBuilder.lessThanOrEqualTo(joinRoot.get(queryItem.getField()).as(Date.class),
						(Date) value);
			}
			if (value instanceof String) {
				return criteriaBuilder.lessThanOrEqualTo(joinRoot.get(queryItem.getField()).as(String.class),
						(String) value);
			}
			return null;
		case Like:
			return criteriaBuilder.like(joinRoot.get(queryItem.getField()), String.format("%%%s%%", value));
		case Null:
			return criteriaBuilder.not(joinRoot.get(queryItem.getField()));
		case NotNull:
			return criteriaBuilder.isNotNull(joinRoot.get(queryItem.getField()));
		case Start:
			return criteriaBuilder.like(joinRoot.get(queryItem.getField()), String.format("%s%%", value));
		case End:
			return criteriaBuilder.like(joinRoot.get(queryItem.getField()), String.format("%%%s", value));
		default:
			break;
		}

		return null;
	}

	/**
	 * 包装In查询条件
	 *
	 * @param root
	 * @param criteriaBuilder
	 * @param queryItems
	 * @param queryItem
	 * @param <TEntity>
	 * @return
	 */
	public static <TEntity extends BaseEntity> Predicate wrapInQueryItem(Path<?> root, CriteriaBuilder criteriaBuilder,
			List<QueryConditionItem> queryItems, QueryConditionItem queryItem) {
		CriteriaBuilder.In<Object> in = criteriaBuilder.in(root.get(queryItem.getField()));

		queryItems.stream().filter(a -> queryItem.getField().equals(a.getField())).forEach(a -> {
			in.value(a.getValue());
		});

		return criteriaBuilder.and(in);
	}

	/**
	 * 包装In查询条件(Join查询)
	 *
	 * @param join
	 * @param criteriaBuilder
	 * @param queryItems
	 * @param queryItem
	 * @param <R>
	 * @param <T>
	 * @return
	 */
	public static <R extends BaseEntity, T extends BaseEntity> Predicate wrapInQueryItem(Join<R, T> join,
			CriteriaBuilder criteriaBuilder, List<QueryConditionItem> queryItems, QueryConditionItem queryItem) {
		CriteriaBuilder.In<Object> in = criteriaBuilder.in(join.get(queryItem.getField()));

		queryItems.stream().filter(a -> queryItem.getField().equals(a.getField())).forEach(a -> {
			in.value(a.getValue());
		});

		return criteriaBuilder.and(in);
	}

	/**
	 * 取得ID字段IN查询
	 *
	 * @return 查询条件类型
	 */
	public static String getInIdConditionType() {
		return String.format(FORMAT_CONDITION, IN_KEY, INTERVAL, EntityUtils.FIELD_ID);
	}

	/**
	 * 取得ID字段的 IN查询 查询条件对象
	 *
	 * @param value
	 *            字段值
	 * @return IN查询条件对象
	 */
	public static RequestCondition getInIdCondition(String value) {
		return getInCondition(EntityUtils.FIELD_ID, value);
	}

	/**
	 * 取得IN查询查询条件对象
	 *
	 * @param field
	 *            字段名
	 * @param value
	 *            字段值
	 * @return IN查询条件对象
	 */
	public static RequestCondition getInCondition(String field, String value) {
		if (!StringUtils.isEmpty(field) && !StringUtils.isEmpty(value)) {
			return new RequestCondition(String.format(FORMAT_CONDITION, IN_KEY, INTERVAL, field), value);
		}

		return new RequestCondition();
	}

	public static Predicate prepareCondition(Predicate where1, Predicate where2, CriteriaBuilder criteriaBuilder) {
		if (where1 != null && where2 != null) {
			return criteriaBuilder.and(where1, where2);
		}
		if (where1 != null) {
			return where1;
		}
		if (where2 != null) {
			return where2;
		}
		return null;
	}

	public static Predicate prepareCondition(CriteriaBuilder criteriaBuilder, Predicate... conditions) {
		return prepareCondition(criteriaBuilder, Arrays.asList(conditions));
	}

	public static Predicate prepareCondition(CriteriaBuilder criteriaBuilder, List<Predicate> conditions) {
		if (CollectionUtils.isEmpty(conditions)) {
			return null;
		}

		AtomicReference<Predicate> result = new AtomicReference<>();
		conditions.stream().filter(Objects::nonNull).forEach(a -> {
			Predicate prev;
			if ((prev = result.get()) == null) {
				result.set(a);
			} else {
				result.set(criteriaBuilder.and(prev, a));
			}
		});

		return result.get();
	}

}
