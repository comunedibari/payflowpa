/**
 * 26/giu/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.ArrayUtils;

/**
 * @author gdefacci
 */
public class TableComparisonContainer {
	
	private TableComparison[] comps;

	public TableComparisonContainer(TableComparison[] comps) {
		this.comps = comps;
	}
	
	public TableComparisonContainer setQuery(String tableName, String query) {
		for (int i = 0; i < comps.length; i++) {
			TableComparison cmp = comps[i];
			if (cmp.getTableName().equalsIgnoreCase(tableName)) {
				comps[i] = new TableComparison(cmp.getTableName(), cmp.getExcludeFields(), query);
				return this;
			}
		}
		throw new IllegalStateException("cant find table " + tableName);
	}
	
	public TableComparisonContainer excludeFields(String tableName, String[] flds) {
		for (int i = 0; i < comps.length; i++) {
			TableComparison cmp = comps[i];
			if (cmp.getTableName().equalsIgnoreCase(tableName)) {
				comps[i] = new TableComparison(cmp.getTableName(), flds, cmp.getQuery());
				return this;
			}
		}
		throw new IllegalStateException("cant find table " + tableName);
	}
	
	public TableComparisonContainer addExcludeFields(String tableName, String[] flds) {
		for (int i = 0; i < comps.length; i++) {
			TableComparison cmp = comps[i];
			if (cmp.getTableName().equalsIgnoreCase(tableName)) {
				String[] mflds = ArrayUtils.concat(cmp.getExcludeFields(), flds);
				comps[i] = new TableComparison(cmp.getTableName(), mflds, cmp.getQuery());
				return this;
			}
		}
		throw new IllegalStateException("cant find table " + tableName);
	}

	public TableComparison[] getTableComparisons() {
		return comps;
	}
	
	public synchronized TableComparisonContainer add(TableComparison comp) {
		for (int i = 0; i < comps.length; i++) {
			TableComparison cmp = comps[i];
			if (cmp.getTableName().equals(comp.getTableName())) {
				comps[i] = comp;
				return this;
			}
		}
		internalAdd(comp);
		return this;
	}
	
	private synchronized void internalAdd(TableComparison comp) {
		TableComparison[] actualComp = getTableComparisons();
		TableComparison[] newArr = new TableComparison[actualComp.length + 1];
		System.arraycopy(actualComp, 0, newArr, 0, actualComp.length);
		newArr[actualComp.length] = comp;
		this.comps = newArr;
	}
	
	public TableComparisonContainer excludeFromAll(String[] flds) {
		for (int i = 0; i < comps.length; i++) {
			TableComparison cmp = comps[i];
			String[] mflds = ArrayUtils.concat(cmp.getExcludeFields(), flds);
			comps[i] = new TableComparison(cmp.getTableName(), mflds, cmp.getQuery());
		}
		return this;
	}

}
