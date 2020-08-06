/**
 * Created on 29/nov/07
 */
package it.nch.eb.flatx.flatmodel;

import java.io.Reader;
import java.io.Writer;


/**
 * @author gdefacci
 */
public interface ConversionService {
	
	void convert(Reader reader, Writer writer);

}
