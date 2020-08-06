package it.nch.fwk.fo.base.text;
/*
 * File: DataSorterInterface.java
 * Package: com.etnoteam.service.text
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 26-ott-03 - 12.23.50
 * Created by: finsaccanebbia (Etnoteam)
 */
 
public interface DataSorterInterface {
	
	/* separatore costruzione chiavi */
	char SEP = '_';
	
	/* XML nomi tag */
	String TAG_DATASORTER = "DATASORTER";
	String TAG_SORTER = "SORTER";
	String TAG_FACTORY = "FACTORY";
	String TAG_CLASS = "CLASS";		
	String TAG_VALUE = "VALUE";		
    
	/* XML nomi attributi */
	
	String ATTRIB_TYPE = "type";	
	
	String ATTRIB_NAME = "name";	
	String ATTRIB_COLUMN = "columnName";
	String ATTRIB_ALTERNATE = "altColumn";
	String ATTRIB_ORDER = "order";
	
	/* chiavi ausiliarie */
	String KEY_LOCALE = "locale";
}
