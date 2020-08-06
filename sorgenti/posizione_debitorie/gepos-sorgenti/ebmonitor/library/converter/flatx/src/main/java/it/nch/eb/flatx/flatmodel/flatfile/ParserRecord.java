/**
 * Created on 08/feb/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.common.utils.StringUtils;
import it.nch.eb.flatx.exceptions.ParserException;
import it.nch.eb.flatx.exceptions.PropertyAccessException;
import it.nch.eb.flatx.exceptions.TokenizationException;
import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.OffsetsLinesFactory;
import it.nch.eb.flatx.files.model.LineMatcher;
import it.nch.eb.flatx.files.model.LinesFactory;
import it.nch.eb.flatx.files.model.TokenizedLine;
import it.nch.eb.flatx.flatmodel.Record;
import it.nch.eb.flatx.flatmodel.conversioninfo.ConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.IConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.error.ParserErrorsFactory;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConversionInfo;
import it.nch.eb.flatx.flatmodel.objectconverters.ToObjectConvertBuilder;
import it.nch.eb.flatx.flatmodel.objectconverters.converters.VoidToObjectConverter;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;


/**
 * @author gdefacci
 */
public class ParserRecord extends Record implements ObjectConversionInfoRecord  {

	private final LineMatcher	lineMatcher;
	
	private ToObjectConversionInfo[] conversionInfos;
	
	private final ParserErrorsFactory errorsFactory = new ParserErrorsFactory();

	protected final ToObjectConvertBuilder	convertBuilder	= new ToObjectConvertBuilder();
	
	public ParserRecord(ObjectBuilder objectBuilder, LineMatcher matcher) {
		super(ToObjectConversionInfo.class, objectBuilder);
		this.lineMatcher = matcher;
	}
	
	public synchronized ToObjectConversionInfo[] getConversionInfos() {
		if (conversionInfos==null) {
			lazyInit();
		}
		return conversionInfos;
	}

	private synchronized void lazyInit() {
		conversionInfos = (ToObjectConversionInfo[]) filterConversionInfos( getConversionInfos(this) );
	}
	
	public TokenizedLine tokenizeLine(String str, LinesFactoryBuilder lfBuilder) {
		return getLinesFactory(getConversionInfos(), lfBuilder).create(str);
	}

	private boolean isVoid(ToObjectConversionInfo toObjectConversionInfo) {
		return VoidToObjectConverter.isVoid(toObjectConversionInfo.getToObjectConverter());
	}

	public LineMatcher getMatcher() {
		return lineMatcher;
	}

	public String getName() {
		return StringUtils.getSimpleName(getClass());
	}
	
	public boolean canConsume(final String content, LinesFactoryBuilder lfBuilder) throws TokenizationException {
		TokenizedLine tokenizedContent = null;
		if (content==null) return false;
		LinesFactory linesFactory = getLinesFactory(getConversionInfos(), lfBuilder);
		tokenizedContent = linesFactory.create(content);
		return getMatcher().match(tokenizedContent);
	}

	public Object createObject(String line, LinesFactoryBuilder linesFactory, BeanErrorCollector errorCollector, int lineNumber) {
		Object obj = createContainer();
		return fillObject(obj, line, linesFactory, lineNumber, errorCollector);
	}
	
	public Object fillObject(Object obj, String line, LinesFactoryBuilder lfBuilder, int lineNumber, BeanErrorCollector errorCollector) {
		ToObjectConversionInfo[] converters = getConversionInfos();
		if (converters==null || converters.length==0) {
			String msg = "no " + ConversionInfo.class.getName() + "'s for class " + this.getClass().getName();
			throw new ParserException(msg);
		}
		
		OffsetsLinesFactory linesFactory = getLinesFactory(lfBuilder);
		String[] values;
		TokenizedLine tokenizedLine;
		try {
			tokenizedLine = linesFactory.create(line);
		} catch (Exception e1) {
			String msg = "line length[" + line.length() 
				+ "]does not match the proper length[" + linesFactory.getLineLength() 
				+ "\n]line number " + lineNumber 
				+ "]line content \n" + line + "\n";
			throw new ParserException(msg, lineNumber);
		}
		if (tokenizedLine == null) throw new NullPointerException();
		if (!lineMatcher.match(tokenizedLine))  {
			throw new ParserException("line cant be parsed.The matcher " + lineMatcher + " doesnt match the line[tokenized] " + tokenizedLine + "\n real line value \n" + line);
		}
		values = linesFactory.create(line).all();
		
		if (converters.length!=values.length) {
			String msg = "PositionalConverter'[" + getClass().getName() + "]s n.[" + converters.length + "]values n.[" + values.length + "]";
			throw new RuntimeException(msg);
		}
	
		for (int i = 0; i < converters.length; i++) {
			ToObjectConversionInfo converter = converters[i];
			if (!isVoid(converter)) {
				String value = values[i];
				try {
					converter.setProperty(obj, value);
				} catch (Exception e) {
					if (e instanceof PropertyAccessException) throw (PropertyAccessException)e;
					
					int startOffset = linesFactory.getStartOffset(i);
					int endOffset = linesFactory.getEndOffset(i);
					
					if (e instanceof ParserException) {
						ParserException pex = (ParserException) e;
						pex.setLinePositionStart(startOffset);
						pex.setLinePositionEnd(endOffset);
					}
					
					errorCollector.collectError(
						errorsFactory.createError(obj, converter.getName(), value, line, e, 
							converter.getConverter(), lineNumber, startOffset, endOffset, this) );
				}
			}
		}
		
		return obj;
	}
	
	protected OffsetsLinesFactory getLinesFactory(LinesFactoryBuilder lfBuilder) {
		return super.getLinesFactory(getConversionInfos(), lfBuilder);
	}
	
	private static int sum(int[] offsets, int i) {
		int res = 0;
		for (int j = 0; j < i; j++) {
			res += offsets[j];
		}
		return res;
	}

	public OffsetRange getOffsetByName(String fieldName) {
		int[] offsets =  getRecordOffsetsArray();
		IConversionInfo[] cis = this.getConversionInfos();
		int i=0;
		while (i<cis.length) {
			String recFldName = cis[i].getName();
			if (fieldName.equals(recFldName)) {
				int sum = sum(offsets, i);
				return new OffsetRange( sum, sum + offsets[i] ) ;
			}
			i++;
		}
		return new OffsetRange(0,0);
	}

	public int[] getRecordOffsetsArray() {
		return this.getRecordOffsetsArray(getConversionInfos());
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("record[" + StringUtils.getSimpleName(this.getClass()) + "] ");
		if (this.getObjectBuilder()!=null) sb.append("objectBuilder [" + this.getObjectBuilder() + "]");
		if (this.lineMatcher!=null) sb.append("lineMatcher [" + this.lineMatcher + "]");
		return sb.toString();
	}

}
