/**
 * Created on 26/feb/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.flatx.flatmodel.flatfile.parser.IParser;




/**
 * @author gdefacci
 */
public interface ParsersFactory {
	
	IParser createParser();

}
