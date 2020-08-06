/**
 * 15/giu/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.common.utils.loaders.InputsFactory;
import it.nch.eb.common.utils.loaders.ReaderLoader;
import it.nch.eb.common.utils.loaders.ResourceLoaders;
import it.nch.eb.xsqlcmd.dao.UIdProvider;
import it.nch.eb.xsqlcmd.dbtrpos.dao.EsitiErrorHandler;
import it.nch.eb.xsqlcmd.dbtrpos.dao.FromXmlToDbPendenze;
import it.nch.eb.xsqlcmd.dbtrpos.dao.InsertPendenzeCartHeaderEffect;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzaXPathValidator;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeConsts;
import it.nch.eb.xsqlcmd.dbtrpos.dao.PendenzeModelExecutionDispatcherFactory;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TableNames;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandlers;
import it.nch.eb.xsqlcmd.dbtrpos.operations.PendenzeModelExecutionDispatcher;
import it.nch.eb.xsqlcmd.dbtrpos.operations.DBErrorsHandlers.Simple;
import it.nch.eb.xsqlcmd.utils.ArrayUtils;
import it.nch.eb.xsqlcmd.utils.ConnectionEffect;
import it.nch.eb.xsqlcmd.utils.DbHelper;

import java.io.File;
import java.io.Reader;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableIterator;
import org.dbunit.operation.DatabaseOperation;

import com.ibatis.sqlmap.client.SqlMapClient;

/**
 * @author gdefacci
 */
public abstract class BaseTestRunner {
	
	public static final String XML_CATALOG_CLASSPATH_LOCATION = "xmlcatalog.xml";
	private static final String[] DBUNIT_DTDS_CATALOGS = 
		new String[] { "classpath:/" + XML_CATALOG_CLASSPATH_LOCATION };
	
	private final PendenzeTestConfiguration testConfig;
	private final List/*<String>*/ initScripts = new ArrayList();
	private final String[] initScriptsLocations;
	private final boolean splitScritps;
	private final TestMode testMode;

	private final TableComparisonContainer tableComparisonsContainer;

	private final String testFolderProjectRelativePosition;
	
	private final String operatorDefault;
	
	public BaseTestRunner(TestMode tstMode,
			PendenzeTestConfiguration testConfig,
			String testFolderProjectRelativePosition) {
		this(tstMode, testConfig, PendenzeConsts.OPERATOR_DEFAULT, null, false, testFolderProjectRelativePosition);
	}
	
	public BaseTestRunner(TestMode tstMode,
			PendenzeTestConfiguration testConfig,
			String opDefault,
			String testFolderProjectRelativePosition) {
		this(tstMode, testConfig, opDefault, null, false, testFolderProjectRelativePosition);
	}

	public BaseTestRunner(TestMode tesMode,
			PendenzeTestConfiguration testConfig,
			String operatorDefault,
			String[] initScriptsLocations, 
			boolean splitScritps,
			String testFolderProjectRelativePosition) {
		this.testConfig = testConfig;
		this.testMode = tesMode;
		this.operatorDefault = operatorDefault;
		this.initScriptsLocations = initScriptsLocations;
		this.splitScritps = splitScritps;
		this.testFolderProjectRelativePosition = testFolderProjectRelativePosition;
		this.tableComparisonsContainer = new TableComparisonContainer( defaultTableComparisons(testConfig.getTableNames()) );
	}
	
//	private DBErrorsHandlers.Simple defaultDbErrorsHandler(PendenzeTestConfiguration testConfig) {
//		return new DBErrorsHandlers.Simple(new EsitiErrorHandler(testConfig.getSqlMapClient(),  testConfig.getTablesUidProvider(), operatorDefault));
//	}
	
	protected ReaderLoader commonsScriptsLoader() {
		return ResourceLoaders.CLASSPATH;
	}
	
	protected abstract ReaderLoader resourcesLoader();
	
	public TestOutcome runTests(final File fldr) {
		if (!fldr.exists()) throw new RuntimeException(fldr.getAbsolutePath() + " does not exists");
		if (!fldr.isDirectory()) throw new RuntimeException(fldr.getAbsolutePath() + " is not a folder");
		return runTests(fldr.getAbsolutePath());
	}
	public TestOutcome runTests(final String fldrLoc) {
		return getTestsRun(fldrLoc).run();
	}
	
	public AllTestsRunnable getTestsRun(final String fldrLoc) {
		return new AllTestsRunnable(retrieveTests(fldrLoc));
	}
	
	public TableComparisonContainer compareOptions() {
		return tableComparisonsContainer;
	}
	
