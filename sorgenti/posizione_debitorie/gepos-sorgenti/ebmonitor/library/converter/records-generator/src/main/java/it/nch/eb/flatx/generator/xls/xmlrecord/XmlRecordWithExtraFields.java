/**
 * 
 */
package it.nch.eb.flatx.generator.xls.xmlrecord;

import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.generator.javamodel.FieldDecl;

/**
 * @author gdefacci
 *
 */
public class XmlRecordWithExtraFields {
	
	private final XmlRecord xmlRecord;
	private final FieldDecl[] extraFields;

	public XmlRecordWithExtraFields(XmlRecord xmlRecord, FieldDecl[] extraFields) {
		this.xmlRecord = xmlRecord;
		this.extraFields = extraFields!=null ? extraFields : new FieldDecl[0];
	}

	public XmlRecord getXmlRecord() {
		return xmlRecord;
	}

	public FieldDecl[] getExtraFields() {
		return extraFields;
	}
	
	public static XmlRecordWithExtraFields create(XmlRecord rec) {
		return new XmlRecordWithExtraFields(rec, null);
	}
	
	public static XmlRecordWithExtraFields create(XmlRecord rec, String[][] extraFlds ) {
		FieldDecl[] extras = null;
		if (extraFlds!=null && extraFlds.length > 0) {
			extras = new FieldDecl[extraFlds.length];
			for (int i = 0; i < extraFlds.length; i++) {
				String[] nameTypePair = extraFlds[i];
				if (nameTypePair.length!=2) throw new IllegalStateException("not a extra field " + nameTypePair);
				extras[i] = new FieldDecl(nameTypePair[0], nameTypePair[1]);
			}
		}
		
		return new XmlRecordWithExtraFields(rec, extras);
	}

}
