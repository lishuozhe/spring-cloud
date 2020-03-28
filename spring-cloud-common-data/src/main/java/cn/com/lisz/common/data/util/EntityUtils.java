package cn.com.lisz.common.data.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.alibaba.fastjson.JSON;

import cn.com.lisz.common.data.entity.BaseEntity;
import cn.com.lisz.common.model.BaseModel;
import cn.com.lisz.common.model.KeyValuePair;
import cn.com.lisz.common.util.CollectionUtils;
import cn.com.lisz.common.util.StringUtils;
import cn.com.lisz.common.util.comparer.Comparer;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Stream;

import javax.persistence.Id;

import static java.util.stream.Collectors.toList;

/**
 * 数据对象工具类
 * 
 * @author lisz
 *
 */
public class EntityUtils {

	public static final String FIELD_ID = "id";

	public static final String FIELD_PLATFORMID = "platformId";

	public static final String FIELD_CREATEBY = "createBy";
	public static final String FIELD_UPDATEBY = "updateBy";

	public static final String FIELD_CREATETIME = "createTime";
	public static final String FIELD_UPDATETIME = "updateTime";

	public static final String FIELD_REMARKS = "remarks";

	public static final String FIELD_DELETE = "delFlag";

	public static final String FIELD_ORDERTICKS = "orderTicks";

	private static final int MAX_MAPPING_LEVEL = 2;

	private static Logger logger;

	static {
		logger = LoggerFactory.getLogger(EntityUtils.class);
	}

	/* ...... */

	/**
	 * 数据对象字段值拷贝
	 *
	 * @param src
	 *            原始对象
	 * @param dest
	 *            目标对象
	 * @param <TSrc>
	 * @param <TDest>
	 */
	public static <TSrc, TDest> void clone(TSrc src, TDest dest) {
		clone(src, dest, null, true, null, null, 0, false);
	}

	/**
	 * 数据对象字段值拷贝
	 *
	 * @param src
	 *            原始对象
	 * @param dest
	 *            目标对象
	 * @param setter
	 *            附加操作
	 * @param <TSrc>
	 * @param <TDest>
	 */
	public static <TSrc, TDest> void clone(TSrc src, TDest dest, Consumer<TDest> setter) {
		clone(src, dest, setter, true, null, null, 0, false);
	}

	/**
	 * 数据对象字段值拷贝
	 *
	 * @param src
	 *            原始对象
	 * @param dest
	 *            目标对象
	 * @param setter
	 *            附加操作
	 * @param ignoreEmpty
	 *            忽略空值
	 * @param includes
	 *            包含字段
	 * @param excludes
	 *            排除字段
	 * @param level
	 *            映射层数
	 * @param isSetChild
	 *            是否映射子对象
	 * @param <TSrc>
	 * @param <TDest>
	 */
	public static <TSrc, TDest> void clone(TSrc src, TDest dest, Consumer<TDest> setter, boolean ignoreEmpty,
			List<String> includes, List<String> excludes, int level, boolean isSetChild) {
		if (src == null || dest == null) {
			return;
		}
		if (level <= 0) {
			level = 1;
		}
		setEntryFields(src, dest, ignoreEmpty, includes, excludes, level, isSetChild);

		if (setter != null) {
			try {
				setter.accept(dest);
			} catch (Exception e) {
				logger.error(
						String.format("Clone data error, src: %s, dest: %s", getEntryInfo(src), getEntryInfo(dest)), e);
			}
		}
	}

	/**
	 * 数据对象映射
	 *
	 * @param destType
	 *            目标类型
	 * @param src
	 *            原始对象
	 * @param <TSrc>
	 * @param <TDest>
	 * @return
	 */
	public static <TSrc, TDest> TDest mapping(Class<TDest> destType, TSrc src) {
		return mapping(destType, src, null, true, null, null, 0, true);
	}

	/**
	 * 数据对象映射
	 *
	 * @param destType
	 *            目标类型
	 * @param src
	 *            原始对象
	 * @param level
	 *            映射层数
	 * @param <TSrc>
	 * @param <TDest>
	 * @return
	 */
	public static <TSrc, TDest> TDest mapping(Class<TDest> destType, TSrc src, int level) {
		return mapping(destType, src, null, true, null, null, level, true);
	}

