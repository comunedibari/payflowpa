/*
 * Creato il 21-apr-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.backend;

import it.nch.ebweb.generate.backend.service.Service;
import it.nch.ebweb.generate.core.CreatorCostanst;
import it.nch.ebweb.generate.core.Generic;

import java.io.File;
import java.util.StringTokenizer;

/**
 * @author FelicettiA
 *
 * TODO Per modificare il modello associato al commento di questo tipo generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class BaseGenerator {
	
	
	public static void populateProperties()  {

		String root = CreatorCostanst.ROOT_FOLDER;
		root += "services-configuration/src/main/java/it/nch/ebweb/generate/servizi";

		//System.out.println("========>"+root);
		File servizi = new File(root);

		File servs[] = servizi.listFiles();

		for (int i = 0; i < servs.length; i++) {

			String name = ((File) servs[i]).getName();
			StringTokenizer st = new StringTokenizer(name, ".");
			String ns = "";

			if (st.countTokens() > 1) {

				ns = st.nextToken();
				String p = st.nextToken().trim();
				if (p.equals("java")) {

					Generic gen = null;

					try {
						gen = (Generic) Class.forName("it.nch.ebweb.generate.servizi." + ns).newInstance();
					} catch (InstantiationException e) {
						// TODO Blocco catch generato automaticamente
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						// TODO Blocco catch generato automaticamente
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						// TODO Blocco catch generato automaticamente
						e.printStackTrace();
					}
					Service service = gen.getService();
					ServiceProperties sp = ServiceProperties.getIstance();					
					sp.setCategoriaByServizio(service.getName(), CreatorCostanst.getCategoryName( service.getCategoria() ), service.isFrameworkInternalService());

					
				}
			}
		}
	}

}
