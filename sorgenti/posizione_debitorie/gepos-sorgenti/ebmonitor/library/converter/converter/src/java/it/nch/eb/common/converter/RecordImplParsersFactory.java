/**
 * Created on 09/apr/08
 */
package it.nch.eb.common.converter;

import java.io.Serializable;

import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.RecordImpl;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.parser.AbstaractParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.MappedParserDsl;
import it.nch.eb.flatx.flatmodel.flatfile.parser.ParserDsl;

/**
 * base functionalities for ParsersFactory
 * @author gdefacci
 */
public class RecordImplParsersFactory implements Serializable {

	private static final long	serialVersionUID	= -4661475217644210229L;
	private final AbstaractParserDsl	parserDsl;

	public RecordImplParsersFactory() {
		this(new ParserDsl());
	}

	public RecordImplParsersFactory(AbstaractParserDsl parserDsl) {
		this.parserDsl = parserDsl;
	}
	
	public RecordImplParsersFactory(ConvertersMapper convertersMapper) {
		this(new MappedParserDsl(convertersMapper));
	}
	
	public AbstaractParserDsl parserDsl() {
		return parserDsl;
	}

	public static IParser create(RecordImpl rec, Class klass, ConvertersMapper convertersMapper) {
		return new ParserDsl().createParser(rec, createObjectBuilder(klass), createTipoRecordMatcher(1, getTipoRecord(rec)), convertersMapper);
	}
	public static IParser create(RecordImpl rec, Class klass) {
		return new ParserDsl().createParser(rec, createObjectBuilder(klass), createTipoRecordMatcher(1, getTipoRecord(rec)));
	}
	
	public IParser createParser(RecordImpl rec, Class klass) {
		return parserDsl().createParser(rec, createObjectBuilder(klass), createTipoRecordMatcher(1, getTipoRecord(rec)) );
	}

	protected static String getTipoRecord(RecordImpl record) {
		return record.getRecordBindings().stringValue(ConversionsConsts.TIPO_RECORD);
	}

	public static ObjectBuilder createObjectBuilder(Class klass) {
		return new NewInstanceObjectBuilder(klass);
	}
	
	public static LineMatcher createTipoRecordMatcher(final int idx, final String tipoRecord) {
		return ParserDsl.lineMatcherBuilder()
			.withName("line matcher for tipo record [" + tipoRecord + "]")
			.tokenMatch(1, tipoRecord)
			.create(); 
	}

}