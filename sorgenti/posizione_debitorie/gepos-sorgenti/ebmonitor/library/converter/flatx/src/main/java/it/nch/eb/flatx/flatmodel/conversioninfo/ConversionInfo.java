/**
 * Created on 04/gen/08
 */
package it.nch.eb.flatx.flatmodel.conversioninfo;

import java.io.Serializable;

import it.nch.eb.flatx.bean.types.SizedConverter;

public class ConversionInfo implements SizedConverter, Comparable, Serializable, IConversionInfo {

	private static final long	serialVersionUID	= -167522517394313530L;
	private final SizedConverter delegate;
	private final int	position;
	
	private String name;

	public ConversionInfo(int pos, SizedConverter delegate) {
		this(pos, delegate, null);
	}

	public ConversionInfo(int pos, SizedConverter delegate, String name) {
		if (pos < 0) throw new IllegalStateException("pos should be more than 0");
		if (delegate==null) throw new NullPointerException();
		this.position = pos;
		this.delegate = delegate;
		this.name = name;
	}

	public String encode(String stringModel) {
		return delegate.encode(stringModel);
	}

	public Integer getLength() {
		return delegate.getLength();
	}
	
	public SizedConverter getConverter() {
		return delegate;
	}

	public String getName() {
		return name;
	}
	
	public int getPosition() {
		return position;
	}

	public void setName(String name) {
		this.name = name;
	}

	static int compare(int a, int b) {
		if (a==b) return 0;
		else if (a<b) return -1;
		else return 1;
	}

	public int compareTo(Object o) {
		if (o != null && o instanceof ConversionInfo) {
			ConversionInfo other = (ConversionInfo) o;
			return compare(this.position, other.position);
		}
		return 1;
	}

	public String toString() {
		return getName() + " pos[" + getPosition() + "]converter[" + getConverter() + "]";
	}

}