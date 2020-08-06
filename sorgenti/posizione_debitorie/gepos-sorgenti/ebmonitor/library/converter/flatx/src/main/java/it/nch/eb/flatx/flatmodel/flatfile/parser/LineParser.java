/**
 * Created on 04/gen/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.common.utils.ReflectionUtils.Setter;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.exceptions.ParserException;
import it.nch.eb.flatx.exceptions.PropertyAccessException;
import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.OffsetsLinesFactory;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileUtils;
import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.files.model.TokenizedLine;
import it.nch.eb.flatx.flatmodel.ConvertersMapper;
import it.nch.eb.flatx.flatmodel.FilePositionProvider;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.Record;
import it.nch.eb.flatx.flatmodel.conversioninfo.ConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.VoidExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ParserRecord;
import it.nch.eb.flatx.flatmodel.flatfile.error.ParserBeanError;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordStructureEqualsHelper;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SimpleRecordStructure;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.VoidToObjectConverter;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;
import it.nch.eb.flatx.flatmodel.verifiers.IBeanError;



/**
 * record type suited for flats file parsing. 
 * keep immutable when possible.
 * 
 * @author gdefacci
 */
public class LineParser extends Record implements IParser, SimpleRecordStructure {
	
	protected static final org.slf4j.Logger	log	= ResourcesUtil.createLogger(LineParser.class);
	
	private final LineMatcher	matcher;
	private final String		name;
	private ConversionInfo[]    conversionInfos;
	
	//private final ParserErrorsFactory errorsFactory = new ParserErrorsFactory();

	private final boolean	optional;
	
	private final ConvertersMapper convertersMapper;

	private Object	record; // Either[IRecord, ParserRecord]
	
	public LineParser(String name, ObjectBuilder objectBuilder, IRecord record, LineMatcher matcher, boolean optional) {
		this(name, objectBuilder, record, matcher, optional, null);
	}
	public LineParser(String name, ObjectBuilder objectBuilder, IRecord record, LineMatcher matcher, boolean optional, ConvertersMapper convertersMapper) {
		super(objectBuilder);
		this.matcher = matcher;
		this.name = name != null ? name : record.getName();
		this.record = record;
		this.optional = optional;
//		this.conversionInfos = filterNotNullConverter(record.getConversionInfos());
		this.conversionInfos = record.getConversionInfos();
		this.convertersMapper = convertersMapper;
		if (matcher == null) throw new NullPointerException();
	}
	
	public LineParser(String name, ObjectBuilder objectBuilder, ParserRecord record, LineMatcher matcher, boolean optional) {
		this(name,objectBuilder,record,matcher,optional,null);	
	}
	public LineParser(String name, ObjectBuilder objectBuilder, ParserRecord record, LineMatcher matcher, boolean optional, ConvertersMapper convertersMapper) {
		super(objectBuilder);
		this.matcher = matcher;
		this.record = record;
		this.name = name != null ? name : record.getName();
		this.optional = optional;
		this.conversionInfos = record.getConversionInfos();
		this.convertersMapper = convertersMapper;
		if (matcher == null) throw new NullPointerException();
	}

//	private LineParser(String name, ObjectBuilder objectBuilder, IRecord record, LineMatcher matcher) {
//		this( name, objectBuilder, record, matcher, false);
//	}
	
//	public LineParser(ObjectBuilder objectBuilder, IRecord record, LineMatcher matcher) {
//		this(null, objectBuilder, record, matcher);
//	}
//	
//	public LineParser(Class klass, IRecord record, LineMatcher matcher) {
//		this(null, new NewInstanceObjectBuilder(klass), record, matcher);
//	}
	
	public IRecord getIRecord() {
		if (record instanceof IRecord) return  (IRecord) record;
		else return null; // TODO: is throwing an excpetion a better option?
	}
	
	public ParserRecord getParserRecord() {
		if (record instanceof ParserRecord) return  (ParserRecord) record;
		else return null;  // TODO: is throwing an excpetion a better option?
	}
	
	public int hashCode() {
//		return getObjectBuilder().getProductClass().hashCode() * getName().hashCode();
		return RecordStructureEqualsHelper.instance.hashCode(this);
	}

	public boolean equals(Object obj) {
		return RecordStructureEqualsHelper.instance.areEquals(this, obj);
//		if (obj instanceof LineParser) {
//			LineParser othr = (LineParser) obj;
//			if (getObjectBuilder().getProductClass().getName().equals(othr.getObjectBuilder().getProductClass().getName())) {
//				if (getName().equals(othr.getName())) return true;
//			} 
//		} 
//		return false;
	}

	public LineMatcher getMatcher() {
		return matcher;
	}

	public ConversionInfo[] getConversionInfos() {
		return conversionInfos;
	}

	public String getName() {
		return name;
	}
	
	public Object createObject(InputFile inputFile, LinesFactoryBuilder linesFactoryBuilder, BeanErrorCollector errorCollector) {
		if (!match(inputFile, linesFactoryBuilder)) return null;
		Object obj = createContainer();
		Object res = fillObject(obj, inputFile.currentLine(), linesFactoryBuilder, errorCollector, inputFile.getLineNumber());
		log.info(this + " parsed ("  + inputFile.getLineNumber() + "):" + inputFile.currentLine());
		decorateModel(res, inputFile, linesFactoryBuilder);
		InputFileUtils.moveNextIfNotFinished(inputFile);
		return res;
	}
	