	/**
	 * 数据对象映射
	 *
	 * @param destType
	 *            目标类型
	 * @param src
	 *            原始对象
	 * @param setter
	 *            附加操作
	 * @param <TSrc>
	 * @param <TDest>
	 * @return
	 */
	public static <TSrc, TDest> TDest mapping(Class<TDest> destType, TSrc src, Consumer<TDest> setter) {
		return mapping(destType, src, setter, true, null, null, 0, true);
	}

	/**
	 * 数据对象映射
	 *
	 * @param destType
	 *            目标类型
	 * @param src
	 *            原始对象
	 * @param setter
	 *            附加操作
	 * @param ignoreEmpty
	 *            忽略空值
	 * @param includes
	 *            包含字段
	 * @param excludes
	 *            排除字段
	 * @param level
	 *            映射层数
	 * @param isSetChild
	 *            是否映射子对象
	 * @param <TSrc>
	 * @param <TDest>
	 * @return
	 */
	public static <TSrc, TDest> TDest mapping(Class<TDest> destType, TSrc src, Consumer<TDest> setter,
			boolean ignoreEmpty, List<String> includes, List<String> excludes, int level, boolean isSetChild) {
		if (destType == null || src == null) {
			return null;
		}
		if (level <= 0) {
			level = 1;
		}
		TDest dest;
		try {
			dest = destType.newInstance();
		} catch (Exception e) {
			logger.error("目标类型实例化出错", e);
			return null;
		}

		setEntryFields(src, dest, ignoreEmpty, includes, excludes, level, isSetChild);

		if (setter != null) {
			try {
				setter.accept(dest);
			} catch (Exception e) {
				logger.error(String.format("附加操作处理出错, 目标对象: %s", getEntryInfo(dest)), e);
			}
		}

		return dest;
	}

	/**
	 * 取得对象字段值(字符串)
	 *
	 * @param entry
	 *            对象实例
	 * @param fieldName
	 *            字段名
	 * @param <TEntry>
	 *            对象类型
	 * @return 字段值
	 */
	public static <TEntry> String getEntryFieldValue(TEntry entry, String fieldName) {
		return getEntryFieldValue(entry, String.class, fieldName);
	}

	/**
	 * 取得对象字段值
	 *
	 * @param entry
	 *            对象实例
	 * @param fieldType
	 *            字段类型
	 * @param name
	 *            字段名
	 * @param <TEntry>
	 *            对象类型
	 * @param <TValue>
	 *            字段类型
	 * @return 字段值
	 */
	@SuppressWarnings("unchecked")
	public static <TEntry, TValue> TValue getEntryFieldValue(TEntry entry, Class<TValue> fieldType, String name) {
		AtomicReference<TValue> reference = new AtomicReference<>();

		if (entry != null && fieldType != null && !StringUtils.isEmpty(name)) {
			Stream.of(entry.getClass().getDeclaredFields())
					.filter(a -> name.equalsIgnoreCase(a.getName()) && fieldType == a.getType()).findFirst()
					.ifPresent(a -> {
						try {
							a.setAccessible(true);
							reference.set((TValue) a.get(entry));
						} catch (Exception e) {
							logger.warn("类型实例字段值取得失败", e);
						}
					});
		}

		return reference.get();
	}

	/**
	 * 数据对象描述信息取得
	 *
	 * @param entry
	 *            数据对象实例
	 * @return
	 */
	public static <TEntry> String getEntryInfo(TEntry entry) {
		try {
			return JSON.toJSONString(entry);
		} catch (Exception ignore) {
		}

		return null;
	}

	/**
	 * 实体类主键名取得
	 *
	 * @param entityType
	 *            实体类类型
	 * @param <TEntity>
	 * @return
	 */
	public static <TEntity extends BaseEntity> String getPrimaryKeyName(Class<TEntity> entityType) {
		if (entityType != null) {
			Optional<Field> optionalFieldPrimaryKey = getPrimaryKeyFieldList(entityType).stream().findFirst();
			if (optionalFieldPrimaryKey.isPresent()) {
				return optionalFieldPrimaryKey.get().getName();
			}
		}

		return null;
	}

	/**
	 * 实体类类型名主键名取得
	 *
	 * @param entityType
	 *            实体类类型
	 * @param <TEntity>
	 * @return
	 */
	public static <TEntity extends BaseEntity> String getEntityNameWithPrimaryKeyName(Class<TEntity> entityType) {
		String primaryKeyName = getPrimaryKeyName(entityType);
		return StringUtils.isEmpty(primaryKeyName) ? null
				: String.format("%s%s", StringUtils.getLastPart(entityType.getName(), "."), primaryKeyName);
	}

