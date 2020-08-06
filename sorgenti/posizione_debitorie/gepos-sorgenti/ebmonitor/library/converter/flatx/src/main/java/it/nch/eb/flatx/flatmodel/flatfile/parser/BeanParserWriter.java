/**
 * Created on 03/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser;

import it.nch.eb.flatx.flatmodel.conversioninfo.ObjectCallback;
import it.nch.eb.flatx.flatmodel.flatfile.IRecordWriter;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.verifiers.GetterStrategy;



/**
 * @author gdefacci
 */
public class BeanParserWriter extends BaseParserWriter implements IRecordWriter {
	
	final BeanStructure consumer;
	
	public BeanParserWriter(BeanStructure consumer, 
			LineTerminatedStringAction action, ObjectCallback beforeWritingBean) {
		super(action, beforeWritingBean);
		this.consumer = consumer;
	}

	public void write(Object model, GetterStrategy getter) {
		IRecordStructure[] propConsumers = consumer.getItemStructures();
		if (getBeforeEachBean()!=null) getBeforeEachBean().apply(model);
		for (int i = 0; i < propConsumers.length; i++) {
			IRecordStructure itemParser = propConsumers[i];
			String propertyName = itemParser.getName();
			IRecordWriter recordWriter = createRecordWriter(itemParser, getAction(), getBeforeEachBean());
			if (propertyName!=null) {
				Object prop = getter.getValueFrom(model, propertyName);
				if (prop!=null) recordWriter.write(prop, getter);
			}
		}
	}

	public String toString() {
		return "bean parser writer for " + this.consumer;
	}
	
}