	/**
	 * opportunity of augmentig model informations, with input file infos
	 */
	protected void decorateModel(Object res, InputFile inputFile, LinesFactoryBuilder linesFactoryBuilder) {
		if (res instanceof FilePositionProvider) {
			((FilePositionProvider)res).setLineNumber(inputFile.getLineNumber());
		}
	}

	public boolean match(InputFile inputFile, LinesFactoryBuilder lfBuilder) {
		String content = inputFile.currentLine();
		return match(content, lfBuilder);
	}
	
	public boolean match(String content, LinesFactoryBuilder lfBuilder) {
		try {
			TokenizedLine tokenizedContent = null;
			if (content==null) return false;
			tokenizedContent = getLinesFactory(lfBuilder).create(content);
			return getMatcher().match(tokenizedContent);
			
		} catch (RuntimeException e) {
			return false;
		}
	}
	
	protected synchronized OffsetsLinesFactory getLinesFactory(ConversionInfo[] converters, LinesFactoryBuilder lfBuilder) {
		int[] cols = getRecordOffsetsArray(converters);
		return createFixedColumnsLineFactory(cols, lfBuilder);	
	}
	
	public Object fillObject(Object obj, String line, LinesFactoryBuilder lfBuilder, BeanErrorCollector errorCollector, int lineNumber) {
	
		ConversionInfo[] converters = getConversionInfosOrFail();
		
		OffsetsLinesFactory linesFactory = getLinesFactory(converters, lfBuilder);
		String[] values;
		try {
			values = linesFactory.splitLine(line, conversionInfos);
		} catch (ParserException e) {
			e.setLineNumber(lineNumber);
			throw e;
		}
	
		for (int i = 0; i < converters.length; i++) {
			ConversionInfo converter = converters[i];
			if (!isVoid(converter)) {
				String value = values[i];
				try {
					setPropertyValue(obj, converter, value);
				} catch (Exception e) {
					int startOffset = linesFactory.getStartOffset(i);
					int endOffset = linesFactory.getEndOffset(i);
					
					if (e instanceof PropertyAccessException) {
						throw new ParserException(e, lineNumber, startOffset, endOffset);
					}
					
					if (e instanceof ParserException) {
						ParserException pex = (ParserException) e;
						pex.setLinePositionStart(startOffset);
						pex.setLinePositionEnd(endOffset);
					}
					
					log.warn("error with " + converter + "\nparsing line " + line, e);
					
					errorCollector.collectError(
						createError(obj, converter.getName(), value, line, e, 
							converter.getConverter(), lineNumber, startOffset, endOffset) );
				}
			}
		}
	
		return obj;
	}
	
	public IBeanError createError(Object targetObject, String propertyName, Object propertySourceValue, 
			String line, Exception e, Converter converter, int lineNumber, int lineStartPos, int lineEndPos) {
		return new ParserBeanError(targetObject, propertyName, propertySourceValue, 
				e, converter, line,  lineNumber, lineStartPos, lineEndPos);
	}
	

	private ConversionInfo[] getConversionInfosOrFail() {
		ConversionInfo[] converters = getConversionInfos();
		if (converters==null || converters.length==0) {
			String msg = "no " + ConversionInfo.class.getName() + "'s for class " + this.getClass().getName();
			throw new ParserException(msg);
		}
		return converters;
	}
	
	public Converter getMappedConverter(Converter cnv) {
		Converter mappedCnv = null;
		if (convertersMapper!=null) mappedCnv = convertersMapper.getMappedConverter(cnv);
		if (mappedCnv==null) return cnv;
		else {
			return mappedCnv;
		}
	}

	private void setPropertyValue(Object obj, ConversionInfo converter, String value) {
		if (!(converter instanceof VoidExpressionConversionInfo)) {
			Object realValue = null;
			if (converter instanceof ToObjectConversionInfo) {
				ToObjectConversionInfo r = ((ToObjectConversionInfo)converter);
				Converter cnv = getMappedConverter( r.getConverter() );
				String c1 = cnv.encode(value);
				realValue = r.getToObjectConverter().convert(c1);
			} else {
				Converter cnv = getMappedConverter(converter.getConverter());
				realValue = cnv.encode(value);
			}
			setResult(obj, converter.getName(), realValue);
		}
	}

	protected void setResult(Object obj, String name2, Object realValue) {
		Setter setter;
 		if (realValue!=null) setter = ReflectionUtils.getSetterForProperty(obj, name2, realValue.getClass());
		else setter = ReflectionUtils.getSetterForProperty(obj, name2);
		setter.setValue(realValue);
	}

	private boolean isVoid(IConversionInfo converter) {
		if (converter instanceof ToObjectConversionInfo) {
			ToObjectConversionInfo toObjectConversionInfo = (ToObjectConversionInfo) converter;
			return VoidToObjectConverter.isVoid(toObjectConversionInfo.getToObjectConverter());
		}
		return false;
	}

	private OffsetsLinesFactory linesFactoryCache = null;
	public synchronized OffsetsLinesFactory getLinesFactory(LinesFactoryBuilder bldr ) {
		if (linesFactoryCache==null) linesFactoryCache = getLinesFactory(getConversionInfos(), bldr);
		return linesFactoryCache;
	}
	
	public boolean isOptional() {
		return optional;
	}
	
	public String toString() {
		return StringUtils.getSimpleName(LineParser.class) 
			+ "[" + name + "]"
			+ " match [" + this.matcher.toString() + "]";
	}
	public StructureKind getStructureKind() {
		return simple;
	}
	
}