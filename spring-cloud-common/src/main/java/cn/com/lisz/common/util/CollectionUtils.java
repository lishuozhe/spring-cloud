package cn.com.lisz.common.util;

import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class CollectionUtils {

	/**
	 * 取得实例列表
	 *
	 * @param entries
	 *            实例列表
	 * @param <T>
	 *            实例类型
	 * @return 实例列表
	 */
	@SafeVarargs
	public static <T> List<T> asList(T... entries) {
		if (!isEmpty(entries)) {
			return Arrays.stream(entries).collect(toList());
		}
		return new ArrayList<>();
	}

	/**
	 * 判断列表是否为空
	 *
	 * @param entries
	 *            列表
	 * @param <T>
	 *            类型
	 * @return 是否
	 */
	@SafeVarargs
	public static <T> boolean isEmpty(T... entries) {
		return entries == null || entries.length == 0;
	}

	/**
	 * 判断列表是否为空
	 *
	 * @param collection
	 *            列表
	 * @param <T>
	 *            类型
	 * @return 是否
	 */
	public static <T> boolean isEmpty(Collection<T> collection) {
		return collection == null || collection.isEmpty();
	}

	/**
	 * 判断列表是否全部为NULL
	 *
	 * @param entries
	 *            列表
	 * @param <T>
	 *            类型
	 * @return 是否
	 */
	@SuppressWarnings("unchecked")
	public static <T> boolean isAllNull(T... entries) {
		return entries == null || Stream.of(entries).allMatch(Objects::nonNull);
	}

	/**
	 * 判断列表是否全部为NULL
	 *
	 * @param collection
	 *            列表
	 * @param <T>
	 *            类型
	 * @return 是否
	 */
	public static <T> boolean isAllNull(Collection<T> collection) {
		return collection == null || collection.stream().allMatch(Objects::nonNull);
	}

	/**
	 * 判断列表是否包含非空元素并取得非空元素
	 *
	 * @param collection
	 *            列表
	 * @param result
	 *            非空列表
	 * @param <T>
	 *            类型
	 * @return 是否包含非空元素
	 */
	public static <T> boolean isNonNullList(Collection<T> collection, List<T> result) {
		if (!isEmpty(collection)) {
			result = collection.stream().filter(Objects::nonNull).collect(toList());
			return !isEmpty(result);
		}

		return false;
	}

	/**
	 * 连接列表序列
	 *
	 * @param collections
	 *            列表序列
	 * @param <T>
	 *            元素类型
	 * @return 元素列表
	 */
	@SafeVarargs
	public static <T> List<T> connect(Collection<T>... collections) {
		List<T> result = new ArrayList<>();

		if (!isEmpty(collections)) {
			Arrays.stream(collections).filter(collection -> !isEmpty(collection))
					.forEach(collection -> collection.stream().filter(Objects::nonNull).forEach(result::add));
		}

		return result;
	}

	/**
	 * 连接列表序列
	 *
	 * @param collections
	 *            列表序列
	 * @param <T>
	 *            元素类型
	 * @return 元素列表
	 */
	public static <T> List<T> connect(Collection<Collection<T>> collections) {
		List<T> result = new ArrayList<>();

		if (!isEmpty(collections)) {
			collections.stream().filter(collection -> !isEmpty(collection))
					.forEach(collection -> collection.stream().filter(Objects::nonNull).forEach(result::add));
		}

		return result;
	}

	/**
	 * 取得列表中交叉的元素
	 *
	 * @param collection1
	 *            列表1
	 * @param collection2
	 *            列表2
	 * @param <T>
	 *            元素类型
	 * @return 元素列表
	 */
	public static <T> List<T> crossItems(Collection<T> collection1, Collection<T> collection2) {
		if (isEmpty(collection1) || isEmpty(collection2)) {
			return Collections.emptyList();
		}

		List<T> result = new ArrayList<>();

		collection1.forEach(val -> {
			if (collection2.contains(val)) {
				result.add(val);
			}
		});

		return result;
	}

	/**
	 * 取得列表中非空列表
	 *
	 * @param entries
	 *            列表
	 * @param <T>
	 *            类型
	 * @return 非空列表
	 */
	@SuppressWarnings("unchecked")
	public static <T> List<T> getNonNullList(T... entries) {
		if (!isEmpty(entries)) {
			return Stream.of(entries).filter(Objects::nonNull).collect(toList());
		}

		return Collections.emptyList();
	}

	/**
	 * 取得列表中非空列表
	 *
	 * @param collection
	 *            列表
	 * @param <T>
	 *            类型
	 * @return 非空列表
	 */
	public static <T> List<T> getNonNullList(Collection<T> collection) {
		if (!isEmpty(collection)) {
			return collection.stream().filter(Objects::nonNull).collect(toList());
		}

		return Collections.emptyList();
	}

	/**
	 * 判断列表是否不为空
	 * 
	 * @param coll
	 *            列表
	 * @return 是否
	 */
	public static boolean isNotEmpty(Collection<?> coll) {
		return !isEmpty(coll);
	}
}
