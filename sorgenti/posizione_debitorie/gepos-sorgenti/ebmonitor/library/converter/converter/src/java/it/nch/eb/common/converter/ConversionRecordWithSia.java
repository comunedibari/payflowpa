/**
 * Created on 28/mag/08
 */
package it.nch.eb.common.converter;

import it.nch.eb.flatx.bean.types.Converter;


/**
 * @author gdefacci
 */
public abstract class ConversionRecordWithSia extends ConversionRecord {

	private static final long	serialVersionUID	= 7129731065926620827L;

	public ConversionRecordWithSia(String xpath, String tipoRecord) {
		super(xpath, tipoRecord);
	}
	
	protected abstract Converter initSia();

}
