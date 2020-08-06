package it.nch.eb.common.converter.pmtreq.cdtr.parser;

import it.nch.eb.common.converter.RecordImplParsersFactory;
import it.nch.eb.common.converter.common.models.RecordCodaModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.CdtrBody;
import it.nch.eb.common.converter.pmtreq.cdtr.models.CdtrPaymentRequest;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record01Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record32Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record60Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record66Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record67Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record68Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record69Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record86Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.RecordCodaBodyModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.RecordTestaBodyModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.RecorddiTestaModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.Record30sModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord30Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord62Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord65Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord80Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord82Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord84Model;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord40sModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord62sModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord66sModel;
import it.nch.eb.common.converter.pmtreq.cdtr.models.xml.XmlRecord68sModel;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record01;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record30;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record32;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record40;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record60;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record62;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record65;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record66;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record67;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record68;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record69;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record80;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record82;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record84;
import it.nch.eb.common.converter.pmtreq.cdtr.records.Record86;
import it.nch.eb.common.converter.pmtreq.cdtr.records.RecordCodaBody;
import it.nch.eb.common.converter.pmtreq.cdtr.records.RecordTestaBody;
import it.nch.eb.common.converter.pmtreq.dbtr.records.EsitiRecordCoda;
import it.nch.eb.common.converter.pmtreq.dbtr.records.EsitiRecordTesta;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;

public class PaymentRequestCdtrParsersFactory  extends RecordImplParsersFactory implements ParsersFactory{
	private static final long serialVersionUID = 1L;

	public static interface Names {
		String XML_RECORD_30 		= "XmlRecord30Model";
		String XML_RECORD_40 		= "XmlRecord40Model";
		String XML_RECORD_62		= "XmlRecord62Model";
		String XML_RECORD_66		= "XmlRecord66Model";
		String XML_RECORD_68		= "XmlRecord68Model";
		String CDTR_PMTREC 		= "CdtrPaymentRequest";
		String CDTR_BODY 		= "CdtrBody";
	}	
	
	public PaymentRequestCdtrParsersFactory() {
		super();
	}

	public PaymentRequestCdtrParsersFactory(ConvertersMapper convertersMapper) {
		super(convertersMapper);
	}

	public IParser createParser() {
		return parserDsl().createParser(
				Names.CDTR_PMTREC, 
				CdtrPaymentRequest.class,
				createMainParsers());
	}

	private IParser[] createMainParsers() {
		IParser[] parsers = new IParser[] {
				createRecordTestaParser(),
				createBodiesParser(),
				createRecordCodaParser(),
			};
		return parsers;
	}

	public IParser createRecordCodaParser() {
		return parserDsl().createParser("recordCoda", new EsitiRecordCoda("/CBICdtrPmtStatusReportMsg", "EH"), RecordCodaModel.class);
	}

	public IParser createRecordTestaParser() {
		return parserDsl().createParser("recorddiTesta", new EsitiRecordTesta("/CBICdtrPmtStatusReportMsg/CBIHdrSrv", "SH"), RecorddiTestaModel.class);
	}

	public IParser createBodiesParser() {
		return parserDsl().atLeast(1, createParserBody());
	}

	public IParser createParserBody() {
		IParser[] parsers = new IParser[] {
				createParser(new RecordTestaBody(), RecordTestaBodyModel.class),
				createParser(new Record01(), Record01Model.class),
				parserDsl().any(createParser30s()),
				createParser(new RecordCodaBody(), RecordCodaBodyModel.class),
			};
		return parserDsl().createParser(Names.CDTR_BODY, 
				CdtrBody.class,
				parsers);		
	}
	
	public IParser createParser30s() {
		return parserDsl().createParser(Names.XML_RECORD_30, 
				Record30sModel.class,
				create30sParsers());
	}

	private IParser[] create30sParsers() {
		IParser[] parsers = new IParser[] {
			createParser(new Record30(), XmlRecord30Model.class),
			parserDsl().any(createParser(new Record32(), Record32Model.class)),
			createParser40s(),
		};
		return parsers;
	}
	
	public IParser createParser40s() {
		return parserDsl().createParser(Names.XML_RECORD_40, 
				XmlRecord40sModel.class,
				create40sParsers());
	}	

	private IParser[] create40sParsers() {
		IParser[] parsers = new IParser[] {
			parserDsl().createParser(new Record40(), XmlRecord40Model.class),
			parserDsl().any(createParser(new Record60(), Record60Model.class)),
			parserDsl().any(createParser62s()),
			parserDsl().createOptionalParser(new Record80(), XmlRecord80Model.class),
			parserDsl().createOptionalParser(new Record82(), XmlRecord82Model.class),
			parserDsl().createOptionalParser(new Record84(), XmlRecord84Model.class),
			parserDsl().createOptionalParser(new Record86(), Record86Model.class),
		};
		return parsers;
	}			
	
	public IParser createParser62s() {
		return parserDsl().createParser(Names.XML_RECORD_62, 
				XmlRecord62sModel.class,
				create62sParsers());
	}

	private IParser[] create62sParsers() {
		IParser[] parsers = new IParser[] {
			createParser(new Record62(), XmlRecord62Model.class),
			parserDsl().any(createParser(new Record65(), XmlRecord65Model.class)),
			createParser66s(),
			createParser68s()
		};
		return parsers;
	}
	
	public IParser createParser66s() {
		return parserDsl().createOptionalParser(Names.XML_RECORD_66, 
				XmlRecord66sModel.class,
				create66sParsers());
	}

	private IParser[] create66sParsers() {
		IParser[] parsers = new IParser[] {
			createParser(new Record66(), Record66Model.class),
			parserDsl().atMost(4, createParser(new Record67(), Record67Model.class)),
		};
		return parsers;
	}

	public IParser createParser68s() {
		return parserDsl().createOptionalParser(Names.XML_RECORD_68, 
				XmlRecord68sModel.class,
				create68sParsers());		
	}
	
	
	private IParser[] create68sParsers() {
		IParser[] parsers = new IParser[] {
			createParser(new Record68(), Record68Model.class),
			parserDsl().atMost(4, createParser(new Record69(), Record69Model.class)),
		};
		return parsers;
	}		
}
