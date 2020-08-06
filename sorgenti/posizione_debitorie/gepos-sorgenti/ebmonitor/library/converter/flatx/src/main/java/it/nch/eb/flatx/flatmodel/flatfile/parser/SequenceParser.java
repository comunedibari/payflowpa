/**
 * Created on 01/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.exceptions.PropertyAccessException;
import it.nch.eb.flatx.files.LinesFactoryBuilder;
import it.nch.eb.flatx.files.model.InputFile;
import it.nch.eb.flatx.files.model.InputFileUtils;
import it.nch.eb.flatx.flatmodel.flatfile.NewInstanceObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.ObjectBuilder;
import it.nch.eb.flatx.flatmodel.flatfile.Quantifier;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SeqRecordStructure;
import it.nch.eb.flatx.flatmodel.verifiers.BeanErrorCollector;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * greedy consumes using parser and collecting result in a list. The items efectivelly consumed are the ones the result 
 * list contains
 * @author gdefacci
 */
public class SequenceParser extends NotifierParser implements SeqRecordStructure {
	
	private final static ObjectBuilder arraylistObjectBuilder = 
		new NewInstanceObjectBuilder(List.class, ArrayList.class);
	
	private final IParser parser;
	private final Quantifier quantifier;
	
	public SequenceParser(IParser parser, int min, int max, IRecordEventsDispatcher dispatcher) {
		super(arraylistObjectBuilder, dispatcher);
		this.parser = parser;
		this.quantifier = new Quantifier(min,max);
	}
	public SequenceParser(IParser parser, int min, int max) {
		this(parser, min, max, null);
	}
	
	public Object createObject(InputFile inputFile, LinesFactoryBuilder lfBuilder, BeanErrorCollector errorCollector) {
		boolean finished = inputFile.currentLine() == null;
		List result = (List) createContainer();
		
//		ParsedItemInfo lastParsedItem = null;
		while (!finished && quantifier.can(result.size())) {
			inputFile.markSavePoint();
			
//			ParsedItemInfo auxParsedItem = null;
			Object obj = null;
			try {
//				auxParsedItem = new ParsedItemInfo(parser, inputFile);
				obj = parser.createObject(inputFile, lfBuilder, errorCollector);
				if (obj!=null) {
//					auxParsedItem.setParserResult(result);
//					lastParsedItem = auxParsedItem;
				}
			} catch (Exception e) {
				if (e instanceof PropertyAccessException) {
					PropertyAccessException pae = (PropertyAccessException)e;
					throw pae;
//					LocalizedPropertyAccessException newExc = new LocalizedPropertyAccessException(lastParsedItem, pae);
//					throw newExc;
				}
				obj = null;
			}  
			if (obj!=null) { 
				setResult(result, parser, obj);
				inputFile.commit();
				finished = InputFileUtils.hasFinished(inputFile);
			} else {
				finished = true;
				inputFile.rollbackToSavePoint();
			}
		}
		if (quantifier.must(result.size())) {
			throw new IllegalStateException("the parser " + parser 
					+ " consumed " + result.size() + " items, but it has to parse " + quantifier + 
					" items");
		}
		if (result.isEmpty()) return null;
		return result;
	}

	public void setPropertyValue(Object result, IParser parser, Object obj) {
		if (obj==null) {
			if (!parser.isOptional())
				throw new IllegalStateException("missing mandatory parser"+ parser);
		} else {
			if (result instanceof Collection) {
				((Collection)result).add(obj);
			} else {
				throw new ClassCastException("expecting a Collection but got a " + obj.getClass());
			}
		}
	}
	
	public String getName() {
		return parser.getName();
	}

	public boolean isOptional() {
		return quantifier.isOptional();
	}

	public String toString() {
		return "sequence parser" + quantifier.toString() + ", " + parser.toString() ;
	}

	public boolean match(InputFile inputFile, LinesFactoryBuilder lfBuilder) {
		return parser.match(inputFile, lfBuilder);
	}
	
	public IRecordStructure getItemStrucuture() {
		return getItemParser();
	}
	
	public IParser getItemParser() {
		return parser;
	}
	
	public Quantifier getQuantifier() {
		return quantifier;
	}
	public StructureKind getStructureKind() {
		return seq;
	}
	
}
