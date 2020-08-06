package it.nch.eb.flatx.generator.xls.xmlrecord;

/**
 * @author gdefacci
 */
public class UpdateStatementModel {
	
	private final String statementName;
	private final XRowInfo[] updateColumns;
	private final XRowInfo[] pkColumns;
	
	public UpdateStatementModel(String statementName, 
			XRowInfo[] updateColumns,
			XRowInfo[] pkColumns) {
		this.statementName = statementName;
		this.updateColumns = updateColumns;
		this.pkColumns = pkColumns;
	}
	
	public boolean getNonEmpty() {
		return updateColumns.length > 0;
	}
	
	public String getStatementName() {
		return statementName;
	}

	public XRowInfo[] getUpdateColumns() {
		return updateColumns;
	}

	public XRowInfo[] getPkColumns() {
		return pkColumns;
	}
	
}
