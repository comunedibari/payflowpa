/*
 * Created on Sep 26, 2008 by Riccardo Cannas
 *
 */
package it.nch.erbweb.business.cbi.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Riccardo
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class NumeroRighePaginaPreferenze {

	// 10, 25, 50
	private final static String NUM_PAG_1 = "10";
	private final static String NUM_PAG_2 = "25";
	private final static String NUM_PAG_3 = "50";

	public static List getValues() {
		List list = new ArrayList();
		list.add(new String(NUM_PAG_1));
		list.add(new String(NUM_PAG_2));
		list.add(new String(NUM_PAG_3));
		return list;
	}
}
