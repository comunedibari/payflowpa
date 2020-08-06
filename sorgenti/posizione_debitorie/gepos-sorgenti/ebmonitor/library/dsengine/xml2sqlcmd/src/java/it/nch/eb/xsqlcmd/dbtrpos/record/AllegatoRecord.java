/**
 * 23/apr/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.record;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.xsqlcmd.dbtrpos.dao.IdPDocumentXPaths;

/**
 * @author gdefacci
 */
public class AllegatoRecord extends it.nch.eb.xsqlcmd.dbtrpos.gen.record.AllegatoRecord {

	private static final long serialVersionUID = 6477863931927195571L;

	public AllegatoRecord(String xpath) {
		super(xpath);
	}
	
	public AllegatoRecord(String operation, String sfx) {
		this(IdPDocumentXPaths.PENDENZA_XPATH_STRING, operation , sfx);
	}
	
	public AllegatoRecord(String base, String operation, String sfx) {
		super(StringUtils.concatPaths( new String[] { base , operation , sfx } ));
	}
	
}
