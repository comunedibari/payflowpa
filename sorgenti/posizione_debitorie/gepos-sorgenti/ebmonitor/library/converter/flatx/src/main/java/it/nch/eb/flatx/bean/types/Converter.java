/**
 * Created on 15/lug/07
 */
package it.nch.eb.flatx.bean.types;

import java.io.Serializable;


public interface Converter extends Serializable {

	String encode(String stringModel);
	
}
