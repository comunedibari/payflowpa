/**
 * Created on 30/mag/08
 */
package it.nch.eb.common.converter.pmtreq.advinf.parser;

import it.nch.eb.common.converter.RecordImplParsersFactory;
import it.nch.eb.common.converter.pmtreq.advinf.models.AdvinfModel;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record02Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record04Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record06Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record07Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record09Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record10Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.Record11Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaBodyModel;
import it.nch.eb.common.converter.pmtreq.advinf.models.RecordCodaModel;
import it.nch.eb.common.converter.pmtreq.advinf.models.RecordTestaBodyModel;
import it.nch.eb.common.converter.pmtreq.advinf.models.RecorddiTestaModel;
import it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlAdvinfBodyModel;
import it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord01Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord03Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord05Model;
import it.nch.eb.common.converter.pmtreq.advinf.models.xml.XmlRecord08Model;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record01;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record02;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record03;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record04;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record05;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record06;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record07;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record08;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record09;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record10;
import it.nch.eb.common.converter.pmtreq.advinf.records.Record11;
import it.nch.eb.common.converter.pmtreq.advinf.records.RecorddiTesta;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.flatfile.parser.AbstaractParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;


/**
 * @author gdefacci
 */
public class AdvinfParserFactory  extends RecordImplParsersFactory implements it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory {
	
	private static final long	serialVersionUID	= -6602621993892643698L;

	public static interface Names {
		String ADVINF_BODY = "AdvinfBodyModel";
		String ADVINF = "AdvinfModel";
	}
	
	public AdvinfParserFactory() {
		super();
	}

	public AdvinfParserFactory(AbstaractParserDsl parserDsl) {
		super(parserDsl);
	}

	public AdvinfParserFactory(ConvertersMapper convertersMapper) {
		super(convertersMapper);
	}

	public IParser createBodyParser() {
		IParser[] parsers = createBodyParsers();
		return parserDsl().createParser(Names.ADVINF_BODY, XmlAdvinfBodyModel.class, parsers);
	}

	public IParser[] createBodyParsers() {
		IParser[] parsers = {
			createParser(new it.nch.eb.common.converter.pmtreq.advinf.records.RecordTestaBody(),  RecordTestaBodyModel.class),
			createParser(new Record01(),   XmlRecord01Model.class ),
			createParser(new Record02(),  Record02Model.class),
			createParser(new Record03(),  XmlRecord03Model.class),
			parserDsl().any( createParser(new Record04(),  Record04Model.class) ),
			parserDsl().createOptionalParser(new Record05(),  XmlRecord05Model.class),
			parserDsl().createOptionalParser(new Record06(),  Record06Model.class),
			parserDsl().createOptionalParser(new Record07(),  Record07Model.class),
			parserDsl().any( createParser(new Record08(),  XmlRecord08Model.class) ),
			parserDsl().createOptionalParser(new Record09(),  Record09Model.class),
			parserDsl().createOptionalParser(new Record10(),  Record10Model.class),
			parserDsl().createOptionalParser(new Record11(),  Record11Model.class),
			createParser(new it.nch.eb.common.converter.pmtreq.advinf.records.RecordCodaBody(),  RecordCodaBodyModel.class),
		};
		return parsers;
	}

	public IParser record20Parser() {
		return createParser(new Record02(),  Record02Model.class);
	}
	
	public IParser[] createParsers() {
		IParser[] parsers = {
			createRecordTestaParser(),
			createBodySequenceParser(),
			createRecordCodaParser(),
		};
		return parsers;
	}

	public IParser createBodySequenceParser() {
		return parserDsl().atLeast(1, createBodyParser());
	}

	public IParser createRecordCodaParser() {
		return createParser(new it.nch.eb.common.converter.pmtreq.advinf.records.RecordCoda(),  RecordCodaModel.class);
	}

	public IParser createRecordTestaParser() {
		return createParser(new RecorddiTesta(),   RecorddiTestaModel.class );
	}

	public IParser createParser() {
		IParser[] parsers = createParsers();
		return parserDsl().createParser(Names.ADVINF, AdvinfModel.class, parsers);
	}

}
