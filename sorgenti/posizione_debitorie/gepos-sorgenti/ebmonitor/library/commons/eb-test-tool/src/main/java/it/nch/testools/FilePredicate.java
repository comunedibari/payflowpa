/**
 * Created on 03/ott/08
 */
package it.nch.testools;

import java.io.File;


/**
 * @author gdefacci
 */
public interface FilePredicate {

	boolean match(File f);
}
