/**
 * Created on 28/ago/07
 */
package it.nch.fwk.checks;

import java.util.Iterator;

/**
 * @author gdefacci
 */
public interface IElementCursorIterator extends Iterator {
	
//	are client apps interested in this feature? 
//	public IElementCursor lookahead();
	
	public boolean hasNext();
	public IElementCursor nextElement();

}
