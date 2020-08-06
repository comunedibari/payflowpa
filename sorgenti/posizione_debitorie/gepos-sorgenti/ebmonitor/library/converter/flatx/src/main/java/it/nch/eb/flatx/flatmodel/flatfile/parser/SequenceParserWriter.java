/**
 * Created on 09/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.flatmodel.conversioninfo.ObjectCallback;
import it.nch.eb.flatx.flatmodel.flatfile.IRecordWriter;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SeqRecordStructure;
import it.nch.eb.flatx.flatmodel.verifiers.GetterStrategy;

import java.util.Collection;
import java.util.Iterator;

class SequenceParserWriter extends BaseParserWriter {
	
	final SeqRecordStructure sp;
	
	public SequenceParserWriter(SeqRecordStructure sp, 
			LineTerminatedStringAction	action, 
			ObjectCallback beforeBeanCallback) {
		super(action, beforeBeanCallback);
		this.sp = sp;
	}

	public void write(Object model, GetterStrategy getter) {
		IRecordStructure itemParser = sp.getItemStrucuture();
		if (model instanceof Collection)  {
			Collection collection = (Collection) model; 
			IRecordWriter recordWriter = createRecordWriter(itemParser, getAction(), getBeforeEachBean());
			
			for (Iterator it = collection.iterator(); it.hasNext();) {
				Object object = it.next();
				if (object!=null) recordWriter.write(object, getter);
			}				
		} else {
			throw new IllegalStateException();
		}
	}

	public String toString() {
		return "SequenceParserWriter for " + sp;
	}
	
}