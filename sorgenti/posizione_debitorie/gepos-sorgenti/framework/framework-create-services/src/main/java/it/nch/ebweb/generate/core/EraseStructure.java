/*
 * Creato il 27-mar-2006
 *
 * TODO Per modificare il modello associato a questo file generato, aprire
 * Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
package it.nch.ebweb.generate.core;

import java.io.File;

/**
 * @author EE10057
 * 
 * TODO Per modificare il modello associato al commento di questo tipo generato,
 * aprire Finestra - Preferenze - Java - Stile codice - Modelli codice
 */
public class EraseStructure {

	static String path;

	static File todelete;

	public static void erase_ORM_Form() {
		//TODO: filter the static classes in orm project
		todelete = new File(path);
		recursiveErase(todelete);


		path = CreatorCostanst.FOLDER_BUSINESS_C_FORM_GENERATED;
		todelete = new File(path);
		recursiveErase(todelete);
		System.out.println("FORM = " + path);

		path = CreatorCostanst.FOLDER_BUSINESS_COMMON_GENERATED;
		todelete = new File(path);
		recursiveErase(todelete);
		System.out.println("COMMON = " + path);

		path = CreatorCostanst.FOLDER_BUSINESS_I_FORM_GENERATED;
		todelete = new File(path);
		recursiveErase(todelete);
		System.out.println("COMMON = " + path);

		path = CreatorCostanst.FOLDER_ORM_CLIENT_GENERATED;
		todelete = new File(path);
		recursiveErase(todelete);
		System.out.println("COMMON = " + path);

	}

	public static void erase_Service_Model_Interface() {

		path = CreatorCostanst.FOLDER_SERVICE_MODEL_INTERFACE + "it/";
		todelete = new File(path);
		recursiveErase(todelete);
		System.out.println("Service Model Interface = " + path);

	}

	public static void erase_Service_Model() {

		path = CreatorCostanst.FOLDER_SERVICE_MODEL + "it/";
		todelete = new File(path);
		recursiveErase(todelete);
		System.out.println("Service Model Interface = " + path);

	}
	
	public static void erase_Business_Delegator() {
		path = CreatorCostanst.FOLDER_BUSINESS_DELEGATOR + "it/nch/iris";
		todelete = new File(path);
		recursiveErase(todelete);
		System.out.println("Service Business Delegate = " + path);

	}

	private static void recursiveErase(File file) {

		file.delete();
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				((File) files[i]).delete();
				recursiveErase(((File) files[i]));
			}
		}
	}

}
