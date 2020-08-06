/**
 * Created on 10/mar/2009
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.common.utils.resource.StringPredicate;


/**
 * @author gdefacci
 */
public class TryConverter implements Converter {
	
	public static final StringPredicate	outputAlwaysOK = new StringPredicate() {

		private static final long serialVersionUID = 1L;

		public boolean match(String str) {
			return true;
		}
		
	};
	
	private static final org.slf4j.Logger log = ResourcesUtil.createLogger(TryConverter.class);
	
	private static final long	serialVersionUID	= -4801457369252610269L;

	private final Converter[] converters;
	private final StringPredicate	validty;

	public TryConverter(Converter[] converters) {
		this(converters, outputAlwaysOK);
	}
	public TryConverter(Converter[] converters, StringPredicate validResultPredicate) {
		this.converters = converters;
		this.validty = validResultPredicate;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof TryConverter)) return false;
		TryConverter othr = (TryConverter) obj;
		boolean res = true;
		int idx = 0;
		while (res && idx <converters.length) {
			res = othr.converters[idx].equals(converters[idx]);
			idx ++;
		}
		return res;
	}
	
	public String encode(String stringModel) {
		String res = null;
		for (int i = 0; i < converters.length && res==null; i++) {
			Converter cnv = converters[i];
			try {
				String lres = cnv.encode(stringModel);
				if (validty.match(lres)) {
					res = lres;
				}
			} catch (Exception e) {
				log.warn("error converting '" + stringModel + "' with " + cnv + " trying next converter");
			}
		}
		return res;
	}
	
	public Converter[] getConverters() {
		return converters;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < this.converters.length; i++) {
			Converter cnv = this.converters[i];
			sb.append(cnv.toString());
		}
		return sb.toString();
	}
	
}
