
package com.example.cs4500_sp19_noideainc.utils;

import java.util.List;

public class SearchCriteria {

	private List<SearchPredicate> criteria;
	public SearchCriteria() {
		// TODO Auto-generated constructor stub
	}
	public List<SearchPredicate> getCriteria() {
		return criteria;
	}
	public void setCriteria(List<SearchPredicate> criteria) {
		this.criteria = criteria;
	}
	
	public String toString() {
		String temp = "";
		for(int i =0; i < criteria.size(); i++) {
			temp = temp + " " + Integer.toString(i) + " " +  criteria.get(i).toString() + "\n";
		}
		return temp;
		
	}

}