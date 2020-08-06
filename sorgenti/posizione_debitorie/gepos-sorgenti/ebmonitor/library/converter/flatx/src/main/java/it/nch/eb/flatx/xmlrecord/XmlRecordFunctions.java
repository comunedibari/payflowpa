/**
 * Created on 11/mar/2009
 */
package it.nch.eb.flatx.xmlrecord;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.XmlRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.BeanStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.IRecordStructure;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordFunction;
import it.nch.eb.flatx.flatmodel.flatfile.parser.exp.RecordFunctions;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;


/**
 * @author gdefacci
 */
public class XmlRecordFunctions implements RecordFunctions {
	
	private final RecordFunction recordPropertiesSetter = new RecordFunction() {

		public Object apply(IRecordStructure recStruct, XPathPosition pos, XPathsMapBindings xpathBindings,
				IBindingManager lbindings) {
			XmlRecord xmlRec = getXmLRecordOrFail(recStruct);
			return xmlRec.get(recStruct.getObjectBuilder(), pos, xpathBindings);
		}

		public boolean canApply(IRecordStructure recStruct, XPathPosition pos, XPathsMapBindings xpathBindings,
				IBindingManager lbindings) {
			XmlRecord rec = getXmLRecordOrFail(recStruct);
			return rec instanceof ElementPredicate ?
					((ElementPredicate)rec).match(pos, xpathBindings) : true;
		}

		public String toString() {
			return "set all xpath on the XMLRecord";
		}
		
	};

	public RecordFunction getBeanPropertiesSetter() {
		return new RecordFunction() {

			public Object apply(IRecordStructure recStruct, 
					XPathPosition pos, 
					XPathsMapBindings xpathBindings,
					IBindingManager lbindings) {
				if (recStruct instanceof BeanXmlRecordStructure) {
					return apply((BeanXmlRecordStructure)recStruct, pos, xpathBindings, lbindings);
				} else throw new IllegalStateException("Not a BeanXmlRecordStructure : \n" + recStruct);
			}
			
			public Object apply(BeanStructure recStruct, 
					XPathPosition pos, 
					XPathsMapBindings xpathBindings,
					IBindingManager lbindings) {
				
				Object res = recStruct.getObjectBuilder().create();
				
				IRecordStructure[] items = recStruct.getItemStructures();
				boolean almostOne = false;
				for (int i = 0; i < items.length; i++) {
					IRecordStructure itemStruct = items[i];
					Object value = lbindings.get(itemStruct.getName());
					if (value == null && !itemStruct.isOptional()) {
//						TODO: ripristinare
						throw new IllegalStateException("the structure " + recStruct 
								+ "\nis missing the mandatory property " + itemStruct 
								+ "\npos :" + pos + "\nbindings :" + lbindings);
					} else if (value!=null) {
						almostOne = true;
						ReflectionUtils.setProperty(res, itemStruct.getName(), value);
					}
				}
				if (almostOne) return res;
				else return null;
			}

			public boolean canApply(IRecordStructure recStruct, XPathPosition pos, XPathsMapBindings xpathBindings,
					IBindingManager lbindings) {
				return true;
			}

			public String toString() {
				return "set all bean properties";
			}
			
		};
	}

	public RecordFunction getRecordPropertiesSetter() {
		return recordPropertiesSetter;
	}
	
	private XmlRecord getXmLRecordOrFail(IRecordStructure rec) {
		if (rec instanceof SimpleXmlRecordStructure) {
			return ((SimpleXmlRecordStructure)rec).getXmlRecord();
		} else
			throw new IllegalStateException("not a " + SimpleXmlRecordStructure.class.getName());
	}

}
