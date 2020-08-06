package it.nch.iris.business.custom.dao.util;

import java.util.ArrayList;
import java.util.Iterator;

public class BusinessQuery {
	
	private ArrayList attributes;
	private String fromClause;
	private String whereClause;
	private String orderByClause;
	private static final String SPACE = " ";
	private static final String COMMA = ",";

	public static void main(String[] args) {
		BusinessQuery bq = new BusinessQuery();
		bq.addAttribute("A.ID");
		bq.addAttribute("B.ID");
		bq.addAttribute("A.DESCRIPTION");
		bq.addFromClause("FROM TABLE_A A, TABLE_B B");
		bq.addWhereClause("WHERE A.ID = B.ID");
		bq.addOrderByClause("ORDER BY A.ID");
		
		

	}
	
	public void addFromClause(String fromClause) {
		this.fromClause = fromClause;
	}
	
	public void addWhereClause(String whereClause) {
		this.whereClause = whereClause;
	}
	
	public void addOrderByClause(String orderByClause) {
		this.orderByClause = orderByClause;
	}
	
	public void addAttribute(String column) {
		if (attributes == null) {
			attributes = new ArrayList();
		}
		attributes.add(column);
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT");
		sb.append(SPACE);
		
		Iterator it = attributes.iterator();
		while (it.hasNext()) {
			String attr = (String)it.next();
			sb.append(attr);
			if (it.hasNext()) {
				sb.append(COMMA);
				sb.append(SPACE);
			}
		}
		
		if (fromClause != null) {
			sb.append(SPACE);
			sb.append(fromClause);
		}
		if (whereClause != null) {
			sb.append(SPACE);
			sb.append(whereClause);
		}
		if (orderByClause != null) {
			sb.append(SPACE);
			sb.append(orderByClause);
		}
		
		return sb.toString();
	}
	
	public String toStringWithDistinct() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("SELECT DISTINCT");
		sb.append(SPACE);
		
		Iterator it = attributes.iterator();
		while (it.hasNext()) {
			String attr = (String)it.next();
			sb.append(attr);
			if (it.hasNext()) {
				sb.append(COMMA);
				sb.append(SPACE);
			}
		}
		
		if (fromClause != null) {
			sb.append(SPACE);
			sb.append(fromClause);
		}
		if (whereClause != null) {
			sb.append(SPACE);
			sb.append(whereClause);
		}
		if (orderByClause != null) {
			sb.append(SPACE);
			sb.append(orderByClause);
		}
		
		return sb.toString();
	}


}
