/**
 * 18/set/2009
 */
package it.nch.gen.xsqlcmd;

import java.io.File;

public class GenInfo {
	final File srcFolder;// = new File("src/generators");
	final File workBookFile;// = new File("xls/pendenze-full.xls");
	final File ibatisFolder;// = new File(srcFolder, "it/nch/sample/ibatis");
	final String targetPackage;// = "it.nch.eb.xsqlcmd.test.dbtrpos.gen";

	public GenInfo(File srcFolder, File workBookFile, String targetPackage,
			File ibatisFolder) {
		super();
		this.srcFolder = srcFolder;
		this.workBookFile = workBookFile;
		this.ibatisFolder = ibatisFolder;
		this.targetPackage = targetPackage;
	}
	
	public File getSrcFolder() {return srcFolder;}
	public File getWorkBookFile() {return workBookFile;}
	public File getIbatisFolder() {return ibatisFolder;}
	public String getTargetPackage() {return targetPackage;}
}