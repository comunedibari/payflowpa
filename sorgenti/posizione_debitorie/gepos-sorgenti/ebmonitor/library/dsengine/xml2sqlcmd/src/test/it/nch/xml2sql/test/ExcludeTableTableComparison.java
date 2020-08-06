/**
 * 04/ago/2009
 */
package it.nch.xml2sql.test;

/**
 * @author gdefacci
 */
public class ExcludeTableTableComparison extends TableComparison{

	public ExcludeTableTableComparison(String tableName) {
		super(tableName, null);
	}

	public String toString() {
		return "exclude " + getTableName() + " from comparison";
	}

	
}
