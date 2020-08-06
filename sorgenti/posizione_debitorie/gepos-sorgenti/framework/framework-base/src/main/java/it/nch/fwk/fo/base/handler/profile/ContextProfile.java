/*
 * File: ContextProfile.java
 * Package: com.etnoteam.service.handler.profile
 * 
 * Revision: $Revision: 1.1.1.1 $ 
 * Last revision by: $Author: CattaniA $
 * Last revised on: $Date: 2006/05/03 11:06:45 $ 
 * Created on: 11-ott-03 - 16.22.00
 * Created by: finsaccanebbia (Etnoteam)
 */
package it.nch.fwk.fo.base.handler.profile;

import java.io.Serializable;

/**
 * @author finsaccanebbia
 *
 * <br>
 * Interfaccia implementata da tutti gli oggeti che hanno una definizion
 * interna della multiprofilazione per banca, canale, lingua come ad 
 * esempio la UserContext
 * 
**/
public interface ContextProfile extends Serializable {
	/**
	 * Returns the bank.
	 * @return String
	 */
	public String getBank() ;

	/**
	 * Returns the channel.
	 * @return String
	 */
	public String getChannel() ;

	/**
	 * Returns the language.
	 * @return String
	 */
	public String getLanguage() ;
	
}
