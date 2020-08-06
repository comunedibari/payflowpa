/**
 * Created on 18/giu/08
 */
package it.nch.eb.common.converter.pmtreq.likepc.parser;

import it.nch.eb.common.converter.RecordImplParsersFactory;
import it.nch.eb.common.converter.pmtreq.likepc.models.LikePcBodyModel;
import it.nch.eb.common.converter.pmtreq.likepc.models.LikePcModel;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.flatfile.parser.AbstaractParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;


/**
 * @author gdefacci
 */
public class LikePcParsersFactroy extends RecordImplParsersFactory implements it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory {
	
	private static final long	serialVersionUID	= -2591361173308892601L;

	public static interface SNames {
		String BODY = "LikePcBodyModel";
		String MAIN = "LikePcModel";
	}
	
	public LikePcParsersFactroy() {
		super();
	}

	public LikePcParsersFactroy(AbstaractParserDsl parserDsl) {
		super(parserDsl);
	}

	public LikePcParsersFactroy(ConvertersMapper convertersMapper) {
		super(convertersMapper);
	}

	public IParser createParser() {
		IParser[] parsers = createMainParsers();
		return parserDsl().createParser(SNames.MAIN, LikePcModel.class, parsers);
	}
	
	public IParser[] createMainParsers() {
		return new IParser[] {
			createRecordTestaParser(),
			createBodyParser(),
			createRecordCodaParser(),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.RecordXF1(),  it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF1Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.RecordXF2(),  it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF2Model.class),		
		};
	}

	public IParser createRecordCodaParser() {
		return createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.RecordEF(),  it.nch.eb.common.converter.pmtreq.likepc.models.RecordEFModel.class  );
	}

	public IParser createRecordTestaParser() {
		return createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.RecordPC(),  it.nch.eb.common.converter.pmtreq.likepc.models.RecordPCModel.class  );
	}
	
	public IParser[] createBodyParsers() {
		return new IParser[] {
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record10(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record10Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record16(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record16Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record17(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record17Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record20(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record20Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record30(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record30Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record40(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record40Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record50(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record50Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record60(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record60Model.class),
			createParser( new it.nch.eb.common.converter.pmtreq.likepc.records.Record70(),  it.nch.eb.common.converter.pmtreq.likepc.models.Record70Model.class),
		};
	}
	
	public IParser createBodyParser() {
		IParser[] parsers = createBodyParsers();
		return parserDsl().createParser(SNames.BODY, LikePcBodyModel.class, parsers);
	}

}
