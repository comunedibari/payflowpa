/**
 * Created on 23/nov/07
 */
package it.nch.fwk.checks;

import java.util.Iterator;

import it.nch.fwk.core.NamespacesInfos;


/**
 * @author gdefacci
 */
public interface IteratorsFactory {
	
	String descendantOrSelf = "descendantOrSelf";
	
	Iterator getIterator(String name, IElementCursor root);
	NamespacesInfos getNamespaces();

}
