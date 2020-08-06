package it.nch.eb.flatx.flatmodel.objectconverters;

/**
 * @author gdefacci
 */
public class ToBytesObjectConverter implements ToObjectConverter {
	private static final long serialVersionUID = 6829686895879460610L;
	private final byte[] stereoType = new byte[0];

	public Object convert(String str) {
		return str.getBytes();
	}

	public Class getConversionTargetClass() {
		return stereoType.getClass();
	}
}