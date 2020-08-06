/**
 * 02/ott/2009
 */
package it.nch.xml2sql;

import it.nch.eb.common.utils.loaders.ReaderLoader;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.FromXmlToDbPendenze;
import it.nch.eb.xsqlcmd.dbtrpos.dao.HeaderEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.OtfEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzaXPathValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelExecutionDispatcherFactory;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TablesUidProvider;
import it.nch.eb.xsqlcmd.dbtrpos.error.PendenzeDBErrorsFactory;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandlers;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandlers.Simple;
import it.nch.eb.xsqlcmd.dbtrpos.operations.IBatisPendenzeErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.operations.PendenzeModelExecutionDispatcher;
import it.nch.eb.xsqlcmd.utils.ConnectionEffect;
import it.nch.eb.xsqlcmd.utils.DbHelper;
import it.nch.fwk.core.NamespacesInfos;
import it.nch.xml2sql.test.BaseTestRunner;
import it.nch.xml2sql.test.ClasspathTestRunner;
import it.nch.xml2sql.test.FailingTest;
import it.nch.xml2sql.test.FullDbTest;
import it.nch.xml2sql.test.PendenzeTestConfiguration;
import it.nch.xml2sql.test.TestEnvironments;
import it.nch.xml2sql.test.TestModes;
import it.nch.xml2sql.test.TestOutcome;
import it.nch.xml2sql.test.Xml2sqlDbUnitHelper;

import java.io.File;
import java.io.FileFilter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import junit.framework.TestCase;

import org.dbunit.dataset.IDataSet;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author gdefacci
 */
public class TestsCreate extends TestCase {

