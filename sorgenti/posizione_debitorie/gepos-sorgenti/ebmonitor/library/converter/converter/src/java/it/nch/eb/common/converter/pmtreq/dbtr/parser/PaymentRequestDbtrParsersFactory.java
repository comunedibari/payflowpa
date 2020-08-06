/**
 * Created on 26/feb/08
 */
package it.nch.eb.common.converter.pmtreq.dbtr.parser;

import it.nch.eb.common.converter.RecordImplParsersFactory;
import it.nch.eb.common.converter.common.models.RecordCodaModel;
import it.nch.eb.common.converter.pmtreq.dbtr.models.EsitiParserModel;
import it.nch.eb.common.converter.pmtreq.dbtr.models.PmtReqDbtrEnvelope;
import it.nch.eb.common.converter.pmtreq.dbtr.models.PmtReqDbtrTrxInfo;
import it.nch.eb.common.converter.pmtreq.dbtr.models.Record20Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.Record22Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.Record32Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.Record34Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.Record36Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.RecordCodaBodyModel;
import it.nch.eb.common.converter.pmtreq.dbtr.models.RecorddiTestaModel;
import it.nch.eb.common.converter.pmtreq.dbtr.models.XmlRecordTestaBodyModel;
import it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlPmtReqDbtrStatusInfo;
import it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord01Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord30Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord40Model;
import it.nch.eb.common.converter.pmtreq.dbtr.models.xml.XmlRecord80Model;
import it.nch.eb.common.converter.pmtreq.dbtr.records.EsitiRecordCoda;
import it.nch.eb.common.converter.pmtreq.dbtr.records.EsitiRecordTesta;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record01;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record20;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record22;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record30;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record32;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record34;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record36;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record40;
import it.nch.eb.common.converter.pmtreq.dbtr.records.Record80;
import it.nch.eb.common.converter.pmtreq.dbtr.records.RecordCodaBody;
import it.nch.eb.common.converter.pmtreq.dbtr.records.RecordTestaBody;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.flatfile.parser.AbstaractParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;


/**
 * @author gdefacci
 */
public class PaymentRequestDbtrParsersFactory extends RecordImplParsersFactory implements it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory {
	
	private static final long	serialVersionUID	= 4982847847648545245L;

	public static interface Names {
		public static final String	DBTR_PMTREQ	= "EsitiParserModel";
		public static final String	DBTR_TRX_INFO	= "PmtReqDbtrTrxInfo";
		public static final String	DBTR_STATUS_INFO	= "PmtReqDbtrStatusInfo";
		public static final String	DBTR_ENVELOPE	= "PmtReqDbtrEnvelope";
	};
	
	public PaymentRequestDbtrParsersFactory() {
		super();
	}

	public PaymentRequestDbtrParsersFactory(AbstaractParserDsl parserDsl) {
		super(parserDsl);
	}
	
	public PaymentRequestDbtrParsersFactory(ConvertersMapper convertersMapper) {
		super(convertersMapper);
	}

	public IParser createParser() {
		IParser[] parsers = {
			createRecordTestaParser(),
			createRecordTestaBodyParser(),
			createBodySequenceParser(),
			createRecordCodaBodyParser(),
			createRecordCodaParser(),
		};
		return parserDsl().createOptionalParser(Names.DBTR_PMTREQ, EsitiParserModel.class, parsers);
	}

	public IParser createBodySequenceParser() {
		return parserDsl().any(create_01_80_FileParser());
	}

	public IParser createRecordTestaBodyParser() { // wrong
		return createParser(new RecordTestaBody(), XmlRecordTestaBodyModel.class);
	}

	public IParser createRecordCodaParser() {
		return parserDsl().createParser("recordCoda", new EsitiRecordCoda(), RecordCodaModel.class);
	}

	public IParser createRecordTestaParser() {
		return parserDsl().createParser("recorddiTesta", new EsitiRecordTesta(), RecorddiTestaModel.class);
	}

	public IParser createRecordCodaBodyParser() {
		return parserDsl().createParser(new RecordCodaBody(), RecordCodaBodyModel.class);
	}
	
	public IParser create_01_80_FileParser() {
		IParser[] parsers = {
			createParser(new Record01(), XmlRecord01Model.class),
			parserDsl().any(create_20_22_FileParser()),
			parserDsl().any(create_30_80_FileParser()),
		};
		return parserDsl().createParser(Names.DBTR_ENVELOPE, PmtReqDbtrEnvelope.class, parsers);
	}
	
	public IParser[] create_20_22_parsers() {
		return new IParser[] {
			createParser(new Record20(), Record20Model.class),
			parserDsl().any(createParser(new Record22(), Record22Model.class))
		};
	}
	
	public IParser create_20_22_FileParser() {
		return parserDsl().createParser(Names.DBTR_TRX_INFO, PmtReqDbtrTrxInfo.class, create_20_22_parsers() );
	}
	
	public IParser[] create_30_80_parsers() {
		return new IParser[] {
			createParser(new Record30(), XmlRecord30Model.class),
			parserDsl().any(createParser(new Record32(), Record32Model.class)),
			parserDsl().any(createParser(new Record34(), Record34Model.class)),
			parserDsl().any(createParser(new Record36(), Record36Model.class)),
			createParser(new Record40(), XmlRecord40Model.class),
			parserDsl().any( createRecord80Parser() ),
		};
	}

	public IParser createRecord80Parser() {
		return createParser(new Record80(), XmlRecord80Model.class);
	}
	
	public IParser create_30_80_FileParser() {
		return parserDsl().createParser(Names.DBTR_STATUS_INFO, 
				XmlPmtReqDbtrStatusInfo.class, 
				create_30_80_parsers());
	}

}
