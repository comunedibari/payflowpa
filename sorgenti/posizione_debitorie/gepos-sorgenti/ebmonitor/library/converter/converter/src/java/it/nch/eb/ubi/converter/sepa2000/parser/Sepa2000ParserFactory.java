/**
 * Created on 07/ott/08
 */
package it.nch.eb.ubi.converter.sepa2000.parser;

import it.nch.eb.common.converter.RecordImplParsersFactory;
import it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF1Model;
import it.nch.eb.common.converter.pmtreq.likepc.models.RecordXF2Model;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.flatfile.ParsersFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.AbstaractParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.ubi.converter.sepa2000.models.Sepa2000Document;
import it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordCodaModel;
import it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordDettaglioModel;
import it.nch.eb.ubi.converter.sepa2000.models.Sepa2000RecordTestaModel;
import it.nch.eb.ubi.converter.sepa2000.records.Sepa2000RecordCoda;
import it.nch.eb.ubi.converter.sepa2000.records.Sepa2000RecordDettaglio;
import it.nch.eb.ubi.converter.sepa2000.records.Sepa2000RecordTesta;


/**
 * @author gdefacci
 */
public class Sepa2000ParserFactory extends RecordImplParsersFactory implements ParsersFactory {
	
	private static final long	serialVersionUID	= 1448488446008781385L;

	public static interface ModelsNames {
		String SEPA2000DOC	= "Sepa2000Document";
	}
	
	public Sepa2000ParserFactory() {
		super();
	}

	public Sepa2000ParserFactory(AbstaractParserDsl parserDsl) {
		super(parserDsl);
	}

	public Sepa2000ParserFactory(ConvertersMapper convertersMapper) {
		super(convertersMapper);
	}

	public IParser createParser() {
		return parserDsl().createParser(ModelsNames.SEPA2000DOC, Sepa2000Document.class, createBodyParsers());
	}

	public IParser[] createBodyParsers() {
		return new IParser[] {
			createParser(new Sepa2000RecordTesta(), Sepa2000RecordTestaModel.class),
			parserDsl().any( createParser(new Sepa2000RecordDettaglio(), Sepa2000RecordDettaglioModel.class) ),
			createParser(new Sepa2000RecordCoda(), Sepa2000RecordCodaModel.class),
			parserDsl().createOptionalParser(new it.nch.eb.common.converter.pmtreq.likepc.records.RecordXF1(), RecordXF1Model.class) ,
			parserDsl().createOptionalParser(new it.nch.eb.common.converter.pmtreq.likepc.records.RecordXF2(), RecordXF2Model.class) ,
		};
	}

}
