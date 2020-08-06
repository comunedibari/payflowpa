/**
 * 15/mag/2009
 */
package it.nch.xml2sql.test;

/**
 * @author gdefacci
 */
public class TableComparison {
	private final String tableName;
	private final String[] excludeFields;
	private final String sqlQuery;
	public TableComparison(String tableName, String[] excludeFields) {
		this(tableName, excludeFields, null);
	}
	public TableComparison(String tableName, String[] excludeFields, String sqlQuery) {
		this.tableName = tableName;
		this.excludeFields = excludeFields;
		this.sqlQuery = sqlQuery;
	}
	public String getTableName() {
		return tableName;
	}
	public String[] getExcludeFields() {
		return excludeFields;
	}
	public String getQuery() {
		return sqlQuery;
	}
	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (excludeFields!=null && excludeFields.length > 0) {
			sb.append(excludeFields[0]);
		}
		for (int i = 1; i < excludeFields.length; i++) {
			sb.append(", ");
			sb.append(excludeFields[i]);
		}
		return "compare table(" + tableName + ") excluding fields (" + sb.toString() + ")with query " + sqlQuery;
	}
	
}