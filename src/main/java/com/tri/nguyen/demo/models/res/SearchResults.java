package com.tri.nguyen.demo.models.res;

import java.util.Collections;
import java.util.List;

public class SearchResults {
	private Integer pageTotal;
	private List<Object> records;

	public SearchResults() {
	}

	private SearchResults(Builder builder) {
		this.pageTotal = builder.pageTotal;
		this.records = builder.records;
	}

	public Integer getPageTotal() {
		return pageTotal;
	}

	public void setPageTotal(Integer pageTotal) {
		this.pageTotal = pageTotal;
	}

	public List<Object> getRecords() {
		return records;
	}

	public void setRecords(List<Object> records) {
		this.records = records;
	}

	public static Builder builder() {
		return new Builder();
	}

	public static final class Builder {
		private Integer pageTotal;
		private List<Object> records = Collections.emptyList();

		private Builder() {
		}

		public Builder withPageTotal(Integer pageTotal) {
			this.pageTotal = pageTotal;
			return this;
		}

		public Builder withRecords(List<Object> records) {
			this.records = records;
			return this;
		}

		public SearchResults build() {
			return new SearchResults(this);
		}
	}

}
