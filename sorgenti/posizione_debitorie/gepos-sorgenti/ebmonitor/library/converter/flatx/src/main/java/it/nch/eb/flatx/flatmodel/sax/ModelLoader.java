/**
 * Created on 08/ago/2008
 */
package it.nch.eb.flatx.flatmodel.sax;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.conversioninfo.XPathMapPositionEffect;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @author gdefacci
 */
public class ModelLoader implements ActivableLeafElementHandler {
	
	private static final org.slf4j.Logger log = ResourcesUtil.createLogger(ModelLoader.class);
	
	private Set/*<XPathPosition>*/ areas = new TreeSet();

	private 	final XPathsMapBindings xpathsMap ;
	
	private 	final Converter textConverter;
	
	private final Map/*<XPathPosition, List<XPathMapPositionEffect>>*/ startElementCallbacks = new TreeMap(); 
	private final Map/*<XPathPosition, List<XPathMapPositionEffect>>*/ endElementObjectCallbacks = new TreeMap();
	
//	private final Map/*<XPathPosition, XPathPositionStringFunction>*/ startElementXPathEffects = new TreeMap(); 
//	private final Map/*<XPathPosition, XPathPositionStringFunction>*/ endElementXPathEffects = new TreeMap();
	
	private boolean inside = false;
	
	public ModelLoader() {
		this((Converter)null);
	}
	
	public ModelLoader(Converter textcnv) {
		this(textcnv, new TreeSet());
	}
	
	public ModelLoader(Set/*<BaseXPathPosition>*/ topLevelDefinitions) {
		this(null, topLevelDefinitions);
	}
	
	private ModelLoader(Converter textcnv, Set/*<BaseXPathPosition>*/ topLevelDefinitions) {
		this.textConverter = null;
		this.xpathsMap = new XPathsMapBindings(topLevelDefinitions);
//		if (!xpathsMap.isEmpty()) xpathsMap.enterScope();
	}
	
	public void startElement(XPathPosition pos) {
//		execXPathEffects(pos, startElementXPathEffects);
		if (!inside) {
			if (areasInclude(pos)) {
				inside = true;
			} 
		}
		if (inside) execXPathMapEffects(pos, startElementCallbacks);
	}
	
	public void text(BaseXPathPosition pos, String text) {
		if (inside) {
			putInXPathScope(pos, text);
		}
	}

	protected void putInXPathScope(BaseXPathPosition pos, String text) {
		String txt;
		if (this.textConverter==null) txt = text;
		else txt = this.textConverter.encode(text);
		xpathsMap.put(pos, txt);
	}

	public void endElement(XPathPosition pos) {
		if (inside) execXPathMapEffects(pos, endElementObjectCallbacks);
		if (inside) {
			if (areasInclude(pos)) {
				inside = false;
			}
		}
//		execXPathEffects(pos, endElementXPathEffects);
	}

	private boolean areasInclude(BaseXPathPosition pos) {
		return areas.contains(pos.getUnindexed());
	}

	public void addOnStart(BaseXPathPosition pos, XPathMapPositionEffect efct) {
		addEffect(startElementCallbacks, pos, efct);
	}
	
	public void addOnEnd(BaseXPathPosition pos, XPathMapPositionEffect efct) {
		addEffect(endElementObjectCallbacks, pos, efct);
	}
	
//	public void addOnStart(XPathPosition pos, XPathPositionStringFunction efct) {
//		addEffect(startElementXPathEffects, pos, efct);
//	}
////	
//	public void addOnEnd(XPathPosition pos, XPathPositionStringFunction efct) {
//		addEffect(endElementXPathEffects, pos, efct);
//	}
	
	/**
	 * used to specify a set of xpath that neeed to be preserved, while parsing. it defines in the current scope of 
	 * xpathsMap all the xpaths included in xpathsSet.
	 *  
	 * @param xpathsSet
	 */
	public void defineXPaths(Set/*<BaseXPathPosition>*/ xpathsSet) {
		for (Iterator it = xpathsSet.iterator(); it.hasNext();) {
			BaseXPathPosition pos = (BaseXPathPosition) it.next();
			this.xpathsMap.define(pos);
		}
	}
	
