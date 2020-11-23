package com.laz.arithmetic;

import java.util.List;

public class NestedIntegerImpl implements NestedInteger {
	private List<NestedInteger> result = null;
	private Integer value;

	public void setResult(List<NestedInteger> result) {
		this.result = result;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	@Override
	public boolean isInteger() {
		return result == null ;
	}

	@Override
	public Integer getInteger() {
		return value;
	}

	@Override
	public List<NestedInteger> getList() {
		return result;
	}

}
