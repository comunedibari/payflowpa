/**
 * 07/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.dao;

import java.util.Map;

/**
 * @author gdefacci
 */
public class TableNamesDecoder implements TableNames {
	
	private final TableNames delegate;
	private final Map decodeMap;
	
	public TableNamesDecoder(Map decodeMap) {
		this(TableNames.locical, decodeMap);
	}
	
	private TableNamesDecoder(TableNames delegate, Map decodeMap) {
		this.delegate = delegate;
		this.decodeMap = decodeMap;
	}
	
	private String mapStringValue(String logicName) {
		Object res = decodeMap.get(logicName);
		if (res == null) return null;
		return ((String) res).trim();
	}
	
	/* @Override */
	public String getAllineamenti() {
		return mapStringValue(delegate.getAllineamenti());
	}
	/* @Override */
	public String getPendenze() {
		return mapStringValue(delegate.getPendenze());
	}
	/* @Override */
	public String getDestinatari() {
		return mapStringValue(delegate.getDestinatari());
	}
	/* @Override */
	public String getCondizioniPagamento() {
		return mapStringValue(delegate.getCondizioniPagamento());
	}
	/* @Override */
	public String getVociBilancio() {
		return mapStringValue(delegate.getVociBilancio());
	}
	/* @Override */
	public String getAllegato() {
		return mapStringValue(delegate.getAllegato());
	}
	/* @Override */
	public String getFlussi() {
		return mapStringValue(delegate.getFlussi());
	}
	
	public String getErroriEsitiPendenza() {
		return mapStringValue(delegate.getErroriEsitiPendenza());
	}

	public String getEsitiPendenza() {
		return mapStringValue(delegate.getEsitiPendenza());
	}
	
	public String getPendenzeCart() {
		return mapStringValue(delegate.getPendenzeCart());
	}

	public String[] getAllTableNames() {
		return BaseTableNames.getAllTableNames(this);
	}
	
	public Map entries() {
		return decodeMap;
	}

}
