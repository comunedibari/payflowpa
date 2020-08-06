/**
 * 04/ago/2009
 */
package it.nch.xml2sql.test;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author gdefacci
 */
public class FileSytemTestsRetriever implements DBTestsRetriever {
	
	private final DBTestsFactory testsFactory;
	
	public FileSytemTestsRetriever(DBTestsFactory testsFactory) {
		this.testsFactory = testsFactory;
	}

	public FullDbTest[] retrieveTests(String folder) {
		return retrieveTests(new File(folder));
	}
	
	public FullDbTest[] retrieveTests(File folder) {
		if (!folder.exists()) throw new RuntimeException("the folder " + folder + " does not exists");
		if (!folder.isDirectory()) throw new RuntimeException("the file " + folder + " is not a directory");
		File[] files = folder.listFiles();
		List/*<FullDbTest>*/ res = new ArrayList();
		FullDbTest tst = getTest(folder);
		if (tst!=null) {
			res.add(tst);
		}
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (file.isDirectory()) {
				tst = getTest(file);
				if (tst!=null) {
					res.add(tst);
				}
			}
		}
		return (FullDbTest[]) res.toArray(new FullDbTest[0]);
	}

	private FullDbTest getTest(File folder) {
		if (folder.exists() && folder.isDirectory()) {
			File expcdFile = new File(folder, EXPECTED_DATASET);
			if (expcdFile.exists() && !expcdFile.isDirectory()) {
				File itialDataSetFile;
//				File errorProperties;

				itialDataSetFile = new File(folder, INITIAL_DATASET);
//				errorProperties = new File(folder, ERRORS_PROPERTIES);
				
				File[] xmlFiles = folder.listFiles(new FileFilter() {

					public boolean accept(File f) {
						String fn = f.getName();
						if (!fn.equalsIgnoreCase(INITIAL_DATASET) && !fn.equalsIgnoreCase(EXPECTED_DATASET)) {
							return fn.endsWith(".xml");
						} else  {
							return false;
						}
					}
					
				});
				if (xmlFiles.length == 1) {
					return testsFactory.newDbTest( 
							!itialDataSetFile.exists() ? null : itialDataSetFile.getAbsolutePath(), 
							xmlFiles[0].getAbsolutePath(),
							expcdFile.getAbsolutePath() 
							);
				} else if (xmlFiles.length == 0) {
					throw new IllegalStateException("cant find a valid input file inside " + folder.getAbsolutePath());
				} else {
					StringBuffer allfiles = new StringBuffer();
					for (int i = 0; i < xmlFiles.length; i++) {
						allfiles.append(xmlFiles[i]);
						allfiles.append("\n");
					}
					throw new IllegalStateException("xml files found inside " + folder.getAbsolutePath() + "\n" + allfiles);
				}
				
			} else
				return null;
		} else 
			return null;
	}


}
