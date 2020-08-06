/**
 * 15/giu/2009
 */
package it.nch.xml2sql.test;

import java.io.File;

import it.nch.eb.common.utils.loaders.ReaderLoader;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;

/**
 * @author gdefacci
 */
public class FilesystemTestRunner extends BaseTestRunner {
	
	public FilesystemTestRunner(TestMode tstMode, PendenzeTestConfiguration testConfig) {
		this(tstMode, testConfig, PendenzeConsts.OPERATOR_DEFAULT, null, false);
	}
	
	public FilesystemTestRunner(PendenzeTestConfiguration testConfig,
			String[] commonInitScripts, boolean splitScritps) {
		this(TestModes.VERIFY, testConfig , PendenzeConsts.OPERATOR_DEFAULT, commonInitScripts, splitScritps);
	}

	public FilesystemTestRunner(TestMode tstMode,
			PendenzeTestConfiguration testConfig, 
			String opDefault,
			String[] commonInitScripts, boolean splitScritps) {
		super(tstMode, testConfig, opDefault, commonInitScripts, splitScritps, null);
	}
	
	public FilesystemTestRunner(TestMode tstMode,
			PendenzeTestConfiguration testConfig, String opDefault,
			String testFolderProjectRelativePosition) {
		super(tstMode, testConfig, opDefault, testFolderProjectRelativePosition);
	}

	public FilesystemTestRunner(TestMode tesMode,
			PendenzeTestConfiguration testConfig, String operatorDefault,
			String[] initScriptsLocations, boolean splitScritps,
			String testFolderProjectRelativePosition) {
		super(tesMode, testConfig, operatorDefault, initScriptsLocations, splitScritps,
				testFolderProjectRelativePosition);
	}

	public FilesystemTestRunner(TestMode tstMode,
			PendenzeTestConfiguration testConfig, String testFolderProjectRelativePosition) {
		super(tstMode, testConfig, testFolderProjectRelativePosition);
	}

	protected ReaderLoader resourcesLoader() {
		return ResourceLoaders.FILESYSTEM;
	}

	private final DBTestsRetriever retriever = new FileSytemTestsRetriever(newDBTestFactory());
	protected DBTestsRetriever getTestsRetriever() {
		return retriever;
	}
	
	public FullDbTest[] retrieveTests(File folder) {
		return retrieveTests(folder.getAbsolutePath());
	}

}
