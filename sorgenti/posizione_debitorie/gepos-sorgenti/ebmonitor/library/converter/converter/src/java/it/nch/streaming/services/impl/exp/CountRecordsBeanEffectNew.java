/**
 * Created on 05/mar/2009
 */
package it.nch.streaming.services.impl.exp;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import it.nch.eb.common.converter.BodyRecordsNumberProvider;
import it.nch.eb.common.converter.RecordCountIncTrigger;
import it.nch.eb.common.converter.RecordCountProvider;
import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffect;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanEffectFactory;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.SeqRecordStructure;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.streaming.services.impl.SimpleCounter;


/**
 * @author gdefacci
 */
public class CountRecordsBeanEffectNew implements BeanEffectFactory {
	
	private int curreLineNumber;
//	private final IRecordStructure parser; 

	public BeanEffect apply(IBindingManager bm) {
		return new BeanEffect() {

			public void apply(IRecordStructure parser, XPathPosition pos, Object value) {
//				BeanStructure beanParser = parser;
				SimpleCounter bdyRecsCounter = new SimpleCounter();
				List/*<BodyRecordsNumberProvider>*/ itemsThatNeedNumberOfRecord = 
					property(value, parser, bdyRecsCounter);
				
				for (Iterator it = itemsThatNeedNumberOfRecord.iterator(); it.hasNext();) {
					BodyRecordsNumberProvider brnp = (BodyRecordsNumberProvider) it.next();
					brnp.setBodyRowsNumber(String.valueOf(bdyRecsCounter.get()));
				}
			}
			
		};
	}

	protected List/*<BodyRecordsNumberProvider>*/ property(Object prop, IRecordStructure propParser, SimpleCounter bdyRecCounter) {
		List/*<BodyRecordsNumberProvider>*/ itemsThatNeedNumberOfRecord = new ArrayList();
		if (propParser instanceof SeqRecordStructure) {
			List propValue = (List) prop;
			itemsThatNeedNumberOfRecord.addAll( listProperty(propValue, (SeqRecordStructure) propParser, bdyRecCounter) );
		} else if (propParser instanceof BeanStructure) {
			itemsThatNeedNumberOfRecord.addAll( beanProperty(prop, (BeanStructure) propParser, bdyRecCounter));
		} else {
			itemsThatNeedNumberOfRecord.addAll( simpleProperty(prop, bdyRecCounter));
		}
		return itemsThatNeedNumberOfRecord;
	}

	private List beanProperty(Object res, BeanStructure beanParser , SimpleCounter bdyRecCounter) {
		IRecordStructure[] props = beanParser.getItemStructures();
		List/*<BodyRecordsNumberProvider>*/ itemsThatNeedNumberOfRecord = new ArrayList();
		for (int i = 0; i < props.length; i++) {
			IRecordStructure propParser = props[i];
			Object prop = ReflectionUtils.getGetterValue(res, propParser.getName());
			if (prop!=null) {
				itemsThatNeedNumberOfRecord.addAll( property(prop, propParser, bdyRecCounter) );
			}
		}
		return itemsThatNeedNumberOfRecord;
	}
	
	private List/*<BodyRecordsNumberProvider>*/ listProperty(List propValue, 
			SeqRecordStructure sp, 
			SimpleCounter bdyRecCounter) {
		
		IRecordStructure itemParser = sp.getItemStrucuture();
		List/*<BodyRecordsNumberProvider>*/ itemsThatNeedNumberOfRecord = new ArrayList();
		for (Iterator it = propValue.iterator(); it.hasNext();) {
			Object item = it.next();
			itemsThatNeedNumberOfRecord.addAll( property(item, itemParser, bdyRecCounter) );
		}	
		return itemsThatNeedNumberOfRecord;
	}
	
	private List/*<BodyRecordsNumberProvider>*/ simpleProperty(Object propValue, SimpleCounter bdyRecCounter) {
		List/*<BodyRecordsNumberProvider>*/ itemsThatNeedNumberOfRecord = new ArrayList();
		if (propValue!=null) {
			if (propValue instanceof BodyRecordsNumberProvider) {
				itemsThatNeedNumberOfRecord.add(propValue);
			}
			if (propValue instanceof RecordCountIncTrigger) {
				curreLineNumber++;
				bdyRecCounter.inc();
			}
			if (propValue instanceof RecordCountProvider) {
				((RecordCountProvider)propValue).setRecordCount(String.valueOf(curreLineNumber));
			}
		}
		return itemsThatNeedNumberOfRecord;
	}

	public String toString() {
		return "count records included ";
	}
}
