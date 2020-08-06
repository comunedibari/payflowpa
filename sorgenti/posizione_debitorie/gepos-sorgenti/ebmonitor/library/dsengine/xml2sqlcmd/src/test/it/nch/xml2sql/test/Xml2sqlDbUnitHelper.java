/**
 * 15/mag/2009
 */
package it.nch.xml2sql.test;

import it.nch.eb.common.utils.resource.ResourcesUtil;
import it.nch.eb.xsqlcmd.dao.UIdProvider;
import it.nch.eb.xsqlcmd.dbtrpos.dao.TableNames;
import it.nch.eb.xsqlcmd.utils.DbHelper;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.operation.DatabaseOperation;

/**
 * @author gdefacci
 */
public class Xml2sqlDbUnitHelper extends DbHelper {
	
	private static final org.slf4j.Logger log = ResourcesUtil.createLogger(Xml2sqlDbUnitHelper.class);

	private final PendenzeTestConfiguration testConfig;
	
	public Xml2sqlDbUnitHelper(PendenzeTestConfiguration config) {
		super(config);
		this.testConfig = config;
	}
	
	public DatabaseConnection wrapConnection(Connection conn) throws Exception {
		return this.testConfig.create(conn);
	}
	
	private DbExporter dbExporter(IDatabaseConnection dbUnitConnection) {
		return new DbExporter(dbUnitConnection, testConfig.getTableNames().getAllTableNames());
	}
	
	public void createDefaultDTD() {
		withConnection(new DbUnitConnectionEffect() {

			public void apply(IDatabaseConnection dbUnitConnection) throws Exception {
				dbExporter(dbUnitConnection).createDefaultXmlCatalogAndDTD();
			}
		});
	}
	
	public void exportDb(final File target) {
		withConnection(new DbUnitConnectionEffect() {

			public void apply(IDatabaseConnection dbUnitConnection) throws Exception {
				dbExporter(dbUnitConnection).export(target);
				log.info("created " + target.getAbsolutePath());
			}
		});
	}
	
	public void exportDb(final TableComparisonContainer cmpCnt, final File target) {
		withConnection(new DbUnitConnectionEffect() {

			public void apply(IDatabaseConnection dbUnitConnection) throws Exception {
				dbExporter(dbUnitConnection).export(cmpCnt, target);
				log.info("created " + target.getAbsolutePath());
			}
			
		});
	}
	
	public void fullDatabaseImport(final Reader file)
			throws ClassNotFoundException, DatabaseUnitException, IOException,
			SQLException {
		withConnection(new DbUnitConnectionEffect() {

			public void apply(IDatabaseConnection conn) throws Exception {
				IDataSet dataSet = new FlatXmlDataSet(file, true);
				DatabaseOperation.CLEAN_INSERT.execute(conn, dataSet);
			}
			
		});
	}
	
	public void withConnection(DbUnitConnectionEffect efct) {
		Connection rawConn = null;
		IDatabaseConnection conn = null;
		try {
			rawConn = testConfig.getConnection();
			conn = wrapConnection(rawConn);
			efct.apply(conn);
			rawConn.commit();
		} catch (Exception e) {
			throw new RuntimeException("error getting connection " , e); 
		} finally {
			closeConnection(rawConn);
		}
	}
	
	public UIdProvider getSequence(String dbTableName) {
		TableNames tabNms = testConfig.getTableNames();
		if (tabNms.getAllegato().equals(dbTableName)) return testConfig.getTablesUidProvider().getAllegato();
		else if (tabNms.getAllineamenti().equals(dbTableName)) return testConfig.getTablesUidProvider().getAllineamenti();
		else if (tabNms.getCondizioniPagamento().equals(dbTableName)) return testConfig.getTablesUidProvider().getCondizioniPagamento();
		else if (tabNms.getDestinatari().equals(dbTableName)) return testConfig.getTablesUidProvider().getDestinatari();
		else if (tabNms.getFlussi().equals(dbTableName)) return testConfig.getTablesUidProvider().getFlussi();
		else if (tabNms.getPendenze().equals(dbTableName)) return testConfig.getTablesUidProvider().getPendenze();
		else if (tabNms.getVociBilancio().equals(dbTableName)) return testConfig.getTablesUidProvider().getVociBilancio();
		else if (tabNms.getEsitiPendenza().equals(dbTableName)) return testConfig.getTablesUidProvider().getEsitiPendenza();
		else if (tabNms.getErroriEsitiPendenza().equals(dbTableName)) return testConfig.getTablesUidProvider().getErroriEsitiPendenza();
		else return null;
		
	}
	
}
