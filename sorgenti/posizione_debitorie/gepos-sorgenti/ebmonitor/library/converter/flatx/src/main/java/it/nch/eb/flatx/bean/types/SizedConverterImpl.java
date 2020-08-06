/**
 * Created on 29/giu/07
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;


/**
 * @author gdefacci
 */
public class SizedConverterImpl implements FillerConverter, ReversibleConverter, DelegatorConverter {
	
	private static final long	serialVersionUID	= 4783483899397689474L;
	public final Integer size;
	public final String filler;
	public final int alignment;
	public final Converter delegate;
	
	public SizedConverterImpl(int len, String filler, int alignment, Converter converter ) {
		this.size = new Integer(len);
		this.filler = filler;
		this.alignment = alignment;
		this.delegate = converter;
	}
	
	public boolean equals(Object obj) {
		if (!(obj instanceof FillerConverter)) return false;
		SizedConverterImpl othr = (SizedConverterImpl) obj;
		boolean res = size.equals(othr.getLength());
		if (res) res = filler.equals(othr.getFiller());
		if (res) res = alignment == othr.getAlignment();
		if (res) res = delegate.equals(othr.delegate);
		return res;
	}

	public int hashCode() {
		int sz = size.hashCode();
		int fh = filler.hashCode();
		int dh = delegate.hashCode();
		return sz + fh + alignment * 7 + dh;
	}

	public String encode(String rawValue) {
		String realValue = rawValue == null ? filler : rawValue;
		String res = delegate.encode(realValue);
		if (res==null || "".equals(res.trim())) res = filler;
		res = RegexpUtils.fill(res, size, filler,alignment);
		checkLength(res);
		return res;
	}
	
	private void checkLength(String res) {
		if (res.length()>size.intValue()) 
			throw new IllegalStateException("[" + res + "] is not [" + size.toString() + "] bytes long but [" + res.length() + "]");
	}

	public Integer getLength() {
		return size;
	}

	public String getFiller() {
		return filler;
	}

	public int getAlignment() {
		return alignment;
	}
	
	public Converter getDelegate() {
		return delegate;
	}

	public String toString() {
		return "sized converter:size[" + size
			+ "]filler[" + filler
			+ "]alignment[" + (alignment==Alignments.LEFT? "LEFT":"RIGHT")
			+ "]wrapped converter[" + this.delegate.toString();
	}

	public Converter getReverse() {
		return new StripperConverter(this);
	}
	
}
