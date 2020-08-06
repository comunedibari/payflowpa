/**
 * Created on 15/gen/08
 */
package it.nch.eb.common.converter.pmtreq.records;

import it.nch.eb.common.converter.ConversionRecord;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathsMapBindinsManagerStringFunction;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;



/**
 * @author gdefacci
 */
public class Record65 extends ConversionRecord {

	private static final long	serialVersionUID	= -2962497296888308573L;

	public Record65() {
		super("/MSG:CBIPaymentRequestMsg/MSG:CBIBdyPaymentRequest/BODY:CBIEnvelPaymentRequest/BODY:CBIPaymentRequest/PMRQ:PmtInf/PMRQ:CdtTrfTxInf/PMRQ:RmtInf/PMRQ:Strd/PMRQ:RfrdDocAmt",
				"65");
	}
	
	public final ExpressionConversionInfo	fiiller				= createFixedValue(" ");
	public final ExpressionConversionInfo	tipoRec				= createGetBindingValue(fill2, TIPO_RECORD);
	public final ExpressionConversionInfo	recordCount			= createGetBindingValue(numb7, REC_COUNT, true);
	
	/**
	 * fixme recover
	 */
//	public final Converter  rec = create(fill29, or()
//		.or("PMRQ:DuePyblAmt", new TipoImportoAndImportoAndDivisa("TOT")) 
//		.or("PMRQ:DscntApldAmt", new TipoImportoAndImportoAndDivisa("SCT")) 
//		.or("PMRQ:RmtdAmt", new TipoImportoAndImportoAndDivisa("PAG")) 
//		.or("PMRQ:CdtNoteAmt", new TipoImportoAndImportoAndDivisa("NOT")) 
//		.or("PMRQ:TaxAmt", new TipoImportoAndImportoAndDivisa("TAX")) 
//	);

//	public final Converter	lineTerminator		= createLineTerminator();
	
	static final class TipoImportoAndImportoAndDivisa implements XPathsMapBindinsManagerStringFunction {

		final XPathPosition xpath; 
		final String tipoImporto; 
		
		public TipoImportoAndImportoAndDivisa(String xpath, String tipoImporto) {
			this.xpath = (XPathPosition) XPathsParser.instance.parseXPath(xpath);
			this.tipoImporto = tipoImporto;
		}

		public String getValue(XPathsMapBindings map) {
			String value = map.get(xpath);
			BaseXPathPosition ccyXpath = xpath.attribute("Ccy");
			String ccy = map.get(ccyXpath);
			if (value==null) throw new RuntimeException("cant find xpath " + xpath + " on element " + map);
			if (ccy==null) throw new RuntimeException("cant find xpath " + ccyXpath + " on element " + map);
			return fill3.encode(tipoImporto) + fd_number23_5.encode(value) + fill3.encode(ccy);
		}

		public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
			return getValue(elemsMap);
		}
	}
	

}
