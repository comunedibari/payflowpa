/**
 * 15/giu/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.common.utils.loaders.ReaderLoader;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;

/**
 * @author gdefacci
 */
public class ClasspathTestRunner extends BaseTestRunner {
	
	public ClasspathTestRunner(PendenzeTestConfiguration testConfig,
			String[] commonInitScripts, 
			boolean splitScritps, 
			String testFolderProjectRelativePosition) {
		this(TestModes.VERIFY, testConfig , commonInitScripts, splitScritps, testFolderProjectRelativePosition);
	}
	
	public ClasspathTestRunner(PendenzeTestConfiguration testConfig,
			String opDefault,
			String[] commonInitScripts, 
			boolean splitScritps, 
			String testFolderProjectRelativePosition) {
		this(TestModes.VERIFY, testConfig , opDefault, commonInitScripts, splitScritps, testFolderProjectRelativePosition);
	}
	
	public ClasspathTestRunner(TestMode tesMode,
			PendenzeTestConfiguration testConfig, 
			String testFolderProjectRelativePosition) {
		super(tesMode, testConfig, testFolderProjectRelativePosition);
	}
	
	public ClasspathTestRunner(TestMode tesMode,
			String opDefault,
			PendenzeTestConfiguration testConfig, 
			String testFolderProjectRelativePosition) {
		super(tesMode, testConfig, opDefault, testFolderProjectRelativePosition);
	}


	public ClasspathTestRunner(TestMode tesMode,
			PendenzeTestConfiguration testConfig, 
			String[] commonInitScripts, 
			boolean splitScritps,
			String testFolderProjectRelativePosition) {
		super(tesMode, testConfig, PendenzeConsts.OPERATOR_DEFAULT, commonInitScripts, splitScritps, testFolderProjectRelativePosition);
	}
	
	public ClasspathTestRunner(TestMode tesMode,
			PendenzeTestConfiguration testConfig, 
			String opDefault,
			String[] commonInitScripts, 
			boolean splitScritps,
			String testFolderProjectRelativePosition) {
		super(tesMode, testConfig, opDefault, commonInitScripts, splitScritps, testFolderProjectRelativePosition);
	}
	
	protected ReaderLoader resourcesLoader() {
		return ResourceLoaders.CLASSPATH;
	}

	private final DBTestsRetriever retriever = new ClasspathTestsRetriever(newDBTestFactory());
	protected DBTestsRetriever getTestsRetriever() {
		return retriever;
	}
	
}