	protected boolean performTestAction(
			InputsFactory readerFactory, final String location) throws Exception {
		
		System.out.println("starting test at location " + location);
		
		PendenzeModelExecutionDispatcherFactory serviceFactory = new PendenzeModelExecutionDispatcherFactory(operatorDefault);
		SqlMapClient sqlMapClient = testConfig.getSqlMapClient();
		EsitiErrorHandler ibatisErrHandler = new EsitiErrorHandler(testConfig.getSqlMapClient(),  testConfig.getTablesUidProvider(), operatorDefault);
		Simple errHandler = new DBErrorsHandlers.Simple(ibatisErrHandler);
		PendenzeModelExecutionDispatcher dispatcher = serviceFactory.create(sqlMapClient, testConfig.getTablesUidProvider(), errHandler);
		
		InsertPendenzeCartHeaderEffect insertPendenzeCart = 
			new InsertPendenzeCartHeaderEffect(testConfig, testConfig.getTableNames().getPendenzeCart(), operatorDefault);
		
		PendenzaXPathValidator xpathValidator = 
			new PendenzaXPathValidator(ibatisErrHandler, serviceFactory.getErrorsFactory());
		FromXmlToDbPendenze xml2SqlCmd = new FromXmlToDbPendenze(
				insertPendenzeCart, null,
				dispatcher, 
				xpathValidator,
				testConfig.getNamespacesInfos());
		
		xml2SqlCmd.dispatch(readerFactory);
		
		final Xml2sqlDbUnitHelper helper = new Xml2sqlDbUnitHelper(testConfig);
		helper.withConnection(new ConnectionEffect() {

			public void apply(Connection conn) throws Exception {
				Statement stm = conn.createStatement();
				ResultSet rs = stm.executeQuery("select count(*) from errori_esiti_pendenza;");
				if (rs.next()) {
					int count = rs.getInt(1);
					if (count > 0) {
						System.out.println("***************** ]" + location);
					}
				}
			}
			
		});
		
		return true;
	}
	
	public FullDbTest[] retrieveTests(String folder) {
		return getTestsRetriever().retrieveTests(folder);
	}
	
	protected abstract DBTestsRetriever getTestsRetriever();
	
	public DBTestsFactory newDBTestFactory() {
		return new BaseDBTestFactory(this);
	}
	
	static class BaseDBTestFactory implements DBTestsFactory {
		private BaseTestRunner testRunner;
		public BaseDBTestFactory(BaseTestRunner testRunner) {
			this.testRunner = testRunner;
		}

		public FullDbTest newDbTest(String initialDataSetLoc, 
				final String inputFileLoc, 
				final String expctFileLoc) {
			if (inputFileLoc==null) throw new NullPointerException("inputFileLoc");
			if (expctFileLoc==null) throw new NullPointerException("expctFileLoc");
			
			String[] loadedInitializationScripts = DbHelper.loadScripts( testRunner.initScriptsLocations, testRunner.splitScritps, testRunner.commonsScriptsLoader()); 
			String[] initializationScripts = ArrayUtils.concat(loadedInitializationScripts, testRunner.initScripts); 
			
			IDataSet[] initialDatasets = initialDataSetLoc!= null && initialDataSetLoc.length() > 0 ? testRunner.loadDataSets(new String[]{initialDataSetLoc}) : null;
			IDataSet expctdDataSet = testRunner.loadDataSet(expctFileLoc);
			TestMode testMode2 = testRunner.testMode;
			TestEffect verifyEffect = createVerifyEffect(initialDatasets, expctFileLoc, testMode2);
			return new BaseFullDbTestNew(testRunner.testConfig, initializationScripts, initialDatasets, expctdDataSet, 
					testRunner.tableComparisonsContainer, verifyEffect ) {
				protected boolean testAction() throws Exception {
					try {
						return testRunner.performTestAction(testRunner.resourcesLoader().readerFactory(inputFileLoc), inputFileLoc);
					} catch (Exception e) {
						throw new RuntimeException("exception while processing '" + inputFileLoc + "' cause :"+ e.getMessage(), e);
					}
				}
				
				public String toString() {
					return "testInput(" + inputFileLoc + ")";
				}
				
			};
		}

		private TestEffect createVerifyEffect(final IDataSet[] initialDatasets,
				final String expctFileLoc, TestMode testMode2) {
			final Xml2sqlDbUnitHelper helper = new Xml2sqlDbUnitHelper(this.testRunner.testConfig);
			TestEffect verifyEffect = null;
			if (testMode2.equals(TestModes.FREEZE) ) {
				verifyEffect = new TestEffect() {
					public void apply() {
						File loc = testRunner.testFolderProjectRelativePosition != null ? 
								new File(testRunner.testFolderProjectRelativePosition, expctFileLoc) :
								new File(expctFileLoc);
						helper.exportDb(testRunner.tableComparisonsContainer, loc);
					}
				};
			} else if (testMode2.equals(TestModes.EXECUTE_NO_VERIFY) ) {
				verifyEffect = new TestEffect() {
					public void apply() {
					}
				};
			}
			return verifyEffect;
		};
	}
	