	protected void execXPathMapEffects(XPathPosition pos, Map/*<XPathPosition, List<XPathMapPositionEffect>>*/ effects) {
		List/*<XPathsPositionEffect>*/ efctsList = (List) effects.get(pos.getUnindexed());
		if (efctsList!= null && efctsList.size()>0) {
			for (Iterator it = efctsList.iterator(); it.hasNext();) {
				XPathMapPositionEffect efct = (XPathMapPositionEffect) it.next();
				log.debug("\nposition " + pos + "\nexecuting " + efct);
				efct.apply(pos, xpathsMap);
			}
		}
	}
	
	
//	protected void execXPathEffects(XPathPosition pos, Map/*<XPathPosition, XPathPositionStringFunction>*/ effects) {
//		XPathPositionStringFunction efct = (XPathPositionStringFunction) effects.get(pos.getUnindexed());;
//		if (efct!=null) {
//			log.debug("\nposition " + pos + "\nexecuting " + efct);
//			this.xpathsMap.put(pos, efct.apply(pos));
//		}
//	}
//	
//	protected static void addEffect(Map/*<XPathPosition, List<XPathPositionStringFunction>>*/ effectsMap, 
//			XPathPosition pos, 
//			XPathPositionStringFunction efct) {
//		List efctsList = (List) effectsMap.get(pos.getUnindexed());
//		if (efctsList==null) {
//			efctsList =new ArrayList();
//			effectsMap.put(pos.getUnindexed(), efctsList);
//		}
//		efctsList.add(efct);
//	}

	protected static void addEffect(Map/*<BaseXPathPosition, List<XPathMapPositionEffect>>*/ effectsMap, 
			BaseXPathPosition pos, 
			XPathMapPositionEffect efct) {
		List efctsList = (List) effectsMap.get(pos.getUnindexed());
		if (efctsList==null) {
			efctsList =new ArrayList();
			effectsMap.put(pos.getUnindexed(), efctsList);
		}
		efctsList.add(efct);
	}

	public synchronized void addArea(BaseXPathPosition pos) {
		boolean added = false;
		BaseXPathPosition upos = pos.getUnindexed();
		if (!areas.contains(upos)) {
			Set copy = new TreeSet();
			for (Iterator it = this.areas.iterator(); it.hasNext();) {
				BaseXPathPosition ar = (BaseXPathPosition) it.next();
				if (!copy.contains(ar)) {
					if (ar.startsWith(upos) || upos.startsWith(ar)) {
						if (upos.getDepth() < ar.getDepth()) {
							if (!added) copy.add(upos);
						} else {
							copy.add(ar);
						}
						added = true;
					} else {
						copy.add(ar);
					}
				}
			}
			if (!added) copy.add(upos);
			this.areas = copy;
		}
	}

	public Set getAreas() {
		return areas;
	}

	public boolean isActive() {
		return inside;
	}
	
	private void describeCallbacksTo(String descr,
			Map/*<BaseXPathPosition, List<XPathsPositionEffect>>*/ map, StringBuffer sb) {
		if (!map.entrySet().isEmpty()) {
			sb.append(descr);
			sb.append("\n");
			for (Iterator it = map.keySet().iterator(); it.hasNext();) {
				XPathPosition pos = (XPathPosition) it.next();
				List/*<XPathsPositionEffect>>*/ clbkLst = (List) map.get(pos);
				sb.append("at position: ");
				sb.append(pos);
				if (clbkLst.size()==1) {
					sb.append("\ncalllback: "); 
					sb.append(clbkLst.get(0));
					sb.append("\n");
				} else {
					for (Iterator clbkIt = clbkLst.iterator(); clbkIt.hasNext();) {
						XPathMapPositionEffect xpEffct = (XPathMapPositionEffect) clbkIt.next();
						sb.append(" ");
						sb.append(xpEffct.toString());
						sb.append("\n");
					}
				}
			}
		}
	}

	public String toString() {
		StringBuffer sb = new StringBuffer();
		if (areas.size()==0) sb.append("no area specified");
		else {
			sb.append("areas [n." + areas.size() + "]");
			for (Iterator it = this.areas.iterator(); it.hasNext();) {
				XPathPosition areaPos = (XPathPosition) it.next();
				sb.append(areaPos);
				sb.append(", ");
			}
		}
		describeCallbacksTo("on start ", startElementCallbacks, sb);
		describeCallbacksTo("on end ", endElementObjectCallbacks, sb);
		return sb.toString();
	}

	
	/*package*/ XPathsMapBindings getXpathsMap() {
		return xpathsMap;
	}
	
}