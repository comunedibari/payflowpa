/*
 * Created on Jul 18, 2007
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
package it.nch.profile;

import it.nch.fwk.fo.interfaces.FrontEndContext;

import java.io.Serializable;



public abstract class ProfileManager implements  IProfileManager, Serializable {

	public abstract FrontEndContext getFec();

	public abstract void setFec(FrontEndContext fec);

}