	protected IDataSet[] loadDataSets(String[] locs) {
		IDataSet[] res = new IDataSet[locs.length];
		for (int i = 0; i < locs.length; i++) {
			String location = locs[i];
			res[i] = loadDataSet(location);
		}
		return res;
	}
	
	public static IDataSet loadDataSet(ReaderLoader loader, String loc) {
		try {
			Reader rdr = loader.loadReader(loc);
			CachedDataSet dtSet = new CatalogFlatXmlDataSet(DBUNIT_DTDS_CATALOGS, rdr, true);
			return dtSet;
		} catch (Exception e) {
			throw new RuntimeException("error loading dataset from "  + loc + " with loader " + loader, e);
		} 
	}
	protected IDataSet loadDataSet(String loc) {
		return loadDataSet(resourcesLoader(), loc);
	}
	
	public PendenzeTestConfiguration getTestConfig() {
		return testConfig;
	}

	public String[] getInitScripts() {
		return initScriptsLocations;
	}

	public boolean isSplitScritps() {
		return splitScritps;
	}

	public TestMode getTestMode() {
		return testMode;
	}

	public TableComparisonContainer getTableComparisonsContainer() {
		return tableComparisonsContainer;
	}
	
	public BaseTestRunner addInitializationScript(String initScript) {
		this.initScripts.add(initScript);
		return this;
	}

	public static void moveSeqsAhead(Xml2sqlDbUnitHelper helper, IDataSet ds, Connection conn) {
		try {
			ITableIterator tabIt = ds.iterator();
			while (tabIt.next()) {
				ITable tab = tabIt.getTable();
				int rowCount = tab.getRowCount();
				String realTabName = tab.getTableMetaData().getTableName();
				UIdProvider dbSeq = helper.getSequence(realTabName);
				if (dbSeq!=null) {
					for (int i=0; i< rowCount; i++) {
						dbSeq.nextVal(conn);
					}
				}
			}
		} catch (Exception e) {
			throw new RuntimeException("error moving seuqences ahead " , e);
		}
		
	}

	public static void insertDataSet(Xml2sqlDbUnitHelper helper, Connection conn, IDataSet[] dss) {
		if (dss!=null && dss.length>0) {
			try {
				IDatabaseConnection dbUnitConn = helper.wrapConnection(conn);
				for (int i = 0; i < dss.length; i++) {
					IDataSet ds = dss[i];
					DatabaseOperation.INSERT.execute(dbUnitConn, ds);
					BaseTestRunner.moveSeqsAhead(helper, ds, conn );
					
				}
			} catch (Exception e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			} 
		}
	}

	public static TableComparison[] defaultTableComparisons( final TableNames tableNames) {
		return new TableComparison[] {
				new TableComparison(tableNames.getEsitiPendenza(),
						new String[] { "TS_INSERIMENTO", "TS_AGGIORNAMENTO" }),
				new TableComparison(tableNames.getErroriEsitiPendenza(),
						new String[] { "TS_INSERIMENTO" }),
//				new TableComparison(tableNames.getPendenzeCart(), new String[] {
//						"TIMESTAMP_RICEZIONE", "TS_INSERIMENTO" }),
				new TableComparison(tableNames.getAllegato(), new String[] {
						"TS_AGGIORNAMENTO", "TS_DECORRENZA", "TS_INSERIMENTO" }),
				new TableComparison(tableNames.getPendenze(), new String[] {
						"TS_DECORRENZA", "TS_AGGIORNAMENTO", "OP_ANNULLAMENTO",
						"TS_ANNULLAMENTO", "TS_INSERIMENTO", "TS_MODIFICAENTE",
						"TS_PRESCRIZIONE" }),
				new TableComparison(tableNames.getCondizioniPagamento(),
						new String[] { "TS_AGGIORNAMENTO", "TS_DECORRENZA",
								"TS_INSERIMENTO", "DT_PAGAMENTO", "TS_ANNULLAMENTO" }),
				new TableComparison(tableNames.getDestinatari(), new String[] {
						"TS_AGGIORNAMENTO", "TS_DECORRENZA", "TS_INSERIMENTO" }),
				new TableComparison(tableNames.getVociBilancio(), new String[] {
						"TS_AGGIORNAMENTO", "TS_INSERIMENTO", "TS_DECORRENZA"}),
				new TableComparison(tableNames.getFlussi(), new String[] {
						"FLUSSO", "ORACREAZIONE", "TS_AGGIORNAMENTO",
						"TS_INSERIMENTO", "CREDTTM", "ORGNLCREDTTM" }) };
	}
}
