/**
 * Created on 01/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.files.model.LineMatchersBuilder;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.TipoRecordProvider;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ParserRecord;


/**
 * @author gdefacci
 */
public class ParserDsl extends AbstaractParserDsl {
	
	private static final long	serialVersionUID	= 8158013294009991875L;

	public static final String TIPO_RECORD_BINDINGS_ID = "tipoRecord";
	
	public IParser createParser(int min, int max, IParser parserInfo) {
		return new SequenceParser(parserInfo, min, max);
	}
	
	public IParser createParser(ParserRecord parserRecord) {
		return new LineParser(parserRecord.getName(), parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), false);
	}
	
	public IParser createOptionalParser(String name, IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher) {
		return new LineParser(name, objectBuilder, record, matcher, true);
	}
	
	public IParser createParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher) {
		return new LineParser(record.getName(), objectBuilder, record, matcher, false);
	}
	
	public IParser createParser(IRecord record, Class modelKlass, LineMatcher matcher) {
		ObjectBuilder objectBuilder = createDefaultObjectBuilder(modelKlass);
		return new LineParser(record.getName(), objectBuilder, record, matcher, false);
	}
	
	public IParser createParser(IRecord record, Class modelKlass) {
		String name = record.getName();
		return createParser(name, record, modelKlass, false);
	}
	
	public IParser createOptionalParser(IRecord record, Class modelKlass) {
		return createParser(record.getName(), record, modelKlass, true);
	}

	public IParser createParser(String name, IRecord record, Class modelKlass) {
		return createParser(name, record, modelKlass, false);
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
		return new LineParser(name, objectBuilder, record, matcher, optional);
	}
	
//	public IParser createOptionalParser(IRecord record, Class modelKlass) {
//		ObjectBuilder objectBuilder = createDefaultObjectBuilder(modelKlass);
//		String tipoRecord = null;
//		if (record instanceof TipoRecordProvider) {
//			tipoRecord = ((TipoRecordProvider)record).getTipoRecord();
//		} else {
//			tipoRecord = record.getRecordBindings().stringValue(TIPO_RECORD_BINDINGS_ID);
//		}
//		LineMatcher matcher = lineMatcherBuilder().withName(tipoRecord).tokenMatch(1, tipoRecord).create(); 			
//		return new LineParser(record.getName(), objectBuilder, record, matcher, true);
//	}
	
	public IParser createParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher, ConvertersMapper mapper) {
		return new LineParser(record.getName(), objectBuilder, record, matcher, false, mapper);
	}
	
	public IParser createOptionalParser(ParserRecord parserRecord) {
		return new LineParser(parserRecord.getName(), parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), true);
	}
	
	public IParser createParser(String propName, ParserRecord parserRecord) {
		return new LineParser(propName, parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), false);
	}

	public IParser createOptionalParser(String propName, ParserRecord parserRecord) {
		return new LineParser(propName, parserRecord.getObjectBuilder(), parserRecord, parserRecord.getMatcher(), true);
	}
	
	public IParser createOptionalParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher) {
		return new LineParser(record.getName(), objectBuilder, record, matcher, true);
	}

	public IParser createParser(String name, Class beanClass, IParser[] parsers) {
		return new BeanParser(name, createDefaultObjectBuilder(beanClass), parsers, false);
	}
	
	public IParser createOptionalParser(String name, Class beanClass, IParser[] parsers) {
		BeanParser res = new BeanParser(name, createDefaultObjectBuilder(beanClass), parsers, true);
		return res;
	}
	
	public IParser createOptionalParser(IRecord record, Class modelKlass, LineMatcher matcher) {
		ObjectBuilder objectBuilder = createDefaultObjectBuilder(modelKlass);
		return new LineParser(record.getName(), objectBuilder, record, matcher, true);
	}
	
	
	
	protected ObjectBuilder createDefaultObjectBuilder(Class beanClass) {
		return  new NewInstanceObjectBuilder(beanClass);
	}
	
	public static LineMatchersBuilder lineMatcherBuilder() {
		return new LineMatchersBuilder();
	}
}
