/**
 * Created on 20/giu/08
 */
package it.nch.eb.ubi.converter.pmtreq.parser;

import it.nch.eb.common.converter.pmtreq.models.Record00Model;
import it.nch.eb.common.converter.pmtreq.models.Record01Model;
import it.nch.eb.common.converter.pmtreq.models.RecordFIModel;
import it.nch.eb.common.converter.pmtreq.models.RecordTestaBodyModel;
import it.nch.eb.common.converter.pmtreq.parser.PmtreqParsersFactory;
import it.nch.eb.common.converter.pmtreq.records.RecordFI;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.flatfile.parser.AbstaractParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.ubi.converter.pmtreq.models.RecorddiTestaModel;


/**
 * @author gdefacci
 */
public class UbiPmtreqParserFactory  extends PmtreqParsersFactory {

	private static final long	serialVersionUID	= 3023791313377591533L;

	public UbiPmtreqParserFactory() {
		super();
	}

	public UbiPmtreqParserFactory(final AbstaractParserDsl parserDsl) {
		super(parserDsl);
	}

	public UbiPmtreqParserFactory(final ConvertersMapper convertersMapper) {
		super(convertersMapper);
	}

	public IParser createRecordTestaParser() {
		return createParser(new it.nch.eb.ubi.converter.pmtreq.records.RecorddiTesta(), RecorddiTestaModel.class);
	}

	public IParser createTestaBodyParser() {
		return createParser(new it.nch.eb.ubi.converter.pmtreq.records.RecordTestaBody(), RecordTestaBodyModel.class);
	}
	
	public IParser createRecord00Parser() {
		return parserDsl().createOptionalParser(new it.nch.eb.ubi.converter.pmtreq.records.Record00(), Record00Model.class);
	}
	
	public IParser createRecordFIParser() {
		return parserDsl().createOptionalParser(new RecordFI(), RecordFIModel.class);
	}
	
	protected IParser createRecord01Parser() {
		return createParser(new it.nch.eb.ubi.converter.pmtreq.records.Record01(), Record01Model.class);
	}

	public IParser createBodyParser() {
		return parserDsl().createParser(ModelsNames.PMTREC_BODY, 
				it.nch.eb.ubi.converter.pmtreq.models.PmtreqBody.class, 
				createBodyParsers());
	}
	
	public IParser[] createBodyParsers() {
		return new IParser[] {
			createTestaBodyParser(),
			createRecord00Parser(),
			createBodySeqParser(),
			createRecordFIParser(),
			createCodaBodyParser(),
		};
	}

	public IParser createParser() {
		IParser[] parsers = createMainIParsers();
		return parserDsl().createOptionalParser(ModelsNames.PMTREC, 
				it.nch.eb.ubi.converter.pmtreq.models.PmtreqDocument.class, 
				parsers);
	}

	public IParser[] createMainIParsers() {
		IParser[] parsers = {
			createRecordTestaParser(),
			createBodySequenceParser(),
			createCodaParser(),
		};
		return parsers;
	}

}
