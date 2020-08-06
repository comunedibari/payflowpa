/**
 * Created on 03/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.flatx.flatmodel.verifiers.GetterStrategy;


/**
 * @author gdefacci
 */
public interface IRecordWriter {
	
	public void write(Object model, GetterStrategy getter);
	public void write(Object model);

}
