package com.koleksinaia.rest.controller.response;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;


public class SearchResult<T> {
	
	private int pageNumber;
	
	private long totalItems;

	private List<T> content;
	
	public SearchResult() {
		
	}
	
	public SearchResult(Page<T> page) {
		this.pageNumber = page.getNumber() + 1;
		this.totalItems = page.getTotalElements();
		
		content = new ArrayList<T>();
		for (int i=0; i<page.getNumberOfElements(); i++) {
			content.add(page.getContent().get(i));
		}
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public long getTotalItems() {
		return totalItems;
	}

	public void setTotalItems(long totalItems) {
		this.totalItems = totalItems;
	}

	public List<T> getContent() {
		return content;
	}

	public void setContent(List<T> content) {
		this.content = content;
	}
	
	public static <Order> SearchResult<Order> createSearchResult(Page<Order> page) {
		return new SearchResult<Order>(page);
	}
}
