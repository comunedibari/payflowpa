/**
 * Created on 24/giu/08
 */
package it.nch.eb.common.utils.resource;

import java.io.Serializable;

/**
 * @author gdefacci
 */
public interface StringPredicate extends Serializable {
	boolean match(String str);
}