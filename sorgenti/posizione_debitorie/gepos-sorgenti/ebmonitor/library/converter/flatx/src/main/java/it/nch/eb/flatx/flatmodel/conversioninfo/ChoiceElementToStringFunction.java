/**
 * Created on 10/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.eb.flatx.bean.types.Converter;
import it.nch.eb.flatx.flatmodel.xpath.BaseXPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.IXPathMapScope;
import it.nch.eb.flatx.flatmodel.xpath.XPathPosition;
import it.nch.eb.flatx.flatmodel.xpath.XPathsMapBindings;
import it.nch.eb.flatx.flatmodel.xpath.XPathsParser;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;


/**
 * @author gdefacci
 */
public class ChoiceElementToStringFunction implements XPathsMapBindinsManagerStringFunction {
	
	public static final boolean DEFAULT_OPTIONALITY = false;	// by default choices are mandatory
	
	private final 	List/*<ChoiceModel>*/ 	choices  = new ArrayList();
	private final	boolean optional ;
	
	
	static class ChildrenExistsElementPredicate implements ElementPredicate {
		
		private final BaseXPathPosition suffix;
		
		public ChildrenExistsElementPredicate(String suffix) {
			this.suffix = XPathsParser.instance.parseXPath(suffix);
		}

		public boolean match(XPathPosition pos, IXPathMapScope elemsMap) {
			BaseXPathPosition real = pos.concat(suffix);
			String res = elemsMap.get(real);
			return res!=null;
		}
		
	}
	
	/*
	 * all functionns included in the choices list will receive the element with that path 
	 */
	protected static class ChoiceModel {
		public final ElementPredicate predicate;
		public final XPathsMapBindinsManagerStringFunction function;
		
		public ChoiceModel(ElementPredicate predicate, XPathsMapBindinsManagerStringFunction function) {
			this.function = function;
			this.predicate = predicate;
		}
		
		public ChoiceModel(ElementPredicate predicate, final ElementToStringFunction function) {
			this(predicate, new XPathsMapBindinsManagerStringFunction() {

				public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
					return function.getValue(pos, elemsMap);
				}
				
			});
		}
		
		public String toString() {
			return "choice model - predicate:" + predicate + " funtion " + function;
		}
	}
	
	public ChoiceElementToStringFunction() {
		this(DEFAULT_OPTIONALITY);
	}
		
	public ChoiceElementToStringFunction(boolean optional) {
		this.optional = optional;
	}
	
	public ChoiceElementToStringFunction(boolean optional, String[] choices) {
		this(optional);
		for (int i = 0; i < choices.length; i++) {
			this.or(choices[i]);
		}
	}
	
	public ChoiceElementToStringFunction(String[] choices) {
		this(DEFAULT_OPTIONALITY);
		for (int i = 0; i < choices.length; i++) {
			this.or(choices[i]);
		}
	}
	
	public ChoiceElementToStringFunction(boolean optional, Converter cnv, String[] choices) {
		this(optional);
		for (int i = 0; i < choices.length; i++) {
			this.or(choices[i], cnv);
		}
	}
	
	protected ChoiceElementToStringFunction or(ChoiceModel mod) {
		this.choices.add( mod );
		return this;
	}
	
	private static ChildrenExistsElementPredicate existChildPredicate(String pathSfx) {
		return new ChildrenExistsElementPredicate(pathSfx);
	}
	
	public ChoiceElementToStringFunction or(String xpathPrd, ElementToStringFunction funct) {
		return or(new ChoiceModel( existChildPredicate(xpathPrd), funct));
	}
	
	public ChoiceElementToStringFunction or(String xpathPrd, XPathsMapBindinsManagerStringFunction funct) {
		return or(new ChoiceModel( existChildPredicate(xpathPrd ), funct));
	}
	
	public ChoiceElementToStringFunction or(String xpathPrd, final String functionXpath) {
		final BaseXPathPosition suffix = XPathsParser.instance.parseXPath(functionXpath);
		return or( xpathPrd , new XPathsMapBindinsManagerStringFunction() {

			public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
				BaseXPathPosition rpos = pos.concat(suffix);
				return elemsMap.get(rpos);
			}
			
		});
	}
	
	public ChoiceElementToStringFunction or(String xpath) {
		return or( xpath , xpath);
	}
	
	public ChoiceElementToStringFunction or(String xpath, Converter cnv) {
		return or( xpath , xpath, cnv);
	}
	
	public ChoiceElementToStringFunction or(String xpathPrd, String functionXpath, final Converter cnv) {
		final BaseXPathPosition suffix = XPathsParser.instance.parseXPath(functionXpath);
		return or( xpathPrd , new XPathsMapBindinsManagerStringFunction() {

			public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
				BaseXPathPosition rpos = pos.concat(suffix);
				String res = elemsMap.get(rpos);
				if (cnv!=null && res!=null) return cnv.encode(res);
				else return res;
			}
			
		});
	}

	private String choicesToString() {
		StringBuffer sb = new StringBuffer();
		for (Iterator it = choices.iterator(); it.hasNext();) {
			ChoiceModel cm = (ChoiceModel) it.next();
			sb.append(cm);
			sb.append("\n");
		}
		return sb.toString();
	}

	public String getValue(XPathPosition pos, XPathsMapBindings elemsMap, IBindingManager bindingManager) {
		String res = null;
		for (Iterator it = choices.iterator(); it.hasNext() && res==null;) {
			ChoiceModel choice = (ChoiceModel) it.next();
			if (choice.predicate.match(pos, elemsMap)) {
				res = choice.function.getValue(pos, elemsMap, bindingManager);
			}
		}
		if (optional && res == null) 
			throw new IllegalStateException("\ncant find the mandatory choice \n" + choicesToString() + "\n\n inside xpaths map : \n" + elemsMap); 
		return res;
	}
	

}
