/*
 * Created on 2-nov-2005
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package it.nch.fwk.fo.util;

import java.net.InetAddress;

/**
 * @author finsaccanebbia
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class HostIdentifier {
	/**
	 * Recupero hostname
	 * @return String
	 */
	public static String getHostname() {
		String h = null;
		try {
			h = InetAddress.getLocalHost().getHostName();
		} 
		catch (Exception e) { 
			try {
				h = InetAddress.getLocalHost().getHostAddress();
			}
			catch (Exception e2) {
				h = "Host unknown";
			}
		}
		return h;
	}
}