	private static NamespacesInfos	nss	= new NamespacesInfos(new String[][] { 
			{ "", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpAllineamentoPendenze" },
			{ "h", "http://www.cart.rete.toscana.it/servizi/iris_1_1/IdpHeader" },
	 });
	
	private PendenzeModelExecutionDispatcherFactory serviceFactory =
		new PendenzeModelExecutionDispatcherFactory();
	
	private static PendenzeTestConfiguration testConfiguration = TestEnvironments.mysql().getTestConfiguration();
	
	public static String lastSegmnt(String flNm) {
		int slIndx = flNm.lastIndexOf("/");
		if (slIndx < 0) return flNm;
		else return flNm.substring(slIndx);
	}
	
	public static class DbTestFiles {
		public final String pre;
		public final String testFileName;
		public DbTestFiles(String testFileName) {
			this(null, testFileName);
		}
		public DbTestFiles(String pre, String testFileName) {
			this.pre = pre;
			this.testFileName = testFileName;
			if (testFileName==null) throw new NullPointerException();
		}
		public String fileName() {
			String fn = testFileName.endsWith(".xml") ? testFileName.substring(0, testFileName.length() - 4) : testFileName;
			return lastSegmnt(fn);
		}
	}
	
	public void _test1a() {
		final Xml2sqlDbUnitHelper helper = new Xml2sqlDbUnitHelper(testConfiguration);
		final IDataSet[] dss = new IDataSet[] {
			BaseTestRunner.loadDataSet(ResourceLoaders.CLASSPATH, "testsv3/del1/expected-dataset.xml")
		};
		helper.withConnection(new ConnectionEffect() {

			public void apply(Connection conn) throws Exception {
				BaseTestRunner.insertDataSet(helper , conn, dss);
			}
			
		});
	}
	
	public void _test1() throws Exception {
		File fldr = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/big-xmls/disjunt");
		File[] files = fldr.listFiles(new FileFilter() {

			public boolean accept(File pathname) {
				return pathname.getName().endsWith(".xml");
			}
			
		});
//		File[] filesS= new File[] {
//			new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/big-xmls/disjunt/v3_1_44.xml")
//		};
		final DbHelper dbHelper = new DbHelper(testConfiguration);
		testConfiguration.initializeDB();
		for (int i = 0; i < files.length; i++) {
			final File file = files[i];
//            System.out.println("----processing " +  file.getAbsolutePath());
//			System.in.read();
			importXml(ResourceLoaders.FILESYSTEM, file.getAbsolutePath() , serviceFactory, testConfiguration);
			dbHelper.withConnection(new ConnectionEffect() {

				public void apply(Connection conn) throws Exception {
					ResultSet rs = conn.createStatement().executeQuery("select count(*) from errori_esiti_pendenza");
					ResultSet rs1 = conn.createStatement().executeQuery("select count(*) from esiti_pendenza where ESITO_PENDENZA = 'KO';");
					rs.next();
					rs1.next();
					System.out.println(file.getAbsolutePath());
					System.out.println("\n\n********************\n\n errori_esiti_pendenza : " +  rs.getInt(1));
					System.out.println("\n\n********************\n\n esiti_pendenza : " +  rs1.getInt(1));
				}
				
			});
		}
		
	}

	public void initAndImport(String fn) {
		long startTime = System.currentTimeMillis();
		testConfiguration.initializeDB();
		
		importXml(ResourceLoaders.FILESYSTEM, fn , serviceFactory, testConfiguration);
		
		System.out.println("finished " + (System.currentTimeMillis() - startTime) + ".ms " + fn);
	}
	
	public void testRunTest() throws Exception {
		ClasspathTestRunner runner = new ClasspathTestRunner(
				TestModes.VERIFY,
				testConfiguration, 
				"resources");
		
		TestOutcome outcome = runner.runTests("testsv3");
		
		if (outcome.getFailTests().length > 0) {
			System.out.println("failing tests");
			FailingTest[] ftest = outcome.getFailTests();
			for (int i = 0; i < ftest.length; i++) {
				FailingTest failingTest = ftest[i];
				System.out.println(failingTest.toString());
			}
		} else {
			System.out.println("All tests completed sucessfully");
			FullDbTest[] sTests = outcome.getSuccessTests();
			for (int i = 0; i < sTests.length; i++) {
				FullDbTest fullDbTest = sTests[i];
				System.out.println(fullDbTest);
			}
		}
	}
	
	public void _testCreateTests() throws IOException {
		File baseTargetFolder = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/resources/testsv3");
		DbTestFiles tfs = new DbTestFiles("tests/v03/tests/ins1.xml");
		createDbUnitTest(baseTargetFolder, tfs);
	}
	
	public void _testCreateAllTests() throws IOException {
		File baseTargetFolder = new File("D:/java/projects/flowmanager-svn/dsengine/xml2sqlcmd/resources/testsv3");
		DbTestFiles[] tfs = new DbTestFiles[] {
			new DbTestFiles("tests/v03/tests/ins1.xml"),
			new DbTestFiles("tests/v03/tests/ins2.xml"),
			new DbTestFiles("tests/v03/tests/ins3.xml"),
//			new DbTestFiles("tests/v03/tests/del1Pre.xml", "tests/v03/tests/del1.xml"),
//			new DbTestFiles("tests/v03/tests/del2Pre.xml", "tests/v03/tests/del2.xml"),
//			new DbTestFiles("tests/v03/tests/repl1Pre.xml", "tests/v03/tests/repl1.xml"),
//			new DbTestFiles("tests/v03/tests/repl2Pre.xml", "tests/v03/tests/repl2.xml"),
//			new DbTestFiles("tests/v03/tests/upd2Pre.xml", "tests/v03/tests/upd2.xml"),
//			new DbTestFiles("tests/v03/tests/updt1Pre.xml", "tests/v03/tests/updt1.xml"),
		};
		for (int i = 0; i < tfs.length; i++) {
			DbTestFiles dbTestFiles = tfs[i];
			createDbUnitTest(baseTargetFolder, dbTestFiles);
		}
	};

	private void createDbUnitTest(File baseTargetFolder, DbTestFiles tfs)
			throws IOException {
		if (!baseTargetFolder.exists()) baseTargetFolder.mkdirs();
		File targetFolder = new File(baseTargetFolder, tfs.fileName());
		testConfiguration.initializeDB();
		Xml2sqlDbUnitHelper helper = new Xml2sqlDbUnitHelper(testConfiguration);
		helper.createDefaultDTD();
		
		if (tfs.pre != null) {
			importXml(tfs.pre, serviceFactory, testConfiguration);
			File initialDbFile = new File(targetFolder, "initial-dataset.xml");
			helper.exportDb(initialDbFile);
		}
		
		importXml(tfs.testFileName, serviceFactory, testConfiguration);
		File expctdDbFile = new File(targetFolder, "expected-dataset.xml");
		helper.exportDb(expctdDbFile);
		InputStream inpIs = ResourceLoaders.CLASSPATH.loadInputStream(tfs.testFileName);
		OutputStream outOs = new FileOutputStream( new File(targetFolder, "inp.xml"));
		ResourcesUtil.copyFile(inpIs, outOs);
	}
	
	private static List/*<DBError>*/ importXml(String classpathName,
			PendenzeModelExecutionDispatcherFactory serviceFactory,
			PendenzeTestConfiguration testConfiguration) {
		return importXml(ResourceLoaders.CLASSPATH, classpathName, serviceFactory, testConfiguration);
	}
	
	private static List/*<DBError>*/ importXml(ReaderLoader loader, String classpathName,
			PendenzeModelExecutionDispatcherFactory serviceFactory,
			PendenzeTestConfiguration testConfiguration) {
		EsitiErrorHandler ibatisErrHandler = new EsitiErrorHandler(testConfiguration.getSqlMapClient(), testConfiguration.getTablesUidProvider() );
		Simple hndlr = new DBErrorsHandlers.Simple(ibatisErrHandler);
		PendenzeModelExecutionDispatcher dispatcher = 
			serviceFactory.create(testConfiguration.getSqlMapClient(), testConfiguration.getTablesUidProvider(), hndlr);
		HeaderEffect headerEffect = null;
		OtfEffect otfEffect = null;
//		InsertPendenzeCartHeaderEffect headerEffect = 
//			new InsertPendenzeCartHeaderEffect(testConfiguration, testConfiguration.getTableNames().getPendenzeCart());
		FromXmlToDbPendenze xml2SqlCmd = newFromXml2Sql(headerEffect, otfEffect, dispatcher, ibatisErrHandler, serviceFactory.getErrorsFactory());
		
		xml2SqlCmd.dispatch(loader.readerFactory(classpathName));
		return hndlr.getAllErrors();
	}
	
	private static FromXmlToDbPendenze newFromXml2Sql(
			HeaderEffect headerEffect,
			OtfEffect otfEffect,
			PendenzeModelEffect execProvider,
			IBatisPendenzeErrorHandler errHandler,
			PendenzeDBErrorsFactory errsFactory) {
		PendenzaXPathValidator xpathValidator = new PendenzaXPathValidator(errHandler, errsFactory);
		FromXmlToDbPendenze xml2SqlCmd = new FromXmlToDbPendenze(headerEffect, otfEffect, execProvider, xpathValidator, nss);
		return xml2SqlCmd;
	}
	
	public void _testGenNext() throws SQLException {
		BeanFactory bf = new ClassPathXmlApplicationContext("assembly/h2/application-context.xml");
 		TablesUidProvider keyProvider = (TablesUidProvider) bf.getBean("tablesUidProvider");
 		DataSource ds = (DataSource) bf.getBean("dataSource");
 		long nexVal = keyProvider.getDestinatari().nextVal(ds.getConnection());
 		System.out.println(nexVal);
	}
	
}
