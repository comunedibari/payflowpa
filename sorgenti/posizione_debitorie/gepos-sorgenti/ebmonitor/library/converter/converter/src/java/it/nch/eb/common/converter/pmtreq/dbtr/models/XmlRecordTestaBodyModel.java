/**
 * Created on 13/mag/08
 */
package it.nch.eb.common.converter.pmtreq.dbtr.models;


/**
 * @author gdefacci
 */
public class XmlRecordTestaBodyModel extends RecordTestaBodyModel {
	
	public boolean isFinInstnId() {
		return this.getBic() != null || this.getPrtry() != null;
	}

}
