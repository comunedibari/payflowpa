/**
 * Created on 07/mag/08
 */
package it.nch.eb.flatx.flatmodel;

import java.io.Serializable;

import it.nch.eb.flatx.bean.types.Converter;

public interface ConverterFactory extends Serializable {
	Converter createConverter(it.nch.eb.flatx.bean.types.Converter converter);
}