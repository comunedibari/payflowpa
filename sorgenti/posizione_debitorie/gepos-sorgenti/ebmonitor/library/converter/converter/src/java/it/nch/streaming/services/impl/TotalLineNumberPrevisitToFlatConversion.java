/**
 * Created on 16/ott/08
 */
package it.nch.streaming.services.impl;

import it.nch.eb.common.converter.ConversionsConsts;
import it.nch.eb.flatx.flatmodel.IRecord;
import it.nch.eb.flatx.flatmodel.conversioninfo.BindingManagerEffect;
import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;
import it.nch.eb.flatx.flatmodel.sax.ModelLoader;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;
import it.nch.fwk.core.NamespacesInfos;

import java.util.Map;
import java.util.TreeMap;


/**
 * @author gdefacci
 */
public class TotalLineNumberPrevisitToFlatConversion extends PrevisitToFlatConversion{
	
	private static final long	serialVersionUID	= 1423844430665814326L;
	private final IRecord[] records;

	public TotalLineNumberPrevisitToFlatConversion(BaseToFlatConversionService conversionService) {
		this(conversionService, conversionService.getQueryNamespaces(), conversionService.getRecords(),
				new BindingManagerEffect[] {});
	}
	
	public TotalLineNumberPrevisitToFlatConversion(BaseToFlatConversionService conversionService, 
			BindingManagerEffect[] bmEffects) {
		this(conversionService, conversionService.getQueryNamespaces(), conversionService.getRecords(),
				bmEffects);
	}
	
	public TotalLineNumberPrevisitToFlatConversion(
			BaseToFlatConversionService conversionService, 
			NamespacesInfos nss,
			IRecord[] records,
			BindingManagerEffect[] bmEffects) {
		super(conversionService, nss, bmEffects);
		this.records = records;
	}
	
	public PrevisitToFlatConversion create(BindingManagerEffect[] bmEffect) {
		return new TotalLineNumberPrevisitToFlatConversion( (BaseToFlatConversionService) getConversionService(), 
				getNamespaces(), records, mergedEffects(bmEffect) );
	}

	XPathPosition parse(String xpth) {
		return XPathsParser.instance.parseXPathPosition(xpth);
	}
	
	protected MapCollectorActivableLeafElementHandler createPrevisitElementHandler() {
		final ModelLoader delegate = new ModelLoader();
		final TreeMap map = new TreeMap();
		map.put(ConversionsConsts.TOTAL_ROW_NUMBER, new Integer(0));
		for (int i = 0; i < records.length; i++) {
			final IRecord rec = records[i];
			XPathPosition pos = parse( rec.getBaseXPath() );
			delegate.addArea(pos);
			if (ElementPredicate.class.isAssignableFrom(rec.getClass())) {
				delegate.addOnEnd(pos, new XPathMapPositionEffect() {
	
					public void apply(XPathPosition pos, XPathsMapBindings xpathBindings) {
						ElementPredicate ep = (ElementPredicate) rec;
						if (ep.match(pos, xpathBindings)) {
							Integer cnt = (Integer) map.get(ConversionsConsts.TOTAL_ROW_NUMBER);
							int newValue = cnt.intValue() + 1;
							map.put(ConversionsConsts.TOTAL_ROW_NUMBER, new Integer(newValue));
						}
					}

					public String toString() {
						return "count the total number of lines";
					}
					
				});
			} else {
				delegate.addOnEnd(pos, new XPathMapPositionEffect() {
					
					public void apply(XPathPosition pos, XPathsMapBindings xpathBindings) {
						Integer cnt = (Integer) map.get(ConversionsConsts.TOTAL_ROW_NUMBER);
						int newValue = cnt.intValue() + 1;
						map.put(ConversionsConsts.TOTAL_ROW_NUMBER, new Integer(newValue));
					}
					
					public String toString() {
						return "count the total number of lines";
					}
					
				});
			}
		}
		
		return new MapCollectorActivableLeafElementHandler() {

			public Map getState() {
				return map;
			}

			public boolean isActive() {
				return delegate.isActive();
			}

			public void text(BaseXPathPosition pos, String text) {
				delegate.text(pos, text);
			}

			public void endElement(XPathPosition pos) {
				delegate.endElement(pos);
			}

			public void startElement(XPathPosition pos) {
				delegate.startElement(pos);
			}
			
		};
	}
	

}
