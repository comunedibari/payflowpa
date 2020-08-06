/**
 * 04/ago/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.common.utils.resource.ClasspathResources;
import it.nch.eb.common.utils.resource.StringPredicate;

import java.util.Collection;
import java.util.Iterator;

/**
 * @author gdefacci
 */
public class ClasspathTestsRetriever implements DBTestsRetriever {
	
	private final DBTestsFactory testsFactory;
	
	public ClasspathTestsRetriever(DBTestsFactory testsFactory) {
		this.testsFactory = testsFactory;
	}

	public FullDbTest[] retrieveTests(final String fldrLoc) {

		Collection/*<String>*/list = allResources(expectedFileInFolder(fldrLoc));
		FullDbTest[] tests = new FullDbTest[list.size()];
		
		Iterator it = list.iterator();
		for (int i=0; i < list.size(); i++) {
			String expctdDataSetLoc = (String) it.next();
			final String containingFolder = folderName( expctdDataSetLoc );
			Collection resInFolder = allResources(insideFolder(containingFolder));
			String initialDatasetLoc = null;
			String inputFileLoc = null;
			
			for (Iterator it1 = resInFolder.iterator(); it1.hasNext();) {
				String resource = (String) it1.next();
				if (!isExpectedFile(resource)) {
					if (isInitialDataSetFile(resource)) {
						if (initialDatasetLoc!=null) {
							throw new IllegalStateException("cant happen");
						}
						initialDatasetLoc = resource;
					} else if (isXmlFile(resource)) {
						if (inputFileLoc!=null) {
							throw new IllegalStateException("more than 1 input file inside " + containingFolder + "\nincluded resources " + resInFolder);
						} else {
							inputFileLoc = resource;
						}
					}
				}
			}
			tests[i] = testsFactory.newDbTest(initialDatasetLoc, inputFileLoc, expctdDataSetLoc);
		}

		return tests;
	}

	public static StringPredicate expectedFileInFolder(final String fldrLoc) {
		return new StringPredicate() {
			private static final long serialVersionUID = 1L;

			public boolean match(String str) {
				return isExpectedFile(str) && str.startsWith(fldrLoc + "/");
			}
		};
	}

	public static StringPredicate insideFolder(final String containingFolder) {
		return new StringPredicate() {

			private static final long serialVersionUID = 1L;

			public boolean match(String str) {
				int sgNumb = numberOfSegments(containingFolder) + 1;
				if (str.startsWith(containingFolder + "/")) {
					int nsgmnts = numberOfSegments(str);
					return nsgmnts == sgNumb;
				}
				return false;
			}
			
		};
	}

	private boolean isXmlFile(String resource) {
		return resource.endsWith(".xml");
	}

	private static boolean isInitialDataSetFile(String resource) {
		return resource.endsWith("/" + INITIAL_DATASET);
	}

	private static boolean isExpectedFile(String resource) {
		return resource.endsWith("/" + EXPECTED_DATASET);
	}
	
	private static int numberOfSegments( String nm) {
		int idx = nm.indexOf("/");
		if (idx < 0) return 0;
		else return 1 + numberOfSegments(nm.substring(idx + 1));
	}
	
	/** FIXME: slow, introduce a cache */
	private Collection/*<String>*/ allResources( StringPredicate pred ) {
		ClasspathResources lister = new ClasspathResources(pred);
		return lister.getResources();
	}

	private String folderName(String classpathLoc) {
		int sepIdx = classpathLoc.lastIndexOf("/");
		if (sepIdx < 0) throw new IllegalStateException("tests not allowed on root package");
		else {
			return classpathLoc.substring(0, sepIdx); 
		}
	}

}
