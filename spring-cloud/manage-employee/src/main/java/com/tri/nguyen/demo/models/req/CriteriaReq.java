package com.tri.nguyen.demo.models.req;

import java.util.ArrayList;
import java.util.List;

import com.tri.nguyen.demo.models.entity.common.EnumCommon.SortType;

public class CriteriaReq {
	private String keyword;
	private List<String> columnName;
	private List<SortType> sortType;
	private Integer page;
	private Integer perPage;
	
	public CriteriaReq() {
	}

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	public Integer getPage() {
		return page == null || page <= 1 ? 1 : page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getPerPage() {
		return perPage == null || perPage < 1 ? 10 : perPage;
	}

	public void setPerPage(Integer perPage) {
		this.perPage = perPage;
	}

	public List<String> getColumnName() {
		return columnName == null ? new ArrayList<String>() : columnName;
	}

	public void setColumnName(List<String> columnName) {
		this.columnName = columnName;
	}

	public List<SortType> getSortType() {
		return sortType == null ? new ArrayList<SortType>() : sortType;
	}

	public void setSortType(List<SortType> sortType) {
		this.sortType = sortType;
	}

}
