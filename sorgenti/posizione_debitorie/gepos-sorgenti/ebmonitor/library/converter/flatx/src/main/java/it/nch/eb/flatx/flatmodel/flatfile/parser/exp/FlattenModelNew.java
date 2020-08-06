/**
 * Created on 05/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.conversioninfo.ObjectCallback;
import it.nch.eb.flatx.flatmodel.flatfile.IRecordWriter;
import it.nch.eb.flatx.flatmodel.flatfile.LineTerminatedStringAction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BaseParserWriter;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BeanParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.BeanParserWriter;
import it.nch.eb.flatx.flatmodel.flatfile.parser.LineParser;
import it.nch.eb.flatx.flatmodel.flatfile.parser.RecordWriter;
import it.nch.eb.flatx.flatmodel.flatfile.parser.SequenceParser;
import it.nch.eb.flatx.flatmodel.verifiers.ReflectionGetterStrategy;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;


/**
 * @author gdefacci
 */
public class FlattenModelNew implements BeanEffectFactory {
	
	private final IRecordStructure recStructure;
	private final IRecordWriter recordWriter;

	public FlattenModelNew(IRecordStructure recStructure, LineTerminatedStringAction action) {
		this.recStructure = recStructure;
		this.recordWriter = createRecordWriter(recStructure, action);
	}

	public String toString() {
		return "create a flat text line for " + recStructure; 
	}

	public BeanEffect apply(IBindingManager bm) {
		return new BeanEffect() {

			public void apply(IRecordStructure rs, XPathPosition pos, Object value) {
				recordWriter.write(value, ReflectionGetterStrategy.instance);
			}

			public String toString() {
				return FlattenModelNew.this.toString();
			}
			
		};
	}

	public static IRecordWriter createRecordWriter(IRecordStructure itemParser, 
			LineTerminatedStringAction laction) {
		return createRecordWriter(itemParser, laction, BaseParserWriter.FAKE_OBJECT_CALLBACK );
	}

	public static IRecordWriter createRecordWriter(IRecordStructure itemParser, 
			LineTerminatedStringAction laction, 
			ObjectCallback beforeEachBean) {
		IRecordStructure rec;
		if (itemParser instanceof SequenceParser) {
			rec = ((SequenceParser)itemParser).getItemStrucuture();
		} else {
			rec = itemParser;
		}
		
		if (rec instanceof LineParser) {
			return new RecordWriter(rec.getName(), 
					((LineParser)rec).getConversionInfos(), laction, beforeEachBean );
		}  else if (rec instanceof BeanParser) {
			return new BeanParserWriter((BeanParser) rec, laction, beforeEachBean);
		} 
		throw new IllegalStateException();
	}
}