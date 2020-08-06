/**
 * Created on 01/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.TipoRecordProvider;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ParserRecord;


/**
 * @author gdefacci
 */
public class MappedParserDsl extends ParserDsl {
	
	private static final long	serialVersionUID	= 6007097328069193154L;
	protected final ConvertersMapper convertersMapper;
	
	public MappedParserDsl(ConvertersMapper convertersMapper) {
		if (convertersMapper.isEmpty()) throw new IllegalArgumentException("receivengin a emtpy converters mapper");
		this.convertersMapper = convertersMapper;
	}

	public IParser createParser(ParserRecord parserRecord) {
		return new LineParser(parserRecord.getName(), parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), false, convertersMapper);
	}

	public IParser createParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher) {
		return new LineParser(record.getName(), objectBuilder, record, matcher, false, convertersMapper);
	}
	
	public IParser createParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher, ConvertersMapper mapper) {
		return new LineParser(record.getName(), objectBuilder, record, matcher, false, mapper);
	}
	
	public IParser createParser(String propName, ParserRecord parserRecord) {
		return new LineParser(propName, parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), false, convertersMapper);
	}
	
	public IParser createOptionalParser(ParserRecord parserRecord) {
		return new LineParser(parserRecord.getName(), parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), true, convertersMapper);
	}
	
	public IParser createOptionalParser(String name, IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher) {
		return new LineParser(name, objectBuilder, record, matcher, true, convertersMapper);
	}

	public IParser createOptionalParser(String propName, ParserRecord parserRecord) {
		return new LineParser(propName, parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), true, convertersMapper);
	}
	
	public IParser createOptionalParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher) {
		return new LineParser(record.getName(), objectBuilder, record, matcher, true, convertersMapper);
	}
	
	public IParser createParser(String name, IRecord record, Class modelKlass, boolean optional) {
		ObjectBuilder objectBuilder = createDefaultObjectBuilder(modelKlass);
		String tipoRecord = null;
		if (record instanceof TipoRecordProvider) {
			tipoRecord = ((TipoRecordProvider)record).getTipoRecord();
		} else {
			tipoRecord = record.getRecordBindings().stringValue(TIPO_RECORD_BINDINGS_ID);
		}
		LineMatcher matcher = lineMatcherBuilder().withName(tipoRecord).tokenMatch(1, tipoRecord).create();
		return new LineParser(name, objectBuilder, record, matcher, optional, convertersMapper);
	}
	
}
