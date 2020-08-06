/**
 * 21/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.record;

import it.nch.eb.flatx.flatmodel.conversioninfo.IXPathToObjectConversionInfo;
import it.nch.eb.xsqlcmd.dbtrpos.gen.model.FlussiModel;
import it.nch.eb.xsqlcmd.dbtrpos.gen.record.FlussiRecord;

/**
 * @author gdefacci
 */
public class DestinatariRecord extends it.nch.eb.xsqlcmd.dbtrpos.gen.record.DestinatariRecord {

	private static final long serialVersionUID = 1L;
	
	public DestinatariRecord(String operation) {
		super(operation);
	}

	public final IXPathToObjectConversionInfo flusso     = builder.create( 
			new FlussiRecord(getChildPath()), 
			FlussiModel.class );

}
