/*
 * Creato il 25-feb-2009
 *
 */
package it.tasgroup.dse.view;

import java.io.Serializable;

/**
 * @author agostino
 */
public class DBReference implements Serializable{
	private static final long serialVersionUID = -2072988163288547750L;

	private String tableName; // nome tabelaa sui cui è presente il blob
	
	private String fieldName; // nome campo con il blob contenente l'xml.
	
	private DbKey dbKey;
	
	public DbKey getDbKey(){
		return dbKey;
	} 
	/**
	 * @param tableName
	 * @param keyValue
	 * @param fieldName
	 */
	public DBReference(String _tableName, String[][] keyPairStrings, String _fieldName) {
		super();
		this.tableName = _tableName;
		this.fieldName = _fieldName;
		this.dbKey= new DbKey(keyPairStrings);
	}
	
	/**
	 * @return Restituisce il valore fieldName.
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * @return Restituisce il valore tableName.
	 */
	public String getTableName() {
		return tableName;
	}

	
	/* (non Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("DBReference[");
		buf.append(" tableName: " + tableName);
		for (short i=0;i< this.dbKey.getNumberOfColumns();i++){
			
			buf.append(", keyName[]:" +  dbKey.getPair(i)[0]);
			buf.append(", keyValue[]:" + dbKey.getPair(i)[1]);
		
		}

		buf.append(", fieldName: " + fieldName);
		buf.append("]");
		return buf.toString();
	}
}
