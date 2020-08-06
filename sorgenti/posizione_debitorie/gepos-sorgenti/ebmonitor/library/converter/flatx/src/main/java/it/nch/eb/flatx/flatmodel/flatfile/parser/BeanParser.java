/**
 * Created on 11/feb/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.exceptions.IllegalRecordSequenceException;
import it.nch.eb.flatx.exceptions.ParserException;
import it.nch.eb.flatx.exceptions.PropertyAccessException;
import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ReflectionSetterStrategy;
import it.nch.eb.flatx.flatmodel.flatfile.SetterStrategy;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;


/**
 * parse and returns a single bean obtained using the parsers sequence provided the constructor. 
 * @author gdefacci
 */
public class BeanParser extends NotifierParser  implements BeanStructure {
	
	final IParser[] parsers;
	final String	name;
	private final boolean	optional;
	private final SetterStrategy	setterStrategy;
	
	public BeanParser(
			String name, 
			ObjectBuilder enhancedObjectBuilder,
			IParser[] parsers, 
			SetterStrategy setterStrategy, 
			boolean optional) {
		this(name,enhancedObjectBuilder, parsers, setterStrategy, optional, null);
	}
	
	public BeanParser(String name, ObjectBuilder enhancedObjectBuilder,
			IParser[] parsers, SetterStrategy setterStrategy, boolean optional, IRecordEventsDispatcher dispatcher) {
		super(enhancedObjectBuilder, dispatcher);
		this.parsers = parsers;
		this.name = name;
		this.setterStrategy = getOrElse(setterStrategy, new ReflectionSetterStrategy());
		this.optional = optional;
	}
	
	public static SetterStrategy getOrElse(SetterStrategy ifNotNullReturn, SetterStrategy elseReturn) {
		if (ifNotNullReturn!=null) return ifNotNullReturn;
		return elseReturn;
	}
	
	public BeanParser(String name2, ObjectBuilder createDefaultObjectBuilder, IParser[] parsers2, boolean optional) {
		this(name2, createDefaultObjectBuilder, parsers2, null, optional);
	}
	
	protected boolean isInitial(Object consumption) {
		return ReflectionUtils.isInitial(consumption);
	}
	
	public Object createObject(InputFile stream, LinesFactoryBuilder lfBuilder, BeanErrorCollector errorCollector) {
		Object container = createContainer();
		boolean finished=stream.currentLine()==null;
		int parserCnt = 0;
		int successParserCount = 0;
		while (!finished && parserCnt < parsers.length) {
			stream.markSavePoint();
			Object consumption = null;
			IParser propertyParser = parsers[parserCnt];
			try {
				consumption = propertyParser.createObject(stream, lfBuilder, errorCollector);
				if (consumption!=null) {
					enrichObjectWithLineNumber(consumption, stream.getLineNumber());
					successParserCount ++;
				} 
			} catch (Exception e) {
				if (e instanceof PropertyAccessException) {
					PropertyAccessException pae = (PropertyAccessException)e;
					throw pae;
				}
				
				if (!propertyParser.isOptional()) {		// if the parser
					if (ParseUtils.isFatal(e)) {
						if (e instanceof RuntimeException) throw (RuntimeException)e;
						else throw new RuntimeException(e);
					}
				}
			}
			boolean initial = isInitial(consumption);
			if (initial && !propertyParser.isOptional()) {
				stream.rollbackToSavePoint();
				throw new ParserException("the mandatory parser " + propertyParser + " could not consume current line " + stream.currentLine(), 
						stream.getLineNumber());
		    } else  {
		    	if (consumption!=null) {
					setResult(container, propertyParser, consumption);
		    	}
				stream.commit();
				finished = hasFinished(stream);
			} 
			parserCnt++;
		}
		if (parserCnt < parsers.length) {
			for (int i = parserCnt; i < parsers.length; i++) {
				if (!parsers[i].isOptional()) {
					throw new IllegalRecordSequenceException("expecting the mandatory parser " + parsers[i] , stream.getLineNumber());
				}
			}
		}
		if ((successParserCount < 1) || isInitial(container)) return null;	
		return container;
	}
	
	private boolean hasFinished(InputFile stream) {
		return !stream.hasNext() && stream.currentLine()==null;
	}

	protected void setPropertyValue(Object container, IParser propertyParser, Object proValue) {
		String propertyName = propertyParser.getName();
		if (proValue==null) {
			if (!propertyParser.isOptional()) throw new IllegalStateException("missing mandatory property "  + propertyParser);
		} else {
			setterStrategy.set(container, propertyName, proValue);
		}
	}

	public final boolean isOptional() {
		return optional;
	}

	/**
	 * set the line number on <code>obj</code> if possible
	 * @param obj
	 * @param lineNumber
	 */
	protected void enrichObjectWithLineNumber(Object obj, int lineNumber) {
		if (obj instanceof FilePositionProvider) {
			((FilePositionProvider)obj).setLineNumber(lineNumber);
		}
	}
	
	public String toString() {
		return StringUtils.getSimpleName(this.getClass()) 
				+ " name[" + getName() 
				+ "] consumers [" + stringfy(this.parsers) 
				+ "] create[" + StringUtils.getSimpleName(getObjectBuilder().getProductClass()) + "]instances";
	}
	
	private String stringfy(IParser[] parsers) {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < parsers.length; i++) {
			IParser parserInfo = parsers[i];
			sb.append(parserInfo.toString() + ",");
		}
		return sb.toString();
	}

	public String getName() {
		return name;
	}
	
	public IRecordStructure[] getItemStructures() {
		return getParsers();
	}

	public IParser[] getParsers() {
		return parsers;
	}

	public boolean match(InputFile inputFile, LinesFactoryBuilder lfBuilder) {
		return parsers[0].match(inputFile, lfBuilder);
	}
	
	public StructureKind getStructureKind() {
		return bean;
	}
	
}
