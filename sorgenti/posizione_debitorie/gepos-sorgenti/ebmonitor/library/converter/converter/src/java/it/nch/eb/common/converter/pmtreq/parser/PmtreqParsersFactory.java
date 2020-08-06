/**
 * Created on 04/giu/08
 */
package it.nch.eb.common.converter.pmtreq.parser;

import it.nch.eb.common.converter.RecordImplParsersFactory;
import it.nch.eb.common.converter.common.models.RecordCodaModel;
import it.nch.eb.common.converter.common.records.RecordCoda;
import it.nch.eb.common.converter.common.records.RecordTesta;
import it.nch.eb.common.converter.pmtreq.models.PmtreqBody;
import it.nch.eb.common.converter.pmtreq.models.PmtreqBodyItem;
import it.nch.eb.common.converter.pmtreq.models.PmtreqDocument;
import it.nch.eb.common.converter.pmtreq.models.Record00Model;
import it.nch.eb.common.converter.pmtreq.models.Record01Model;
import it.nch.eb.common.converter.pmtreq.models.Record11Model;
import it.nch.eb.common.converter.pmtreq.models.Record20Model;
import it.nch.eb.common.converter.pmtreq.models.Record30Model;
import it.nch.eb.common.converter.pmtreq.models.Record40Model;
import it.nch.eb.common.converter.pmtreq.models.Record50Model;
import it.nch.eb.common.converter.pmtreq.models.Record60Model;
import it.nch.eb.common.converter.pmtreq.models.Record65Model;
import it.nch.eb.common.converter.pmtreq.models.Record70Model;
import it.nch.eb.common.converter.pmtreq.models.Record80Model;
import it.nch.eb.common.converter.pmtreq.models.RecordCodaBodyModel;
import it.nch.eb.common.converter.pmtreq.models.RecordTestaBodyModel;
import it.nch.eb.common.converter.pmtreq.records.Record00;
import it.nch.eb.common.converter.pmtreq.records.Record01;
import it.nch.eb.common.converter.pmtreq.records.Record11;
import it.nch.eb.common.converter.pmtreq.records.Record20;
import it.nch.eb.common.converter.pmtreq.records.Record30;
import it.nch.eb.common.converter.pmtreq.records.Record40;
import it.nch.eb.common.converter.pmtreq.records.Record50;
import it.nch.eb.common.converter.pmtreq.records.Record60;
import it.nch.eb.common.converter.pmtreq.records.Record65;
import it.nch.eb.common.converter.pmtreq.records.Record70;
import it.nch.eb.common.converter.pmtreq.records.Record80;
import it.nch.eb.common.converter.pmtreq.records.RecordCodaBody;
import it.nch.eb.common.converter.pmtreq.records.RecordTestaBody;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.flatfile.parser.AbstaractParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;


/**
 * @author gdefacci
 */
public class PmtreqParsersFactory extends RecordImplParsersFactory implements it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory {

	private static final long	serialVersionUID	= 6533402743190281598L;

	public static interface ModelsNames {
		String PMTREC_BODY 			= "PmtreqBody";
		String PMTREC_BODY_ITEM 	= "PmtreqBodyItem";
		String PMTREC 				= "PmtreqDocument";
	}
	
	public PmtreqParsersFactory() {
		super();
	}

	public PmtreqParsersFactory(AbstaractParserDsl parserDsl) {
		super(parserDsl);
	}

	public PmtreqParsersFactory(ConvertersMapper convertersMapper) {
		super(convertersMapper);
	}

	public IParser createParser() {
		IParser[] parsers = createMainIParsers();
		return parserDsl().createOptionalParser(ModelsNames.PMTREC, PmtreqDocument.class, parsers);
	}

	public IParser[] createMainIParsers() {
		IParser[] parsers = {
			createRecordTestaParser(),
			createBodySequenceParser(),
			createCodaParser(),
		};
		return parsers;
	}

	public IParser createRecordTestaParser() {
		return createParser(new RecordTesta(), it.nch.eb.common.converter.common.models.RecordTestaModel.class);
	}

	public IParser createCodaParser() {
		return createParser(new RecordCoda(), RecordCodaModel.class);
	}

	public IParser createCodaBodyParser() {
		return createParser(new RecordCodaBody(), RecordCodaBodyModel.class);
	}

	public IParser createTestaBodyParser() {
		return createParser(new RecordTestaBody(), RecordTestaBodyModel.class);
	}
	
	public IParser createBodySequenceParser() {
		return parserDsl().any(  createBodyParser() );
	}
	
	public IParser createBodySeqParser() {
		return parserDsl().any( createBodyItemParser() );
	}

	public IParser createBodyParser() {
		return parserDsl().createParser(ModelsNames.PMTREC_BODY, PmtreqBody.class, createBodyParsers());
	}

	public IParser[] createBodyParsers() {
		return new IParser[] {
			createTestaBodyParser(),
			createRecord00Parser(),
			createBodySeqParser(),
			createCodaBodyParser(),
		};
	}

	public IParser createRecord00Parser() {
		return parserDsl().createOptionalParser(new Record00(), Record00Model.class);
	}

	public IParser createBodyItemParser() {
		return parserDsl().createParser(
			ModelsNames.PMTREC_BODY_ITEM, 
			PmtreqBodyItem.class, 
			createBodyItemParsers());
	}

	public IParser[] createBodyItemParsers() {
		IParser[] parsers = {
			createRecord01Parser(),
			parserDsl().atMost(3, createParser(new Record11(), Record11Model.class)),
			createParser(new Record20(), Record20Model.class),
			parserDsl().createOptionalParser(new Record30(), Record30Model.class),
			parserDsl().any(createParser(new Record40(), Record40Model.class)),
			parserDsl().any(createParser(new Record50(), Record50Model.class)),
			parserDsl().any(createParser(new Record60(), Record60Model.class)),
			parserDsl().createOptionalParser(new Record65(), Record65Model.class),
			parserDsl().createOptionalParser(new Record70(), Record70Model.class),
			parserDsl().createOptionalParser(new Record80(), Record80Model.class),
		};
		return parsers;
	}

	protected IParser createRecord01Parser() {
		return createParser(new Record01(), Record01Model.class);
	}

	public String toString() {
		return it.nch.eb.common.utils.StringUtils.getSimpleName(getClass());
	}
	
}
