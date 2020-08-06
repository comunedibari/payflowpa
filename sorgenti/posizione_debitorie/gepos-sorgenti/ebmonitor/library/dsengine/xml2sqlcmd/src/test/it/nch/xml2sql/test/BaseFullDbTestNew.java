/**
 * 19/mag/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.xsqlcmd.utils.ConnectionEffect;
import it.nch.eb.xsqlcmd.utils.DbHelper;

import java.sql.Connection;

import org.dbunit.dataset.IDataSet;

/**
 * @author gdefacci
 */
public abstract class BaseFullDbTestNew extends FullDbTest {

	private final IDataSet expectedDataset;
	private final String[] initializationScripts;
	private final IDataSet[] initDataSets;

	private TableComparisonContainer tableComparisonsContainer;
	private TestEffect verificationEffect;
	
	public BaseFullDbTestNew(
			PendenzeTestConfiguration testConfig,
			String[] initScripts,
			IDataSet[] initDataSets,
			IDataSet expectedDataSet,
			TableComparisonContainer tableComparisonContainer,
			TestEffect verificationEffect) {
		super(testConfig);
		this.expectedDataset = expectedDataSet;
		this.initializationScripts = initScripts;
		this.initDataSets = initDataSets;
		this.tableComparisonsContainer = tableComparisonContainer;
		this.verificationEffect = verificationEffect;
	}
	
	protected void initDb() {
		helper.withConnection(new ConnectionEffect() {

			public void apply(Connection conn) throws Exception {
				if (initializationScripts!=null && initializationScripts.length > 0) {
					DbHelper.executeSql(conn, initializationScripts);
				}
				BaseTestRunner.insertDataSet(helper, conn, initDataSets);
				conn.commit();
			}

		});
		
	}
	
	protected void verifyResults() {
		if (verificationEffect == null) {
			super.verifyResults();
		} else {
			verificationEffect.apply();
		}
	}

	protected IDataSet expectedDataSet() {
		return expectedDataset;
	}

	protected TableComparison[] tableComparisons() {
		return this.tableComparisonsContainer.getTableComparisons();
	}
	
}
