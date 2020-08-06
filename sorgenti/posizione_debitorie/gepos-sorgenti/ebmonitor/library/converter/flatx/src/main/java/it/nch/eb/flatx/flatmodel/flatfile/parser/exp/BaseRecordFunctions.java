/**
 * Created on 01/mar/2009
 */
package it.nch.eb.flatx.flatmodel.flatfile.parser.exp;

import it.nch.eb.common.utils.ReflectionUtils;
import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.conversioninfo.ExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.conversioninfo.VoidExpressionConversionInfo;
import it.nch.eb.flatx.flatmodel.flatfile.parser.LineParser;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;


/**
 * @author gdefacci
 */
public class BaseRecordFunctions implements RecordFunctions {
	
	public RecordFunction getRecordPropertiesSetter() {
		return setAllRecordProperties;
	}
	
	public RecordFunction getBeanPropertiesSetter() {
		return setAllBeanProperties;
	}

	private final RecordFunction setAllRecordProperties =
		new RecordFunction() {
		
			public Object apply(IRecordStructure recStruct, 
					XPathPosition pos, 
					XPathsMapBindings xpathBindings,
					IBindingManager lbindings) {
				IRecord lrecord = getRecordOrFail(recStruct);

				boolean almostOne = false;
				ExpressionConversionInfo[] cis = lrecord.getConversionInfos();
				lbindings.enterScope(lrecord.getRecordBindings());
				Object res = recStruct.getObjectBuilder().create();
				
				for (int i = 0; i < cis.length; i++) {
					ExpressionConversionInfo eci = cis[i];
					boolean hasBeenSet = setConversionInfoValue(recStruct, pos, eci, res, xpathBindings, lbindings);
					if (!almostOne) almostOne = hasBeenSet;
				}
				
				lbindings.exitScope();
				if (almostOne) return res;
				else return null;
			}

			public boolean canApply(IRecordStructure recStruct, XPathPosition pos, XPathsMapBindings xpathBindings,
					IBindingManager lbindings) {
				IRecord lrec = getRecordOrFail(recStruct);
				boolean processRecord = true;
				if (lrec instanceof ElementPredicate) {
					ElementPredicate predRec = (ElementPredicate)lrec;
					processRecord = predRec.match(pos, xpathBindings);
				}
				return processRecord;
			}

			public String toString() {
				return "set all record properties";
			}
		
		};
		
		private final RecordFunction setAllBeanProperties =
			new RecordFunction() {

				public Object apply(IRecordStructure recStruct, 
						XPathPosition pos, 
						XPathsMapBindings xpathBindings,
						IBindingManager lbindings) {
					if (recStruct.getStructureKind() == IRecordStructure.bean) {
						return apply((BeanStructure)recStruct, pos, xpathBindings, lbindings);
					} else throw new IllegalStateException("Not a bean structure : \n" + recStruct);
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
//							TODO: ripristinare
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
	
			
	private IRecord getRecordOrFail(IRecordStructure recStruct) {
		if (recStruct instanceof LineParser) return ((LineParser) recStruct).getIRecord();
		else throw new IllegalStateException("Not a line parser : \n" + recStruct);
	}
	
	private static boolean setConversionInfoValue(IRecordStructure parser, XPathPosition pos, 
			ExpressionConversionInfo eci, Object res,
			XPathsMapBindings xpathsMap, IBindingManager bindings) {
		try {
			if (eci instanceof VoidExpressionConversionInfo) {
				return false;
			}
			String text = eci.getValue(pos, xpathsMap, bindings);
			if (text==null) {
				return false;
			} else {
				ReflectionUtils.setProperty(res, eci.getName(), text);
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException("\nerror on record: " + parser 
					+ "\nwhile setting prop: " + eci  + "\non object: " + res, e);
		}
	}
}
