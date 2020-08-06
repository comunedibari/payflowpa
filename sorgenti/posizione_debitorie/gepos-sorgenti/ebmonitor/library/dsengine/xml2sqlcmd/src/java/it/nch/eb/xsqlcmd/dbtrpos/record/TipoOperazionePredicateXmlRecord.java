package it.nch.eb.xsqlcmd.dbtrpos.record;

import it.nch.eb.flatx.flatmodel.IMappedXMLRecord;
import it.nch.eb.flatx.flatmodel.MappedXmlRecord;
import it.nch.eb.flatx.flatmodel.PredicateXmlRecordPair;
import it.nch.eb.flatx.flatmodel.conversioninfo.AndElementPredicate;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathUtils;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeInclusions;
import it.nch.eb.xsqlcmd.dbtrpos.model.PendenzeModel;
import it.nch.eb.xsqlcmd.dbtrpos.model.OperationKinds.OperationKind;

/**
 * @author gdefacci
 */
public class TipoOperazionePredicateXmlRecord implements
		PredicateXmlRecordPair {
	
	private final BaseXPathPosition tipoOperazioneXpath;
	private final OperationKind operationKind;
	
	private PendenzeInclusionsElementPredicate pendenzeInclusionPredicate;
	
	public TipoOperazionePredicateXmlRecord(
			OperationKind operationKind, 
			BaseXPathPosition tipoOperazioneXpath,
			PendenzeInclusions pendenzeInclusions) {
		this.operationKind = operationKind;
		this.tipoOperazioneXpath = tipoOperazioneXpath;
		this.pendenzeInclusionPredicate = new PendenzeInclusionsElementPredicate(pendenzeInclusions);
	}

	public ElementPredicate getElementPredicate() {
		return new AndElementPredicate( 
			new ElementPredicate[] {
				this.pendenzeInclusionPredicate,
				new ElementPredicate() {
		
					public boolean match(XPathPosition pos,
							IXPathMapScope elemsMap) {
						return getTipoOperazioneValue(pos, elemsMap).equals(operationKind.getAbbr());
					}
					
				} 
			});
	}

	public IMappedXMLRecord getXmlRecord() {
		return new MappedXmlRecord(PendenzeModel.class, new PendenzeRecord( operationKind.getName()));
	}
		
	private String getTipoOperazioneValue(XPathPosition pos, IXPathMapScope elemsMap) {
		BaseXPathPosition realXPath = XPathUtils.sharedInstance.adaptIndexes(pos, tipoOperazioneXpath);
		String tipoOp = elemsMap.get(realXPath);
		if (tipoOp==null) throw new IllegalStateException("missing mandatory xpath (tipo operazione)" + realXPath + "\ninside map:\n" + elemsMap.toString());
		return PendenzeConverters.tipoOperazioneConverter.encode(  tipoOp );
	}
}