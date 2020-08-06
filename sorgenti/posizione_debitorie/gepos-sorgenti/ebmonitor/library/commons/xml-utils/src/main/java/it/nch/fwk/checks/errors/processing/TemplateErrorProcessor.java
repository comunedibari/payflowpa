/**
 * Created on 17/mag/07
 */
package it.nch.fwk.checks.errors.processing;

import it.nch.eb.common.utils.bindings.IBindingManager;
import it.nch.fwk.checks.IElementCursor;
import it.nch.fwk.checks.errors.ErrorInfo;

import java.io.Serializable;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/*
 * 
 */
public class TemplateErrorProcessor extends BaseErrorProcessor implements Serializable {

	private static final long	serialVersionUID	= 3749690560729588183L;

	private static final boolean	DEFAULT_INCLUDE_ERROR_INFO	= false;
	
	private String templateName;
	private ITemplateProvider templateProvider;
	Set/*<String,String>*/ bindingManagerSet = new TreeSet();
	Set/*<String,String>*/ elementSet = new TreeSet();
	Set/*<String,String>*/ errorSet = new TreeSet();

	private boolean	includeErrorInfo;
	
	public TemplateErrorProcessor(ITemplateProvider templateProvider) {
		this(null, templateProvider, DEFAULT_INCLUDE_ERROR_INFO);
	}
	
	public TemplateErrorProcessor(String templateName, ITemplateProvider templateProvider) {
		this(templateName,templateProvider, DEFAULT_INCLUDE_ERROR_INFO);
	}
	
	public TemplateErrorProcessor(String templateName, ITemplateProvider templateProvider, boolean includeErrorInfoInTemplate) {
		this.templateName = templateName;
		this.templateProvider = templateProvider;
		this.includeErrorInfo = includeErrorInfoInTemplate;
	}

	public String produceErrorMessage() {
		return templateProvider.getTemplateValue(this.templateName, getParametersMap());
	}
	
	public final void collectParams(IElementCursor elem) {
		for (Iterator it = this.elementSet.iterator(); it.hasNext();) {
//			String[] pair = (String[]) it.next();
			StringPair pair = (StringPair) it.next();
			setParameter(pair.get(0), elem.child(pair.get(1)).getValue() );
		}
	}

	public final  void collectParams(ErrorInfo error) {
		if  (includeErrorInfo) {
			this.getParametersMap().put("error", error);
		}
	}

	public final  void collectParams(IBindingManager bindings) {
		for (Iterator it = this.bindingManagerSet.iterator(); it.hasNext();) {
			StringPair pair = (StringPair) it.next();
			setParameter(pair.get(0), bindings.get(pair.get(1)));
		}
	}

	public TemplateErrorProcessor mapBindingManager(String id) {
		return mapBindingManager(id,id);
	}
	
	public TemplateErrorProcessor mapBindingManager(String id, String bindingName) {
		StringPair pair = new StringPair(id, bindingName);
		this.bindingManagerSet.add(pair);
		return this;
	}
	
	public TemplateErrorProcessor mapBindingManager(String[][] mappings) {
		for (int i = 0; i < mappings.length; i++) {
			mapBindingManager(mappings[i][0], mappings[i][1]);
		}
		return this;
	}
	
	public TemplateErrorProcessor mapElement(String id) {
		return mapElement(id, id);
	}
	
	public TemplateErrorProcessor mapElement(String id, String xpath) {
		StringPair pair = new StringPair(id, xpath);
		this.elementSet.add(pair);
		return this;
	}
	
	public TemplateErrorProcessor mapElement(String[][] mappings) {
		for (int i = 0; i < mappings.length; i++) {
			mapElement(mappings[i][0], mappings[i][1]);
		}
		return this;
	}

	public void setIncludeErrorInfo(boolean includeErrorInfo) {
		this.includeErrorInfo = includeErrorInfo;
	}
	
	private static class StringPair implements Comparable {
		private String[]	strs;
		StringPair(String s1, String s2) {
			this.strs = new String[] {s1, s2};
		}
		StringPair(String[] ss) {
			if (ss.length!=2) throw new IllegalArgumentException("only acepted String[] have length 2");
			this.strs = ss;
		}
		public boolean equals(Object obj) {
			if (!(obj instanceof StringPair)) return false;
			StringPair p1 = (StringPair) obj;
			return this.get(0).equals(p1.get(0)) && this.get(1).equals(p1.get(1));
		}
		public int hashCode() {
			return super.hashCode();
		}
		String get(int idx) {
			if ((idx!=0) && (idx!=1)) throw new IllegalArgumentException("idx value = [0,1]");
			return strs[idx];
		}
		public int compareTo(Object arg0) {
			if (!(arg0 instanceof StringPair)) return -1;
			if (((StringPair)arg0).equals(this)) {
				return 0;
			}
			return -1;
		}
	}

}