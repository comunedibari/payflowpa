/**
 * Created on 06/dic/07
 */
package it.nch.eb.flatx.bean.types;

import it.nch.eb.common.utils.Alignments;
import it.nch.eb.common.utils.RegexpUtils;


/**
 * @author gdefacci
 */
public class StripperConverter implements FillerConverter, ReversibleConverter {
	
	private static final long	serialVersionUID	= 4733663645650791950L;
	public final FillerConverter inverse;

	public StripperConverter(FillerConverter inverse) {
		this.inverse = inverse;
	}

	public String encode(String stringModel) {
		if (stringModel==null || "".equals(stringModel.trim())) return stringModel;
		return RegexpUtils.removeFiller(stringModel, inverse.getFiller(), inverse.getAlignment());
	}

	/**
	 * expeceted size for input
	 */
	public Integer getLength() {
		return inverse.getLength();
	}

	public String toString() {
		return "strip converter filler : [" + inverse.getFiller() + "] length : [" + inverse.getLength() + "]";
	}

	public Converter getReverse() {
		return reversed(inverse);
//		return inverse;
	}

	private Converter reversed(FillerConverter inverse2) {
		if (inverse2 instanceof ReversibleConverter) {
			return ((ReversibleConverter)inverse2).getReverse();
		} else {
			return inverse2;
		}
	}

	public int getAlignment() {
		return Alignments.LEFT;
	}

	public String getFiller() {
		return inverse.getFiller();
	}
	
}
