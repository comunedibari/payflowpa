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
public class SelezionaElencoBonificoItalia {
	// KEY  
	private final static String DISPOSIZIONI = "Disposizioni";
	private final static String DISTINTE = "Distinte";
	private final static String IMPORTAZIONI = "Importazioni";
	private final static String SPEDIZIONI = "Spedizioni";
	private final static String DISTINTE_COMPLETE = "DistinteComplete";
	
	public static List getValues() {
		List list = new ArrayList();
		list.add(new String(DISPOSIZIONI));
		list.add(new String(DISTINTE));
		list.add(new String(IMPORTAZIONI));
		list.add(new String(SPEDIZIONI));
		list.add(new String(DISTINTE_COMPLETE));
		return list;
	}
}
