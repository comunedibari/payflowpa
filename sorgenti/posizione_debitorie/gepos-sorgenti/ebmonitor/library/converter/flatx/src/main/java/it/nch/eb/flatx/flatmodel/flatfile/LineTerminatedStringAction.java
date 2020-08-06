/**
 * Created on 04/apr/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import it.nch.eb.flatx.flatmodel.conversioninfo.StringAction;


/**
 * @author gdefacci
 */
public interface LineTerminatedStringAction extends StringAction {

	void printLineTerminator();
}
