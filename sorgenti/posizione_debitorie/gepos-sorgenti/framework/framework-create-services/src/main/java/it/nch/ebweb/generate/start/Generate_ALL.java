/*
 * Creato il 12-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.start;

import it.nch.ebweb.generate.backend.BaseGenerator;
import it.nch.ebweb.generate.backend.Create_BE_Service;
import it.nch.ebweb.generate.backend.service.Service;
import it.nch.ebweb.generate.core.CreatorCostanst;
import it.nch.ebweb.generate.core.EraseStructure;
import it.nch.ebweb.generate.core.Generic;
import it.nch.ebweb.generate.core.MalformedConfigurationException;
import it.nch.ebweb.generate.db.CreateStructureORM;
import it.nch.ebweb.generate.db.ProcessDDLSchema;

import java.io.File;
import java.util.StringTokenizer;

/**
 * @author FelicettiA
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class Generate_ALL {

/*	public static void main(String[] args) {

		try {
		    
		    
		    EraseStructure.erase_ORM_Form();
		    EraseStructure.erase_Business_Delegator();
		    EraseStructure.erase_Service_Model();
		    EraseStructure.erase_Service_Model_Interface();
		    
		    //System.exit(0);
		    
		    
			generateSchemaDB();
			generateAllService();

			System.out.println("");
			System.out.println("");
			System.out.println("");
			System.out.println("          ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("              F I N E    G E N E R A Z I O N E    B A C K - E N D");
			System.out.println("          ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++");
			System.out.println("");
			System.out.println("");

		} catch (Exception e) {
			// TODO Blocco catch generato automaticamente
			e.printStackTrace();
			System.err.println("");
			System.err.println("                    ++++++++++++++++++++++++++++++++++++");
			System.err.println("                              E R R O R E");
			System.err.println("                    ++++++++++++++++++++++++++++++++++++");
			System.err.println("");
			System.err.println("");
			System.err.println("*******************************************************************");
			System.err.println(e.getMessage());
			System.err.println("*******************************************************************");

		}

	}

	private static void generateSchemaDB()throws MalformedConfigurationException {

		ProcessDDLSchema pdddl = new ProcessDDLSchema();
		CreateStructureORM cform = new CreateStructureORM();

		//pdddl.epureDDLFile("D:\\Apache\\pddlioli2.sql");
		//pdddl.epureXMIFile("D:\\Apache\\Moduli MDA\\schemadb.xml");
		cform.generateAllBusinessObject();
		System.out.println("");
		System.out.println("");
		System.out.println("");
		System.out.println("             ++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("                      F I N E    G E N E R A Z I O N E");
		System.out.println("             ++++++++++++++++++++++++++++++++++++++++++++++++");
		System.out.println("");
		System.out.println("");

	}

	private static void generateAllService() throws InstantiationException, IllegalAccessException, ClassNotFoundException {

		String root = CreatorCostanst.ROOT_FOLDER;
		root += "PRJ_Create_Services/src/it/nch/ebweb/generate/servizi";

		
		BaseGenerator.populateProperties();
		
		
		//System.out.println("========>"+root);
		File servizi = new File(root);

		File servs[] = servizi.listFiles();

		for (int i = 0; i < servs.length; i++) {

			//System.out.println("========> "+((File)servs[i]).getName());
			String name = ((File) servs[i]).getName();
			StringTokenizer st = new StringTokenizer(name, ".");

			//System.out.println("========> tot pa ="+st.countTokens());
			String ns = "";

			if (st.countTokens() > 1) {

				ns = st.nextToken();
				String p = st.nextToken().trim();
				if (p.equals("java")) {

					Generic gen = null;

					//gen = (Generic)Class.forName(
					// "it.nch.ebweb.generate.servizi."+((File)servs[i]).getName()).newInstance();
					gen = (Generic) Class.forName("it.nch.ebweb.generate.servizi." + ns).newInstance();

					Service service = gen.getService();
					Create_BE_Service cbes = new Create_BE_Service();
					cbes.createServiceBackEnd(service, false);
					//cbes.createServiceBackEnd(service,true);

				}
			}
		}
	}*/
}
