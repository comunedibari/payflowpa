/**
 * Created on 13/mag/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import java.io.Serializable;

import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ParserRecord;
import it.nch.eb.flatx.flatmodel.flatfile.Quantifier;

/**
 * @author gdefacci
 */
public abstract class AbstaractParserDsl implements Serializable {

	private static final long	serialVersionUID	= -5848431778068907158L;
	
	public abstract IParser createParser(int min, int max, IParser parserInfo);
	public abstract IParser createParser(ParserRecord parser);

	public IParser atLeast(int i, IParser parser) {
		return createParser(i, Quantifier.UNBOUNDED, parser);
	}

	public IParser any(IParser parser) {
		return createParser(0, Quantifier.UNBOUNDED, parser);
	}

	public IParser atMost(int i, IParser parser) {
		return createParser(0, i, parser);
	}

	public IParser exactly(int i, IParser parser) {
		return createParser(i, i, parser);
	}

	public IParser atLeast(int i, ParserRecord parser) {
		return createParser(i, Quantifier.UNBOUNDED, createParser(parser));
	}

	public IParser any(ParserRecord parser) {
		return createParser(0, Quantifier.UNBOUNDED, createParser(parser));
	}

	public IParser atMost(int i, ParserRecord parser) {
		return createParser(0, i, createParser(parser));
	}


	public IParser exactly(int i, ParserRecord parser) {
		return createParser(i, i, createParser(parser));
	}
	
	protected abstract ObjectBuilder createDefaultObjectBuilder(Class beanClass);
	public abstract IParser createOptionalParser(String propName, ParserRecord parserRecord);
	public abstract IParser createParser(String propName, ParserRecord parserRecord);
	
	public abstract IParser createParser(String name, IRecord record, Class modelKlass);
	public abstract IParser createParser(String name, IRecord record, Class modelKlass, boolean optional);
	public abstract IParser createParser(IRecord record, Class modelKlass);
	public abstract IParser createParser(IRecord record, Class modelKlass, LineMatcher matcher);

	public abstract IParser createOptionalParser(IRecord record, Class modelKlass);
	public abstract IParser createOptionalParser(IRecord record, Class modelKlass, LineMatcher matcher);
	
	public abstract IParser createOptionalParser(ParserRecord parserRecord);
	public abstract IParser createParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher, ConvertersMapper mapper);
	public abstract IParser createParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher);
	public abstract IParser createOptionalParser(String name, IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher);
	public abstract IParser createOptionalParser(IRecord record, ObjectBuilder objectBuilder, LineMatcher matcher); 
	public abstract IParser createOptionalParser(String name, Class beanClass, IParser[] parsers);
	public abstract IParser createParser(String name, Class beanClass, IParser[] parsers);

}