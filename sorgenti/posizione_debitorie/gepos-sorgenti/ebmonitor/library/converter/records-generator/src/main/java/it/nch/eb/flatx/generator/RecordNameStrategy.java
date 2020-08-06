/**
 * Created on 04/set/2008
 */
package it.nch.eb.flatx.generator;

import it.nch.eb.flatx.flatmodel.IRecord;


/**
 * @author gdefacci
 */
public interface RecordNameStrategy {

	String get(IRecord rec);
}
