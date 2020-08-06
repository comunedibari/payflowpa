/**
 * 29/mag/2009
 */
package it.nch.eb.xsqlcmd.dbtrpos.record;

import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.xsqlcmd.dbtrpos.dao.IdPDocumentXPaths;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;

/**
 * @author gdefacci
 */
public class PendenzeInclusionsElementPredicate implements ElementPredicate {
	
	private static final XPathPosition idPendenzaEnteXPath = IdPDocumentXPaths.pendenzaEnte;  
	
	private final PendenzeInclusions pendenzeInclusions;
	
	public PendenzeInclusionsElementPredicate(PendenzeInclusions pendenzeInclusions) {
		this.pendenzeInclusions = pendenzeInclusions;
	}

	/* @Override */
	public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
		XPathPosition realXPath = XPathUtils.sharedInstance.adaptIndexes(pos, idPendenzaEnteXPath);
		String idPendenzaEnte = elemsMap.get(realXPath);
		if (idPendenzaEnte==null) throw new IllegalStateException("Missing mandatory xpath " + realXPath);
		if (pendenzeInclusions.include(idPendenzaEnte)) return true;
		else return false;
	}

}
