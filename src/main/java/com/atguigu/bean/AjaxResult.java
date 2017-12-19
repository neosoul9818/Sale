package com.atguigu.bean;

import java.util.List;

public class AjaxResult {
	
	private boolean success;
	
	private List<T_MALL_VALUE> list;

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@Override
	public String toString() {
		return "AjaxResult [success=" + success + "]";
	}

	public AjaxResult(boolean success) {
		super();
		this.success = success;
	}

	public AjaxResult() {
		super();
	}

	public List<T_MALL_VALUE> getList() {
		return list;
	}

	public void setList(List<T_MALL_VALUE> list) {
		this.list = list;
	}
}
