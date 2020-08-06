/**
 * Created on 01/ott/08
 */
package it.nch.testools;

import it.nch.eb.common.utils.resource.ResourcesUtil;

import java.io.File;

import org.slf4j.Logger;


/**
 * rappresenta l'insieme di risorse in input ad un test/famiglia di tests.
 * @author gdefacci
 */
public class KitFolderInfo {
	
	private static final Logger log = ResourcesUtil.createLogger(KitFolderInfo.class);

	private final File inputFolder;
	private final File expectedValuesFolder;
	
	public KitFolderInfo(File input, File expcd) {
		this.inputFolder = input;
		this.expectedValuesFolder = expcd;
		checkFolderExists(this.inputFolder);
		checkFolderExists(this.expectedValuesFolder);
	}
	
	public KitFolderInfo(File base, String serviceName, String inputFolderName, String expectedValuesFolderName) {
		this( 	new File(new File(base, serviceName), inputFolderName),
				new File(new File(base, serviceName), expectedValuesFolderName) );
	}
	
	static void checkFolderExists(File folder) {
		if (!folder.exists()) {
			log.info("creating folder " + folder.getAbsolutePath());
			folder.mkdirs();
		}
	}
	
	public File getInputFolder() {
		return inputFolder;
	}
	
	public File getExpectedValuesFolder() {
		return expectedValuesFolder;
	}
	
}