	/**
	 * 取得主键值
	 *
	 * @param entityType
	 *            实体类类型
	 * @param model
	 *            模型类对象
	 * @param <TEntity>
	 * @param <TModel>
	 * @return
	 */
	public static <TEntity extends BaseEntity, TModel extends BaseModel> String getPrimaryKeyValue(
			Class<TEntity> entityType, TModel model) {
		if (entityType != null && model != null) {
			List<Field> entityPkFields = getPrimaryKeyFieldList(entityType);
			if (!CollectionUtils.isEmpty(entityPkFields)) {
				String pkName = entityPkFields.get(0).getName();
				Optional<Field> modelOptionalField = Arrays.stream(model.getClass().getDeclaredFields())
						.filter(a -> pkName.equals(a.getName()) && a.getType() == String.class).findFirst();
				if (modelOptionalField.isPresent()) {
					Field modelField = modelOptionalField.get();
					modelField.setAccessible(true);
					try {
						return (String) modelField.get(model);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}

		return null;
	}

	/**
	 * 取得排序字段
	 *
	 * @param entityType
	 *            实体类类型
	 * @param <TEntity>
	 * @return
	 */
	public static <TEntity extends BaseEntity> String getEntityOrderFieldName(Class<TEntity> entityType) {
		if (entityType == null) {
			return null;
		}
		Optional<Field> optionalField = Stream
				.concat(Arrays.stream(entityType.getSuperclass().getDeclaredFields()),
						Arrays.stream(entityType.getDeclaredFields()))
				.filter(a -> (FIELD_ORDERTICKS.equals(a.getName()) && a.getType() == Long.class)
						|| (FIELD_CREATETIME.equals(a.getName()) && a.getType() == Date.class))
				.findFirst();
		if (optionalField.isPresent()) {
			return optionalField.get().getName();
		}

		return null;
	}

	/**
	 * 实体类字段取得
	 *
	 * @param entityType
	 *            实体类类型
	 * @param key
	 *            检索字段名
	 * @return 字段
	 */
	public static Field getEntityField(Class<?> entityType, String key) {
		if (!StringUtils.isEmpty(key)) {
			Optional<Field> optionalField = Arrays.stream(entityType.getDeclaredFields())
					.filter(a -> key.equalsIgnoreCase(a.getName())).findFirst();
			if (optionalField.isPresent()) {
				return optionalField.get();
			}

			optionalField = Arrays.stream(entityType.getSuperclass().getDeclaredFields())
					.filter(a -> key.equalsIgnoreCase(a.getName())).findFirst();
			if (optionalField.isPresent()) {
				return optionalField.get();
			}
		}

		return null;
	}

	/**
	 * 实体类字段名取得
	 *
	 * @param entityType
	 *            实体类类型
	 * @param key
	 *            检索字段名
	 * @param <TEntity>
	 * @return
	 */
	public static <TEntity extends BaseEntity> String getEntityFieldName(Class<TEntity> entityType, String key) {
		Field field = getEntityField(entityType, key);
		return field == null ? null : field.getName();
	}

	/**
	 * 实体类逻辑删除设值
	 *
	 * @param entity
	 *            实体类对象
	 * @param status
	 *            是否删除
	 * @param <TEntity>
	 * @return
	 */
	public static <TEntity extends BaseEntity> boolean setEntityDelete(TEntity entity, String status) {
		boolean result = false;
		Class<?> entityType = entity.getClass(), baseType = entityType.getSuperclass();

		try {
			Optional<Field> optionalDelete = Stream
					.concat(Arrays.stream(entityType.getDeclaredFields()), Arrays.stream(baseType.getDeclaredFields()))
					.filter(a -> FIELD_DELETE.equals(a.getName()) && a.getType() == String.class).findFirst();
			if (optionalDelete.isPresent()) {
				Field fieldDelete = optionalDelete.get();
				fieldDelete.setAccessible(true);
				fieldDelete.set(entity, status);

				result = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 判断字段是否存在
	 *
	 * @param entryType
	 *            对象类型
	 * @param fieldName
	 *            字段名
	 */
	public static <TEntity extends BaseEntity> boolean existEntryField(Class<TEntity> entryType, String fieldName) {
		return Stream
				.concat(Arrays.stream(entryType.getDeclaredFields()),
						Arrays.stream(entryType.getSuperclass().getDeclaredFields()))
				.anyMatch(a -> Comparer.invokeDefault(a.getName(), fieldName));
	}

	/**
	 * 对象不为NULL时做相应处理
	 *
	 * @param entry
	 *            对象
	 * @param processer
	 *            对象处理
	 * @param <TEntry>
	 */
	public static <TEntry> void ifPresent(TEntry entry, Consumer<TEntry> processer) {
		if (entry != null && processer != null) {
			try {
				processer.accept(entry);
			} catch (Exception ignored) {

			}
		}
	}

	/**
	 * 注解类值取得
	 *
	 * @param annotationType
	 *            注解类型
	 * @param type
	 *            要取得注解得类型
	 * @param processor
	 *            注解值取得方法
	 * @param provider
	 *            默认值提供方法
	 * @param <AnnotationType>
	 *            注解类型
	 * @param <ResultType>
	 *            返回类型
	 * @return 结果
	 */
	public static <AnnotationType extends Annotation, ResultType> ResultType annotation(
			Class<AnnotationType> annotationType, Class<?> type, Function<AnnotationType, ResultType> processor,
			Supplier<ResultType> provider) {
		if (type.isAnnotationPresent(annotationType)) {
			return processor.apply(type.getAnnotation(annotationType));
		}

		return provider.get();
	}

	/**
	 * 注解类值取得
	 *
	 * @param annotationType
	 *            注解类型
	 * @param method
	 *            要取得注解得方法
	 * @param processor
	 *            注解值取得方法
	 * @param provider
	 *            默认值提供方法
	 * @param <AnnotationType>
	 *            注解类型
	 * @param <ResultType>
	 *            返回类型
	 * @return 结果
	 */
	public static <AnnotationType extends Annotation, ResultType> ResultType annotation(
			Class<AnnotationType> annotationType, Method method, Function<AnnotationType, ResultType> processor,
			Supplier<ResultType> provider) {
		if (method.isAnnotationPresent(annotationType)) {
			return processor.apply(method.getAnnotation(annotationType));
		}

		return provider.get();
	}

	/* ...... */

	/**
	 * 对象不为NULL时取值
	 *
	 * @param income
	 *            输入对象
	 * @param getter
	 *            对象取值处理
	 * @param <TOutcome>
	 *            输出对象类型
	 * @param <TIncome>
	 *            输入对象类型
	 * @return 输出对象
	 */
	public static <TOutcome, TIncome> TOutcome valueOf(TIncome income, Function<TIncome, TOutcome> getter) {
		if (income != null && getter != null) {
			try {
				return getter.apply(income);
			} catch (Exception ignored) {
			}
		}

		return null;
	}

	/**
	 * Optional对象不为空时取值
	 *
	 * @param optional
	 *            Optional对象
	 * @param getter
	 *            对象取值处理
	 * @param <TOutcome>
	 *            输出对象类型
	 * @param <TIncome>
	 *            输入对象类型
	 * @return 输出对象
	 */
	public static <TOutcome, TIncome> TOutcome valueOf(Optional<TIncome> optional, Function<TIncome, TOutcome> getter) {
		if (optional != null && optional.isPresent() && getter != null) {
			try {
				return getter.apply(optional.get());
			} catch (Exception ignored) {
			}
		}

		return null;
	}

	/**
	 * 对象不为NULL时取值, 否则使用默认提供处理
	 *
	 * @param income
	 *            输入对象
	 * @param getter
	 *            对象取值处理
	 * @param supplier
	 *            默认提供处理
	 * @param <TOutcome>
	 *            输出对象类型
	 * @param <TIncome>
	 *            输入对象类型
	 * @return 输出对象
	 */
	public static <TOutcome, TIncome> TOutcome valueOf(TIncome income, Function<TIncome, TOutcome> getter,
			Supplier<TOutcome> supplier) {
		if (income != null && getter != null) {
			try {
				return getter.apply(income);
			} catch (Exception ignored) {
			}
		} else if (supplier != null) {
			try {
				return supplier.get();
			} catch (Exception ignored) {
			}
		}

		return null;
	}

	/**
	 * Optional对象不为空时取值, 否则使用默认提供处理
	 *
	 * @param optional
	 *            Optional对象
	 * @param getter
	 *            对象取值处理
	 * @param supplier
	 *            默认提供处理
	 * @param <TOutcome>
	 *            输出对象类型
	 * @param <TIncome>
	 *            输入对象类型
	 * @return 输出对象
	 */
	public static <TOutcome, TIncome> TOutcome valueOf(Optional<TIncome> optional, Function<TIncome, TOutcome> getter,
			Supplier<TOutcome> supplier) {
		if (optional != null && optional.isPresent() && getter != null) {
			try {
				return getter.apply(optional.get());
			} catch (Exception ignored) {
			}
		} else if (supplier != null) {
			try {
				return supplier.get();
			} catch (Exception ignored) {
			}
		}

		return null;
	}

	/* ...... */

	/**
	 * 取得MAP对象
	 *
	 * @param pairs
	 *            KeyValuePair序列
	 * @param <TKey>
	 *            Key类型
	 * @param <TValue>
	 *            Value类型
	 * @return MAP对象
	 */
	@SafeVarargs
	public static <TKey, TValue> Map<TKey, TValue> getMap(KeyValuePair<TKey, TValue>... pairs) {
		if (CollectionUtils.isEmpty(pairs)) {
			return new HashMap<>();
		}
		return getMap(CollectionUtils.isEmpty(pairs) ? null : Arrays.asList(pairs));
	}

	/**
	 * 取得MAP对象
	 *
	 * @param pairs
	 *            KeyValuePair序列
	 * @param <TKey>
	 *            Key类型
	 * @param <TValue>
	 *            Value类型
	 * @return MAP对象
	 */
	public static <TKey, TValue> Map<TKey, TValue> getMap(List<KeyValuePair<TKey, TValue>> pairs) {
		Map<TKey, TValue> map = new HashMap<>();

		if (!CollectionUtils.isEmpty(pairs)) {
			pairs.forEach(a -> map.put(a.getKey(), a.getValue()));
		}

		return map;
	}

	/* ...... */

	/**
	 * 取得基础字段名列表
	 *
	 * @return 基础字段名列表
	 */
	public static List<String> getBaseFieldList() {
		return Arrays.asList(EntityUtils.FIELD_ID, EntityUtils.FIELD_PLATFORMID, EntityUtils.FIELD_CREATETIME,
				EntityUtils.FIELD_CREATEBY, EntityUtils.FIELD_UPDATETIME, EntityUtils.FIELD_UPDATEBY,
				EntityUtils.FIELD_DELETE, EntityUtils.FIELD_REMARKS);
	}

	/**
	 * 设置对象字段值
	 *
	 * @param entry
	 *            对象实例
	 * @param fieldName
	 *            字段名
	 * @param value
	 *            字段值
	 * @param <TEntry>
	 *            对象类型
	 */
	public static <TEntry> void setEntryField(TEntry entry, String fieldName, Object value) {
		Assert.notNull(entry, "对象不能为NULL");
		Assert.hasText(fieldName, "字段名不能为空");

		Arrays.stream(entry.getClass().getDeclaredFields()).filter(a -> fieldName.equals(a.getName()))
				.forEach(a -> setEntryField(entry, a, value));
	}

	/**
	 * 设置对象字段值
	 *
	 * @param entry
	 *            对象实例
	 * @param fieldInfos
	 *            字段信息列表
	 * @param <TEntry>
	 *            对象类型
	 */
	@SuppressWarnings("unchecked")
	public static <TEntry> void setEntryFields(TEntry entry, KeyValuePair<String, Object>... fieldInfos) {
		Assert.notNull(entry, "对象不能为NULL");
		Assert.notEmpty(fieldInfos, "字段信息列表不能为空");

		Arrays.stream(entry.getClass().getDeclaredFields())
				.forEach(a -> Arrays.stream(fieldInfos).filter(i -> a.getName().equals(i.getKey())).findFirst()
						.ifPresent(i -> setEntryField(entry, a, i.getValue())));
	}

	private static <TEntity extends BaseEntity> List<Field> getPrimaryKeyFieldList(Class<TEntity> entityType) {
		List<Field> primaryKeyFieldList = Arrays.stream(entityType.getDeclaredFields())
				.filter(a -> a.isAnnotationPresent(Id.class)).collect(toList());

		if (CollectionUtils.isEmpty(primaryKeyFieldList)) {
			primaryKeyFieldList = Arrays.stream(entityType.getSuperclass().getDeclaredFields())
					.filter(a -> a.isAnnotationPresent(Id.class)).collect(toList());
		}

		return primaryKeyFieldList;
	}

	private static <TEntry> void setEntryField(TEntry entry, Field field, Object value) {
		try {
			field.setAccessible(true);
			field.set(entry, value);
		} catch (Exception e) {
			logger.error(String.format("更新对象字段出错, Type: %s, Field: %s, Value: %s", entry, field, value), e);
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private static <TSrc, TDest> void setEntryFields(TSrc src, TDest dest, boolean ignoreEmpty, List<String> includes,
			List<String> excludes, int level, boolean isSetChild) {
		List<String> filters = new ArrayList<>();
		List<Field> srcFields, destFields;

		if (CollectionUtils.isEmpty(includes)) {
			srcFields = Stream.concat(Arrays.stream(src.getClass().getDeclaredFields()),
					Arrays.stream(src.getClass().getSuperclass().getDeclaredFields())).collect(toList());
		} else {
			filters.clear();
			filters.addAll(includes.stream().filter(a -> !StringUtils.isEmpty(a)).collect(toList()));
			srcFields = Stream
					.concat(Arrays.stream(src.getClass().getDeclaredFields()),
							Arrays.stream(src.getClass().getSuperclass().getDeclaredFields()))
					.filter(a -> filters.contains(a.getName())).collect(toList());
		}

		if (CollectionUtils.isEmpty(excludes)) {
			destFields = Stream.concat(Arrays.stream(dest.getClass().getDeclaredFields()),
					Arrays.stream(dest.getClass().getSuperclass().getDeclaredFields())).collect(toList());
		} else {
			filters.clear();
			filters.addAll(excludes.stream().filter(a -> !StringUtils.isEmpty(a)).collect(toList()));
			destFields = Stream
					.concat(Arrays.stream(dest.getClass().getDeclaredFields()),
							Arrays.stream(dest.getClass().getSuperclass().getDeclaredFields()))
					.filter(a -> !filters.contains(a.getName())).collect(toList());
		}

		if (0 < srcFields.size() && 0 < destFields.size()) {
			for (Field srcField : srcFields) {
				try {
					srcField.setAccessible(true);
					Object srcFiledValue = srcField.get(src);
					if (!ignoreEmpty || srcFiledValue != null) {
						String srcFieldName = srcField.getName();
						Class<?> srcFieldType = srcField.getType();

						destFields.stream().filter(i -> srcFieldName.equalsIgnoreCase(i.getName())).findFirst()
								.ifPresent(destField -> {
									try {
										destField.setAccessible(true);
										Class<?> destFieldType;

										if (srcFieldType == (destFieldType = destField.getType())) {
											if (srcFieldType == List.class) {
												Type srcType = srcField.getGenericType(),
														destType = destField.getGenericType();
												if (srcType instanceof ParameterizedType
														&& destType instanceof ParameterizedType) {
													Class destGenericType;
													if (((ParameterizedType) srcType)
															.getActualTypeArguments()[0] == (destGenericType = (Class) ((ParameterizedType) destType)
																	.getActualTypeArguments()[0])) {
														destField.set(dest, srcFiledValue);
													} else {
														destField.set(dest,
																((List) srcFiledValue).stream()
																		.map(srcFieldItem -> mapping(destGenericType,
																				srcFieldItem, level))
																		.collect(toList()));
													}
												}
											} else {
												destField.set(dest, srcFiledValue);
											}
										} else if (srcFiledValue != null && destFieldType == String.class) {
											destField.set(dest, srcFiledValue.toString());
										} else if (isSetChild && level < MAX_MAPPING_LEVEL) {
											if (srcFiledValue != null
													&& srcFieldType.getSuperclass() == BaseEntity.class
													&& destFieldType.getSuperclass() == BaseModel.class) {
												Object destFieldValue = mapping(destFieldType, srcFiledValue,
														level + 1);
												destField.set(dest, destFieldValue);
											}
										}
									} catch (Exception e) {
										logger.error("设置目标实例字段出错", e);
									}
								});
					}
				} catch (Exception e) {
					logger.error("取得原始实例字段出错", e);
				}
			}
		}
	}

}
