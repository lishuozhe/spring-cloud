package cn.com.lisz.common.model.web;

import java.util.List;

import cn.com.lisz.common.model.BaseModel;

/**
 * 分页通用类
 *
 * @param <TEntry>
 */
public class PaggingModel<TEntry extends BaseModel> {
	private int pageIndex;
	private int pageSize;
	private long itemCount;
	private long pageCount;
	private List<TEntry> itemList;

	public PaggingModel() {
	}

	public PaggingModel(List<TEntry> itemList) {
		this.itemList = itemList;
	}

	public PaggingModel(int pageIndex, int pageSize, long itemCount, long pageCount, List<TEntry> itemList) {
		this.pageIndex = pageIndex;
		this.pageSize = pageSize;
		this.itemCount = itemCount;
		this.pageCount = pageCount;
		this.itemList = itemList;
	}

	/**
	 * 取得当前页码
	 *
	 * @return
	 */
	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	/**
	 * 取得页码长度
	 *
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * 取得项目数
	 *
	 * @return
	 */
	public long getItemCount() {
		return itemCount;
	}

	public void setItemCount(long itemCount) {
		this.itemCount = itemCount;
	}

	/**
	 * 取得分页数
	 *
	 * @return
	 */
	public long getPageCount() {
		return pageCount;
	}

	public void setPageCount(long pageCount) {
		this.pageCount = pageCount;
	}

	/**
	 * 取得分页项目
	 *
	 * @return
	 */
	public List<TEntry> getItemList() {
		return itemList;
	}

	public void setItemList(List<TEntry> itemList) {
		this.itemList = itemList;
	}
}
