/**
 * Created on 03/gen/08
 */
package it.nch.eb.flatx.flatmodel.flatfile;

import java.io.Serializable;


/**
 * @author gdefacci
 */
public interface ObjectBuilder extends Serializable {

	Object create();
	public Class getProductClass();
}
