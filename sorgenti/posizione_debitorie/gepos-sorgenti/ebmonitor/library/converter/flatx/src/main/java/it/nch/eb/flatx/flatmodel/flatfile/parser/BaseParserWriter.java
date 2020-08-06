/**
 * Created on 09/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.flatmodel.conversioninfo.ObjectCallback;
import it.nch.eb.flatx.flatmodel.flatfile.IRecordWriter;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.verifiers.ReflectionGetterStrategy;


/**
 * @author gdefacci
 */
public abstract class BaseParserWriter implements IRecordWriter {
	
	private final LineTerminatedStringAction	action;
	private final ObjectCallback 				beforeEachBean;
	
	public static final ObjectCallback FAKE_OBJECT_CALLBACK = new ObjectCallback() {

		public void apply(Object obj) {
		}
		
	};
	
	public BaseParserWriter(LineTerminatedStringAction action, ObjectCallback beforeEachBean) {
		this.action = action;
		this.beforeEachBean = beforeEachBean;
	}
	
	public static IRecordWriter createRecordWriter(IRecordStructure itemParser, 
			LineTerminatedStringAction laction) {
		return createRecordWriter(itemParser, laction, FAKE_OBJECT_CALLBACK );
	}

	public static IRecordWriter createRecordWriter(IRecordStructure itemParser, 
			LineTerminatedStringAction laction, 
			ObjectCallback beforeEachBean) {
		
		if (itemParser instanceof LineParser) {
			return new RecordWriter(itemParser.getName(), 
					((LineParser)itemParser).getConversionInfos(), laction, beforeEachBean );
		} else if (itemParser instanceof SequenceParser) {
			return new SequenceParserWriter((SequenceParser) itemParser, laction, beforeEachBean);
		} else if (itemParser instanceof BeanParser) {
			return new BeanParserWriter((BeanParser) itemParser, laction, beforeEachBean);
		}
		throw new IllegalStateException();
	}

	
	public LineTerminatedStringAction getAction() {
		return action;
	}

	public ObjectCallback getBeforeEachBean() {
		return beforeEachBean;
	}

	public void write(Object model) {
		write(model, ReflectionGetterStrategy.instance);
	}
	
}
