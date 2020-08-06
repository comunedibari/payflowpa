/**
 * 29/apr/2009
 */
package it.nch.eb.flatx.flatmodel;

import it.nch.eb.flatx.flatmodel.conversioninfo.ElementPredicate;

/**
 * @author gdefacci
 */
public interface PredicateXmlRecordPair {
	
	ElementPredicate getElementPredicate();
	IMappedXMLRecord getXmlRecord();

}
