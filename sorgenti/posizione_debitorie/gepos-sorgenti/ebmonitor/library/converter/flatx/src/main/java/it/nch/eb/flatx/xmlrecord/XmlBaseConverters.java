/**
 * Created on 09/mar/2009
 */
package it.nch.eb.flatx.xmlrecord;

import it.nch.eb.flatx.bean.types.Converter;


/**
 * @author gdefacci
 */
public interface XmlBaseConverters {
	
	Converter dateAsLongString = new DateToMillisConverter("yyyy'-'MM'-'dd'T'HH':'mm':'ss");
	Converter dateYYY_MM_DD_AsLongString = new DateToMillisConverter("yyyy'-'MM'-'dd");
	Converter stringToBooleanInteger  = new StringtoBooleanIntegerConverter();

}
