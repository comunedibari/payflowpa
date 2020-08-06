/**
 * 19/mag/2009
 */
package it.nch.xml2sql.test;


import org.dbunit.Assertion;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;

/**
 * @author gdefacci
 */
public abstract class FullDbTest {
	
	protected final Xml2sqlDbUnitHelper helper;
	private final PendenzeTestConfiguration testConfiguration;

	public FullDbTest(PendenzeTestConfiguration testConfig) {
		this.helper = new Xml2sqlDbUnitHelper(testConfig);
		this.testConfiguration = testConfig;
	}
	
	protected abstract void initDb(); 
	
	protected abstract boolean testAction() throws Exception;
	protected abstract TableComparison[] tableComparisons();
	protected abstract IDataSet expectedDataSet();
	
	protected void verifyResults() {
		try {
			helper.withConnection(new DbUnitConnectionEffect() {

				public void apply(IDatabaseConnection conn) throws Exception {
					IDataSet actualDataSet = conn.createDataSet();
					IDataSet expectedDataSet = expectedDataSet();
					TableComparison[] tableComparisonsN = tableComparisons();
					for (int i = 0; i < tableComparisonsN.length; i++) {
						TableComparison comp = tableComparisonsN[i];
						if (!(comp instanceof ExcludeTableTableComparison)) {
							ITable actualTable = new SortedTable( actualDataSet.getTable(comp.getTableName()) );
							ITable expectedTable = new SortedTable( expectedDataSet.getTable(comp.getTableName()) );
							if (comp.getQuery() == null) {
								Assertion.assertEqualsIgnoreCols(expectedTable, actualTable, comp.getExcludeFields());
							} else {
								Assertion.assertEqualsByQuery(expectedTable, conn,  comp.getTableName(), comp.getQuery(), comp.getExcludeFields());
							}
						}
					}
				}
			});
			
		} catch (Exception e) {
			throw new RuntimeException(e);
		} 
	}
	
	public Runnable getRun() {
		return new Runnable() {

			public void run() {
				try {
					System.out.println("[INIT] initializing database ");
					testConfiguration.initializeDB();
					System.out.println("[INIT] finisihed initializing database ");
					initDb();
					if (testAction()) {
						verifyResults();
						onSuccess();
					}
				} catch (Exception e) {
					onException(e);
					throw new RuntimeException(e);
				}
				
			}
			
		};
	}

	protected void onException(Exception e) {
		e.printStackTrace();
	}

	protected void onSuccess() {
		System.out.println(">>>>" + this.toString() + ": test completed successfully");
	}

	public PendenzeTestConfiguration getTestConfiguration() {
		return testConfiguration;
	}
	
}
