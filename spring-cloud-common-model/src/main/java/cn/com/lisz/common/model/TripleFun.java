package cn.com.lisz.common.model;

/**
 * 三元方法定义
 *
 * @param <T1>
 *            参数1类型
 * @param <T2>
 *            参数2类型
 * @param <T3>
 *            参数3类型
 * @param <TResult>
 *            返回值类型
 */
@FunctionalInterface
public interface TripleFun<T1, T2, T3, TResult> {
	/**
	 * 调用
	 *
	 * @param t1
	 *            参数1
	 * @param t2
	 *            参数2
	 * @param t3
	 *            参数3
	 * @return 返回值
	 */
	TResult invoke(T1 t1, T2 t2, T3 t3);
}
