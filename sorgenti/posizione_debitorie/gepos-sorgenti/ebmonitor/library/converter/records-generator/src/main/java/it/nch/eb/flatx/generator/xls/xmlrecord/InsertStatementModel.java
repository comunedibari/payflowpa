package it.nch.eb.flatx.generator.xls.xmlrecord;

/**
 * @author gdefacci
 */
public class InsertStatementModel {
	
	private final String statementName;
	private final XRowInfo[] columns;
	
	public InsertStatementModel(String statementName, 
			XRowInfo[] columns) {
		this.statementName = statementName;
		this.columns = columns;
	}

	public String getStatementName() {
		return statementName;
	}

	public XRowInfo[] getColumns() {
		return columns;
	}

}
