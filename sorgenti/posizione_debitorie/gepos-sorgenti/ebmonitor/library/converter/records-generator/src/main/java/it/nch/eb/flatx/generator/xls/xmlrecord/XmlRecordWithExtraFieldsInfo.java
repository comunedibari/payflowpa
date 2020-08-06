/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.flatx.generator.javamodel.FieldDecl;

/**
 * @author gdefacci
 *
 */
public class XmlRecordWithExtraFieldsInfo {
	
	private final String xmlRecord;
	private final FieldDecl[] extraFields;

	public XmlRecordWithExtraFieldsInfo(String xmlRecord, FieldDecl[] extraFields) {
		this.xmlRecord = xmlRecord;
		this.extraFields = extraFields != null ? extraFields : new FieldDecl[0];
	}

	public String getXmlRecord() {
		return xmlRecord;
	}

	public FieldDecl[] getExtraFields() {
		return extraFields;
	}
	
	public static XmlRecordWithExtraFieldsInfo create(String rec) {
		return new XmlRecordWithExtraFieldsInfo(rec, null);
	}
	
	public static XmlRecordWithExtraFieldsInfo create(String rec, String[][] extraFlds ) {
		FieldDecl[] extras = new FieldDecl[extraFlds.length];
		for (int i = 0; i < extraFlds.length; i++) {
			String[] nameTypePair = extraFlds[i];
			if (nameTypePair.length!=2) throw new IllegalStateException("not a extra field " + nameTypePair);
			extras[i] = new FieldDecl(nameTypePair[0], nameTypePair[1]);
		}
		
		return new XmlRecordWithExtraFieldsInfo(rec, extras);
	}

}
