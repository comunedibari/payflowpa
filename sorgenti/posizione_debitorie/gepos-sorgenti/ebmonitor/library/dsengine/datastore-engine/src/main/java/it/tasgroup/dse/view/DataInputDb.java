package it.tasgroup.dse.view;

import it.nch.eb.common.utils.loaders.InputStreamFactory;
import it.tasgroup.dse.dao.StorageDAO;
import it.tasgroup.dse.service.DseEnviroments;
import it.tasgroup.dse.service.SpringNames;

import java.io.InputStream;

import javax.sql.DataSource;



/**
 * @author agostino
 */
public class DataInputDb implements DataInput {
	
	private static final long serialVersionUID = -152493606291117380L;
	
	private DBReference xmlDBBlob = null;
	private StorageDAO dao;
  
	public DataInputDb(DBReference xmlDBBlob) {
		this((DataSource) DseEnviroments.DEFAULT_ENVIROMENT.apply().getBeanFactory().getBean(SpringNames.BeansNames.DATASOURCE), xmlDBBlob);
	}
	/**
	 * @param xmlDBBlob
	 */
	public DataInputDb(DataSource ds, DBReference xmlDBBlob) {
		super();
		this.xmlDBBlob = xmlDBBlob;
		this.dao = new StorageDAO(ds);
	}
	
	/**
	 * @return Restituisce il valore dataRetrievingMode.
	 */
//	public int getDataRetrievingMode() {
//		return DRM_DB;
//	}
	
	/**
	 * @return Restituisce il valore xmlDBBlob.
	 */
	public DBReference getXmlDBBlob() {
		return xmlDBBlob;
	}

	/* (non Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuffer buf = new StringBuffer();
		buf.append("DataInput[");
//		buf.append(" dataRetrievingMode: " + DRM_DB);
		buf.append(", xmlDBBlob: " + xmlDBBlob);
		buf.append("]");
		return buf.toString();
	}

	/*
	 * DBReference xmlDBBlob = dataInputDb.getXmlDBBlob();
					InputStream is = dao.getInputStreamBlob(xmlDBBlob.getTableName(), xmlDBBlob.getDbKey().getValues(), 
							DbKey.COL_NAMES, xmlDBBlob.getFieldName());
	 */
	
	public InputStream createStream() {
		try {
			DBReference xmlDBBlob = getXmlDBBlob();
			InputStream is = dao.getInputStreamBlob(xmlDBBlob.getTableName(), xmlDBBlob.getDbKey().getValues(), 
					DbKey.COL_NAMES, xmlDBBlob.getFieldName());
			return is;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
