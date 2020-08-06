/*
 * Created on 16-nov-2005
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package it.nch.fwk.fo.core;

import it.nch.fwk.fo.das.exception.DasUncheckedException;

import java.io.Serializable;

/**
 * @author ffratoni
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface BackEndContext extends Serializable {
	
	public StatelessSessionManager getStatelessSessionManager()throws DasUncheckedException;
	 
	
	public String getPathMenuCorrente();

}
